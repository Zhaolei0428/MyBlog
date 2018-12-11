package com.zhao.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class AdminController {
    @RequestMapping(value = "/admin", method = GET)
    public String adminPage(){
        return "admin";
    }
}
