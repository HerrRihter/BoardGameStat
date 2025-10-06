package org.trinogin.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.trinogin.repository.JdbcUserRepository;

@Service
public class JdbcUserDetailsService implements UserDetailsService {

    private final JdbcUserRepository userRepository;

    public JdbcUserDetailsService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) {
        var user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new UserDetailsImpl(user.get());
    }
}