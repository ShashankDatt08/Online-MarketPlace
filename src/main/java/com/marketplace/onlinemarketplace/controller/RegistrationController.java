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
    }

    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(
            @RequestParam String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {

        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body("Current Password is Incorrect.");
        }

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("New password and confirm password do not match.");
        }

        userService.changePassword(email, newPassword, confirmPassword);
        return ResponseEntity.ok("Password changed successfully.");

    }

}
