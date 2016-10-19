package com.github.walterfan.example.orm;

import java.util.List;

/**
 * Created by walter on 8/29/16.
 */
public interface MessageDao {

    Integer createMessage(Message msg);

    List<Message> listMesage();

    Message retrieveMessage(Integer id);

    void updateMessage(Message msg) ;

    void deleteMessage(Integer id);
}
