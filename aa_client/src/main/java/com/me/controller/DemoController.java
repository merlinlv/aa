package com.me.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Value("${url}")
    private  String url;
    @GetMapping("/url")
    public String getUrl(){
        return url ;
    }
}
