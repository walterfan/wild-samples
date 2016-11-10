package com.github.walterfan.guestbook;

import org.springframework.core.env.Environment;

/**
 * Created by walter on 07/11/2016.
 */
public class MessageProperties {

    protected Environment env;

    public long getHttpReadTimeout() {
        return env.getProperty("httpReadTimeoutSeconds", Integer.class, 15) * 1000L;
    }

    public String getJdbcDriver() {
        return env.getProperty("jdbc.driver", "org.h2.Driver");
    }

    public String getJdbcUrl() {
        return env.getProperty("jdbc.url", "jdbc:h2:mem:mydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
    }

    public String getJdbcUserName() {
        return env.getProperty("jdbc.username", "sa");
    }

    public String getJdbcPassword() {
        return env.getProperty("jdbc.password", "");
    }
}
