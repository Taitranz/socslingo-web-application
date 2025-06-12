package com.socslingo.website.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import com.socslingo.website.models.User;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmailOrUsername(identifier);
        
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with identifier: " + identifier);
        }
        
        User user = userOptional.get();
        
        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(identifier);
        userBuilder.password(user.getPassword());
        userBuilder.roles("USER"); // You can add more sophisticated role management later
        
        return userBuilder.build();
    }
}
