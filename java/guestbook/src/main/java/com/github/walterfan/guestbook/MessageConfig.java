package com.github.walterfan.guestbook;

import com.github.walterfan.guestbook.controller.IndexController;
import com.github.walterfan.guestbook.controller.MessageController;
import com.github.walterfan.guestbook.dao.MessageDao;
import com.github.walterfan.guestbook.dao.MessageMapper;
import com.github.walterfan.guestbook.service.MessageService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * Created by walter on 06/11/2016.
 */
@Configuration
@EnableWebMvc
@Import({
        IndexController.class,
        MessageController.class,
        MessageService.class
})
public class MessageConfig {

    @Bean
    public MessageService messageService() {
        return new MessageService();
    }

    @Bean
    public MessageProperties messageProperties()  {
        return new MessageProperties();
    }


    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setUrl("jdbc:h2:mem:mydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setPassword("");

        // create a table and populate some data
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("drop table message if exists");
        jdbcTemplate.execute("create table message(id varchar(36), title varchar(255), " +
                "content varchar(4095), tags varchar(255), authorId UUID, createTime TIMESTAMP)");
        jdbcTemplate.update("INSERT INTO message(id, title, content, tags, createTime) values (?,?,?,?,?)",
                UUID.randomUUID().toString(), "hello", "this is a test message", "test", "2016-01-01 12:00:00+08:00");

        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        return (SqlSessionFactory) sqlSessionFactory.getObject();
    }

    @Bean
    public MessageDao messageDao() throws Exception {
        SqlSessionFactory sessionFactory = sqlSessionFactory();
        sessionFactory.getConfiguration().addMapper(MessageMapper.class);

        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(MessageMapper.class);
    }
}
