package com.zhao.blog.controller;

import com.zhao.blog.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.validation.Errors;

import javax.validation.Valid;

@Controller
public class UserController {
    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute(new User());
        return "registerForm";
    }

    @RequestMapping(value = "/register", method = POST)
    public String processRegistration(
            @Valid User user,
            Errors errors) {
        if(errors.hasErrors()) {
            System.out.println(errors.getAllErrors());
            return "registerForm";
        }
        return "redirect:/user/" + user.getUsername();
    }

    @RequestMapping(value = "/user/{username}", method = GET)
    public String showUserProfile(
            @PathVariable String username, Model model) {
        User user = new User();
        user.setUsername(username);
        model.addAttribute(user);
        return "profile";
    }

}
