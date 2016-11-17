package com.github.walterfan.guestbook;

import com.github.walterfan.guestbook.controller.IndexController;
import com.github.walterfan.guestbook.controller.MessageController;
import com.github.walterfan.guestbook.dao.MessageDao;
import com.github.walterfan.guestbook.dao.MessageMapper;
import com.github.walterfan.guestbook.service.MessageService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * Created by walter on 06/11/2016.
 */
@Configuration
@EnableWebMvc
@Import({
        IndexController.class,
        MessageController.class
})
public class MessageConfig {

    @Autowired
    private Environment env;

    @Bean
    public MessageService messageService() {
        return new MessageService();
    }

    @Bean
    public MessageProperties messageProperties()  {
        return new MessageProperties();
    }


    @Bean
    public DataSource dataSource() throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass((Class<? extends Driver>) Class.forName(messageProperties().getJdbcDriver()));
        dataSource.setUsername(messageProperties().getJdbcUserName());
        dataSource.setUrl(messageProperties().getJdbcUrl());
        dataSource.setPassword(messageProperties().getJdbcPassword());

        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws ClassNotFoundException {
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
