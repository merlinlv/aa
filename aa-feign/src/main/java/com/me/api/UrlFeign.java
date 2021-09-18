package com.me.api;

import com.me.api.impl.UrlFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="aa-client",fallback  = UrlFeignImpl.class )
public interface UrlFeign {
    @GetMapping("/url")
    public String getUrl() ;
}
