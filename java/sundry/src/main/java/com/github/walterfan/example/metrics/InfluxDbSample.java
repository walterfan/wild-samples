package com.github.walterfan.example.metrics;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.concurrent.TimeUnit;

/**
 * Created by walter on 03/11/2016.
 */
public class InfluxDbSample {


    public static void main(String[] args) {
        InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "root", "");
        Pong pong = influxDB.ping();
        System.out.println("pong: " + pong);

        String dbName = "aTimeSeries";
        influxDB.createDatabase(dbName);

        // Flush every 2000 Points, at least every 100ms
        //influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);
        System.out.println("--- created database and will insert data ---");

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy("default")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        Point point1 = Point.measurement("cpu")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("idle", 90L)
                .addField("user", 9L)
                .addField("system", 1L)
                .build();
        Point point2 = Point.measurement("disk")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("used", 80L)
                .addField("free", 1L)
                .build();
        batchPoints.point(point1);
        batchPoints.point(point2);
        influxDB.write(batchPoints);
        Query query = new Query("SELECT idle FROM cpu", dbName);
        QueryResult rs = influxDB.query(query);

        System.out.println("result: " + rs.toString());
        influxDB.deleteDatabase(dbName);
    }
}
