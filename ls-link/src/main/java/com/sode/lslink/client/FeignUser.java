package com.sode.lslink.client;

import com.sode.lslink.entity.Link;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="ls-user", path="/users")
public interface FeignUser {

    @PostMapping("/append/{id}/{link}")
    public ResponseEntity<Void> appendLink (@PathVariable Long id, @PathVariable Link link);


}
