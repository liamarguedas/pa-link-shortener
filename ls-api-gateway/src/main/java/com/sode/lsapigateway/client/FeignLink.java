package com.sode.lsapigateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ls-link", path="/urls")
public interface FeignLink {

    @GetMapping("/redirect/{accessKey}")
    String redirect(@PathVariable("accessKey") String accessKey);

    }


