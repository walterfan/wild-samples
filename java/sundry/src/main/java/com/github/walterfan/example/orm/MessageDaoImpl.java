package com.github.walterfan.example.orm;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by walter on 8/29/16.
 */

@Repository
@Transactional(readOnly = true)
public class MessageDaoImpl implements MessageDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Message> listMesage() {
        Session session = sessionFactory.openSession();
        String hql = "FROM Message";
        Query query = session.createQuery(hql);
        List<Message> persons = query.list();
        return persons;
    }

    @Transactional(readOnly = false)
    @Override
    public Integer createMessage(Message msg) {
        Session session = sessionFactory.openSession();
        session.save(msg);
        return msg.getId();
    }

    @Override
    public Message retrieveMessage(Integer id) {
        Session session = sessionFactory.openSession();
        return (Message)session.load(Message.class, id);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateMessage(Message msg) {
        Session session = sessionFactory.openSession();
        session.update(msg);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteMessage(Integer id) {
        Session session = sessionFactory.openSession();
        session.delete(id);
    }

}