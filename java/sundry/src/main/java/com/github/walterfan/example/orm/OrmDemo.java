package com.github.walterfan.example.orm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walter on 8/29/16.
 */
public class OrmDemo {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "app-context.xml");


        MessageService MessageService =
                context.getBean("messageServiceImpl", MessageService.class);

        Message Message = new Message();
        Message.setSubject("good bye");
        Message.setContent("see you tomorrow");
        MessageService.createMessage(Message);

        for (Message p : MessageService.listMesage()){
            System.out.println(p);
        }
    }
}
