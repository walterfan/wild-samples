package com.github.walterfan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getIndexPage()  {
		return "index";
	}

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage()  {
        return "UserManagement";
    }

    @RequestMapping(value = "/guestbook", method = RequestMethod.GET)
    public String getGuestbookPage()  {
        return "Guestbook";
    }
}