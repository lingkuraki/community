package com.kuraki.community.controller;

import com.kuraki.community.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private AuthorizeService authorizeService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        authorizeService.callback(code, state);
        return "index";
    }
}
