package com.sidecar.customer.service.impl;

import com.sidecar.customer.domain.AuthUser;
import com.sidecar.customer.repository.AuthUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    public JwtUserDetailsService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<AuthUser> authUserOptional = this.authUserRepository.findByUsername(userName);
        AuthUser authUser = authUserOptional.orElseThrow(() -> new UsernameNotFoundException("No User found"));
        //AuthUser can be associated with roles and resources for Authorization purpose
        return new User(authUser.getUsername(), authUser.getPassword(), new ArrayList<>());

    }
}
