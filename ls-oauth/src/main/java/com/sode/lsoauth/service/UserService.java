package com.sode.lsoauth.service;

import com.sode.lsoauth.entity.User;
import com.sode.lsoauth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        List<SimpleGrantedAuthority> roles = u.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .toList();
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                roles
        );
    }
}
