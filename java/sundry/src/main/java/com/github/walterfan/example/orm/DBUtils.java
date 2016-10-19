package com.github.walterfan.example.orm;

/**
 * Created by walter on 8/29/16.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DBUtils {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initialize(){
        System.out.println("--- initialize...  ---");
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS MESSAGE");
            statement.executeUpdate(
                    "CREATE TABLE MESSAGE(" +
                            "ID INTEGER Primary key AUTOINCREMENT NOT NULL, " +
                            "SUBJECT varchar(128) not null, " +
                            "CONTENT varchar(256) not null, " +
                            "CREATETIME DEFAULT CURRENT_TIMESTAMP, " +
                            "LASTMODIFIEDTIME DEFAULT CURRENT_TIMESTAMP)"
            );

            statement.execute("DROP TABLE IF EXISTS AUTHOR");
            statement.executeUpdate(
                    "CREATE TABLE AUTHOR(" +
                            "ID INTEGER Primary key AUTOINCREMENT NOT NULL, " +
                            "NAME varchar(128) not null, " +
                            "EMAIL varchar(128) not null, " +
                            "PHONENUMBER varchar(32) not null, " +
                            "CREATETIME DEFAULT CURRENT_TIMESTAMP, " +
                            "LASTMODIFIEDTIME DEFAULT CURRENT_TIMESTAMP)"
            );

            statement.executeUpdate(
                    "INSERT INTO MESSAGE " +
                            "(SUBJECT, CONTENT) " +
                            "VALUES " + "('Hello', 'How are you doing')"
            );
            statement.close();
            connection.close();
            System.out.println("--- initialized  ---");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}