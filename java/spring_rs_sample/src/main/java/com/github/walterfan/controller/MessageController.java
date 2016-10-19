package com.github.walterfan.controller;

import com.github.walterfan.model.Message;
import com.github.walterfan.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by walter on 8/28/16.
 */

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;


    //-------------------Retrieve All Users--------------------------------------------------------

    @RequestMapping(value = "/messages/", method = RequestMethod.POST)
    public ResponseEntity<Void> createMessage(@RequestBody Message msg, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + msg.getSubject());



        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/messages/{id}").buildAndExpand(msg.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
