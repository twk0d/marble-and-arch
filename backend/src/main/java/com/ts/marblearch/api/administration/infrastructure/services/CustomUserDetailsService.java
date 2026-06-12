package com.ts.marblearch.api.administration.infrastructure.services;

import com.ts.marblearch.api.administration.application.IUserRepository;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(new Email(username))
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail().address(),
                        user.getPassword(),
                        user.isActive(),
                        true, true, true,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
