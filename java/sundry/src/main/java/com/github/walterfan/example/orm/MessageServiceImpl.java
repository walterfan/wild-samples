package com.github.walterfan.example.orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created by walter on 8/29/16.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public Integer createMessage(Message msg) {
        return messageDao.createMessage(msg);
    }

    @Override
    public Message retrieveMessage(Integer id) {
        return messageDao.retrieveMessage(id);
    }

    @Override
    public void updateMessage(Message msg) {
        messageDao.updateMessage(msg);
    }

    @Override
    public void deleteMessage(Integer id) {
        messageDao.deleteMessage(id);
    }

    @Override
    public List<Message> listMesage() {
        return messageDao.listMesage();
    }
}
