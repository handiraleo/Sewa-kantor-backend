package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.entity.User;
import com.kampusmerdeka.officeorder.entity.UserAdmin;
import com.kampusmerdeka.officeorder.entity.UserCustomer;
import com.kampusmerdeka.officeorder.repository.CustomerRepository;
import com.kampusmerdeka.officeorder.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomUserDetailServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Test
    void loadUserByUsernameCustomer_Success() {
        UserCustomer userCustomer = UserCustomer.builder()
                .email("rofik@mail.com")
                .password("$2a$12$8LCT9hwZwDh5NzqOC2epeeXRClqvQnzebrHgesb39B3Ex7.Ybov6q")
                .role(User.Role.CUSTOMER)
                .build();

        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(userCustomer));

        UserDetails userDetails = customUserDetailService.loadUserByUsername(userCustomer.getEmail());
        assertEquals(userCustomer.getEmail(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsernameAdmin_Success() {
        UserAdmin userAdmin = UserAdmin.builder()
                .username("admin")
                .password("$2a$12$8LCT9hwZwDh5NzqOC2epeeXRClqvQnzebrHgesb39B3Ex7.Ybov6q")
                .role(User.Role.SUPERADMIN)
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userAdmin));

        UserDetails userDetails = customUserDetailService.loadUserByUsername(userAdmin.getUsername());
        assertEquals(userAdmin.getUsername(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsernameCustomer_Error() {
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        try {
            customUserDetailService.loadUserByUsername("rofik@mail.com");
        } catch (UsernameNotFoundException e) {
        }
    }

    @Test
    void loadUserByUsernameAdmin_Error() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        try {
            customUserDetailService.loadUserByUsername("rofik");
        } catch (UsernameNotFoundException e) {
        }
    }
}