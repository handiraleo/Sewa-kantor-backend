package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.dto.request.AdminLoginRequest;
import com.kampusmerdeka.officeorder.dto.request.CustomerLoginRequest;
import com.kampusmerdeka.officeorder.dto.request.RegisterRequest;
import com.kampusmerdeka.officeorder.entity.User;
import com.kampusmerdeka.officeorder.entity.UserAdmin;
import com.kampusmerdeka.officeorder.entity.UserCustomer;
import com.kampusmerdeka.officeorder.repository.CustomerRepository;
import com.kampusmerdeka.officeorder.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registerSuccess() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("rofik")
                .lastName("s")
                .email("rofik@mail.com")
                .password("12345")
                .build();
        UserCustomer userCustomer = UserCustomer.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(User.Role.CUSTOMER)
                .build();

        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(customerRepository.saveAndFlush(any(UserCustomer.class))).thenReturn(userCustomer);
        when(customUserDetailService.loadUserByUsername(registerRequest.getEmail())).thenReturn(
                new org.springframework.security.core.userdetails.User(
                        userCustomer.getEmail(),
                        userCustomer.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(userCustomer.getRole().name()))
                )
        );

        ResponseEntity<Object> responseEntity = authService.register(registerRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void registerEmailAlreadyExist() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("rofik")
                .lastName("s")
                .email("rofik@mail.com")
                .password("12345")
                .build();
        UserCustomer userCustomer = UserCustomer.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(User.Role.CUSTOMER)
                .build();

        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(userCustomer));

        ResponseEntity<Object> responseEntity = authService.register(registerRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void loginAdminSuccess() {
        UserAdmin userAdmin = UserAdmin.builder().username("superadmin").password("$2a$12$8LCT9hwZwDh5NzqOC2epeeXRClqvQnzebrHgesb39B3Ex7.Ybov6q").role(User.Role.SUPERADMIN).build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userAdmin));
        when(customUserDetailService.loadUserByUsername(userAdmin.getUsername())).thenReturn(
                new org.springframework.security.core.userdetails.User(
                        userAdmin.getUsername(),
                        userAdmin.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(userAdmin.getRole().name()))
                )
        );

        AdminLoginRequest loginRequest = AdminLoginRequest.builder().username("superadmin").password("12345").build();
        ResponseEntity<Object> responseEntity = authService.login(loginRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void loginCustomerSuccess() {
        UserCustomer userCustomer = UserCustomer.builder().email("rofik@mail.com").password("$2a$12$8LCT9hwZwDh5NzqOC2epeeXRClqvQnzebrHgesb39B3Ex7.Ybov6q").role(User.Role.CUSTOMER).build();
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(userCustomer));
        when(customUserDetailService.loadUserByUsername(userCustomer.getEmail())).thenReturn(
                new org.springframework.security.core.userdetails.User(
                        userCustomer.getEmail(),
                        userCustomer.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(userCustomer.getRole().name()))
                )
        );

        CustomerLoginRequest loginRequest = CustomerLoginRequest.builder().email("rofik@mail.com").password("12345").build();
        ResponseEntity<Object> responseEntity = authService.login(loginRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void loginUserNotFound() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.ofNullable(null));
        when(customUserDetailService.loadUserByUsername(anyString())).thenThrow(new UsernameNotFoundException(""));

        AdminLoginRequest loginRequest = AdminLoginRequest.builder().username("any").password("12345").build();
        ResponseEntity<Object> responseEntity = authService.login(loginRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void loginWrongPassword() {
        UserAdmin userAdmin = UserAdmin.builder().username("admin").password("$2a$12$8LCT9hwZwDh5NzqOC2epeeXRClqvQnzebrHgesb39B3Ex7.Ybov6q").role(User.Role.SUPERADMIN).build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userAdmin));
        when(customUserDetailService.loadUserByUsername(userAdmin.getUsername())).thenReturn(
                new org.springframework.security.core.userdetails.User(
                        userAdmin.getUsername(),
                        userAdmin.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(userAdmin.getRole().name()))
                )
        );
        AdminLoginRequest loginRequest = AdminLoginRequest.builder().username("admin").password("wrongpassword").build();
        ResponseEntity<Object> responseEntity = authService.login(loginRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @WithMockUser(username = "rofik@mail.com", authorities = {"CUSTOMER"})
    void meCustomerSuccess() {
        UserCustomer userCustomer = UserCustomer.builder()
                .firstName("rofik")
                .lastName("s")
                .email("rofik@mail.com")
                .password(passwordEncoder.encode("12345"))
                .role(User.Role.CUSTOMER)
                .build();
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(userCustomer));
        try {
            UserCustomer me = authService.me();
            assertEquals(me.getEmail(), userCustomer.getEmail());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @WithMockUser(username = "superadmin", authorities = {"SUPERADMIN"})
    void meAdminSuccess() {
        UserAdmin userAdmin = UserAdmin.builder()
                .username("superadmin")
                .password("$2a$12$8LCT9hwZwDh5NzqOC2epeeXRClqvQnzebrHgesb39B3Ex7.Ybov6q")
                .role(User.Role.SUPERADMIN)
                .build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userAdmin));
        try {

            UserAdmin me = authService.me();
            assertEquals(me.getUsername(), userAdmin.getUsername());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @WithMockUser(username = "superadmin", authorities = {"SUPERADMIN"})
    void meAdminError() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        try {
            authService.me();
        } catch (AuthorizationServiceException e) {
        }
    }

    @Test
    @WithMockUser(username = "rofik@mail.com", authorities = {"CUSTOMER"})
    void meCustomerError() {
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        try {
            authService.me();
        } catch (AuthorizationServiceException e) {
        }
    }
}