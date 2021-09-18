package com.me.controller;

import com.me.api.UrlFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
public class UrlController {
    @Resource
    private UrlFeign urlFeign;

    @GetMapping("/consume/url")
    public String  getUrl(){
        return urlFeign.getUrl() ;
    }
}
