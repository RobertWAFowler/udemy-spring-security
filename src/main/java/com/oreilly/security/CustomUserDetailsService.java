package com.oreilly.security;

import com.oreilly.security.domain.repositories.AutoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final AutoUserRepository autoUserRepository;

    @Autowired
    public CustomUserDetailsService(AutoUserRepository autoUserRepository) {
        this.autoUserRepository = autoUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return autoUserRepository.findByUsername(username);
    }
}