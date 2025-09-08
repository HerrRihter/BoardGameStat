package org.trinogin.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.trinogin.repository.UserRepository;

@Service
public class JdbcUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JdbcUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) {
        UserDetails userDetails = new UserDetailsImpl(userRepository.getUserByUserName(username));
        return userDetails;
    }
}