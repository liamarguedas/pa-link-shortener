package com.sode.lsuser.client;

import com.sode.lsuser.config.FeignConfiguration;
import com.sode.lsuser.entity.Link;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="ls-link", path="/urls", configuration= FeignConfiguration.class)
public interface FeignLink {

    @GetMapping("/all/{username}")
    public ResponseEntity<List<Link>> getAllLinksByUsername(@PathVariable("username") String username);

}


