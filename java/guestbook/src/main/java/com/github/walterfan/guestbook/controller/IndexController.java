package com.github.walterfan.guestbook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by walter on 06/11/2016.
 */
@RestController
@RequestMapping(value = "/")
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
