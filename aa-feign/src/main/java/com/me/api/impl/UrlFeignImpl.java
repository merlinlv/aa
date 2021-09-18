package com.me.api.impl;

import com.me.api.UrlFeign;
import org.springframework.stereotype.Component;

@Component
public class UrlFeignImpl implements UrlFeign {

   public String getUrl(){
       return "调用接口错误";
   }
}
