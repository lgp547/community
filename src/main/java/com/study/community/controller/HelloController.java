package com.study.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    //响应根目录
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
