package com.sode.lslink.client;

import com.sode.lslink.entity.Link;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="ls-user", path="/users")
public interface FeignUser {

    @PostMapping
    public ResponseEntity<Void> appendLink(Long id, Link link);

}
