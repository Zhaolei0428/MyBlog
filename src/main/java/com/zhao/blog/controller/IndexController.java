package com.zhao.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping({"/"})
public class IndexController {
    @RequestMapping(method = GET)
    public String home() {
        return "index";
    }
}
