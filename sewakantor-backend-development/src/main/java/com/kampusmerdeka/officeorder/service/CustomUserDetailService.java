package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.entity.UserAdmin;
import com.kampusmerdeka.officeorder.entity.UserCustomer;
import com.kampusmerdeka.officeorder.repository.CustomerRepository;
import com.kampusmerdeka.officeorder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password;
        String role;

        if (username.matches("^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$")) {
            UserCustomer userCustomer = customerRepository.findByEmail(username).orElse(null);
            if (userCustomer == null) throw new UsernameNotFoundException(String.format("User %s not found", username));

            password = userCustomer.getPassword();
            role = userCustomer.getRole().name();
        } else {
            UserAdmin userAdmin = userRepository.findByUsername(username).orElse(null);
            if (userAdmin == null) throw new UsernameNotFoundException(String.format("User %s not found", username));

            password = userAdmin.getPassword();
            role = userAdmin.getRole().name();
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                password,
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}