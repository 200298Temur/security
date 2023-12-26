package com.mohirdev.mohirdev.security;

import com.mohirdev.mohirdev.domain.Authority;
import com.mohirdev.mohirdev.repositoriy.UserRepository;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailService")
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowercaseUsername = username.toLowerCase();
        return userRepository
                .findByUsername(lowercaseUsername)
                .map(user -> createSpringSecurity(lowercaseUsername, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
    }

    private UserDetails createSpringSecurity(String username, com.mohirdev.mohirdev.domain.User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + username + " was not activated");
        }

        List<GrantedAuthority> authorityList = user
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(username, user.getPassword(), authorityList);
    }
}
