package com.liaoyb.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SigninController {
    @RequestMapping("/signin")
    public String signin() {
        return "signin";
    }
}
