package com.zhao.blog.controller;

import com.zhao.blog.model.User;
import com.zhao.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/home", method = GET)
    public String adminPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getUsername());
        model.addAttribute("adminMessage", "Content Available Only for Users with Admin Role");
        return "admin/home";
    }
}
