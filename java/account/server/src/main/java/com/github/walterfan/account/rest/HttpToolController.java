package com.github.walterfan.account.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.walterfan.account.domain.HttpReq;
@RestController
@RequestMapping("/")
public class HttpToolController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "/request", method = RequestMethod.POST)
    public ResponseEntity<HttpReq> processRequest(@RequestBody HttpReq req) {
        logger.info("got post request: " + req.toString());
        
        return new ResponseEntity<HttpReq>(req, HttpStatus.OK);
    }
}
