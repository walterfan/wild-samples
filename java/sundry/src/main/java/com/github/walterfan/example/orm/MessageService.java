package com.github.walterfan.example.orm;

import java.util.List;

/**
 * Created by walter on 8/29/16.
 */
public interface MessageService {
    Integer createMessage(Message msg);

    Message retrieveMessage(Integer id);

    void updateMessage(Message msg) ;

    void deleteMessage(Integer id);

    List<Message> listMesage();
}
