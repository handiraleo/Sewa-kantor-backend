package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.dto.repsonse.LoginResponse;
import com.kampusmerdeka.officeorder.dto.request.AdminLoginRequest;
import com.kampusmerdeka.officeorder.dto.request.CustomerLoginRequest;
import com.kampusmerdeka.officeorder.dto.request.LoginRequest;
import com.kampusmerdeka.officeorder.dto.request.RegisterRequest;
import com.kampusmerdeka.officeorder.entity.User;
import com.kampusmerdeka.officeorder.entity.UserAdmin;
import com.kampusmerdeka.officeorder.entity.UserCustomer;
import com.kampusmerdeka.officeorder.repository.CustomerRepository;
import com.kampusmerdeka.officeorder.repository.UserRepository;
import com.kampusmerdeka.officeorder.security.TokenProvider;
import com.kampusmerdeka.officeorder.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private CustomUserDetailService userDetailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> login(LoginRequest request) {
        try {
            var username = "";
            var password = request.getPassword();

            User user;
            if (request instanceof CustomerLoginRequest) {
                username = ((CustomerLoginRequest) request).getEmail();
                Optional<UserCustomer> userCustomerOptional = customerRepository.findByEmail(username);
                if (userCustomerOptional.isEmpty()) return ResponseUtil.notFound("user not found");

                user = userCustomerOptional.get();
            } else {
                username = ((AdminLoginRequest) request).getUsername();
                Optional<UserAdmin> userAdminOptional = userRepository.findByUsername(username);
                if (userAdminOptional.isEmpty()) return ResponseUtil.notFound("user not found");

                user = userAdminOptional.get();
            }

            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenProvider.generateToken(userDetails);

            User.Role role = User.Role.valueOf(userDetails.getAuthorities().stream().findFirst().get().toString());

            String email = null;
            username = null;
            if (user instanceof UserAdmin) {
                UserAdmin userAdmin = (UserAdmin) user;
                username = userAdmin.getUsername();
            } else {
                UserCustomer userCustomer = (UserCustomer) user;
                email = userCustomer.getEmail();
            }
            LoginResponse response = LoginResponse.builder()
                    .tokenType("Bearer")
                    .token(token)
                    .email(email)
                    .username(username)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .roleId(role.ordinal())
                    .role(role.name())
                    .build();

            return ResponseUtil.ok("login success", response);
        } catch (UsernameNotFoundException exception) {
            log.error("Failed load user by username with error: {}", exception.getLocalizedMessage());
            return ResponseUtil.notFound("user tidak terdaftar");
        } catch (BadCredentialsException exception) {
            log.error("Failed to authenticate: {}", exception.getLocalizedMessage());
            return ResponseUtil.badRequest("password salah");
        }
    }

    public <T> T me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<? extends GrantedAuthority> grantedAuthority = authentication.getAuthorities().stream().findFirst();

        try {
            String authority = grantedAuthority.get().toString();
            if (authority.equals(User.Role.CUSTOMER.name())) {
                Optional<UserCustomer> customerOptional = customerRepository.findByEmail(username);

                if (customerOptional.isEmpty()) throw new AuthorizationServiceException("Unauthorized");
                return (T) customerOptional.get();
            } else {
                Optional<UserAdmin> userOptional = userRepository.findByUsername(username);

                if (userOptional.isEmpty()) throw new AuthorizationServiceException("Unauthorized");
                return (T) userOptional.get();
            }
        } catch (AuthorizationServiceException exception) {
            log.error(exception.getLocalizedMessage());
            throw exception;
        }

    }

    public ResponseEntity<Object> register(RegisterRequest request) {
        Optional<UserCustomer> customerOptionalByEmail = customerRepository.findByEmail(request.getEmail());
        if (customerOptionalByEmail.isPresent())
            return ResponseUtil.badRequest("email sudah digunakan");


        UserCustomer userCustomer = UserCustomer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.CUSTOMER)
                .build();

        userCustomer = customerRepository.saveAndFlush(userCustomer);

        UserDetails userDetails = userDetailService.loadUserByUsername(userCustomer.getEmail());
        String token = tokenProvider.generateToken(userDetails);


        User.Role role = User.Role.valueOf(userDetails.getAuthorities().stream().findFirst().get().toString());
        LoginResponse response = LoginResponse.builder()
                .tokenType("Bearer")
                .token(token)
                .email(userDetails.getUsername())
                .firstName(userCustomer.getFirstName())
                .lastName(userCustomer.getLastName())
                .roleId(role.ordinal())
                .role(role.name())
                .build();

        return ResponseUtil.ok("register berhasil", response);
    }
}