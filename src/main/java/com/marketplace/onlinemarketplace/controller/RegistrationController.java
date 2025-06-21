import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.validation.Valid;
package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.LoginRequest;
import com.marketplace.onlinemarketplace.entity.RegisterRequest;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity SignupRequest(@Valid @RequestBody RegisterRequest registerRequest) {

        if (userService.findByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email Already Exists");
        }
        userService.registerUser(registerRequest);


        return ResponseEntity.ok("User Registered Successfully");

    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user == null || !bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        }
        String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok("Login Successful. Token: " + token);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String token) {
        if (!userService.isTokenValidForLogout(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or tampered token.");
        }
        userService.logout(token);
        return ResponseEntity.ok("Logout successful.");


    @RestController
    @RequestMapping("/api")
    public class RegistrationController {

        private final UserRepository userRepository;
        private final BCryptPasswordEncoder passwordEncoder;

        public RegistrationController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @PutMapping("/changepassword")
        public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
            User user = userRepository.findByUsername(changePasswordRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + changePasswordRequest.getUsername()));

            if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Incorrect old password");
            }

            if (changePasswordRequest.getNewPassword().length() < 8) {
                return ResponseEntity.badRequest().body("New password is too weak. It should be at least 8 characters long.");
            }

            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);

            return ResponseEntity.ok("Password changed successfully");
        }
    }

    class ChangePasswordRequest {
        private String username;
        private String oldPassword;
        private String newPassword;

    }

    }

   