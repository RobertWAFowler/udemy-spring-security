package com.oreilly.security;

import com.oreilly.security.domain.entities.AutoUser;
import com.oreilly.security.domain.repositories.AutoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AutoUserRepository autoUserRepository;

    @Autowired
    public CustomAuthenticationProvider(AutoUserRepository autoUserRepository) {
        this.autoUserRepository = autoUserRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;
        AutoUser user = autoUserRepository.findByUsername(token.getName());

        if (user == null || !user.getPassword().equalsIgnoreCase(token.getCredentials().toString())
                || token.getMake().equalsIgnoreCase("subaru")) {
            throw new BadCredentialsException("The credentials are invalid");
        }
        return new CustomAuthenticationToken(user, user.getPassword(), user.getAuthorities(), token.getMake());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.equals(authentication);
    }
}
