package com.kuraki.community.controller;

import com.kuraki.community.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        indexService.callback(code, state);
        return "index";
    }
}
