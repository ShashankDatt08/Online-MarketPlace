package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.LoginRequest;
import com.marketplace.onlinemarketplace.entity.RegisterRequest;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity loginRequest(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());

        if(user == null && !bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword()))  {
            return ResponseEntity.badRequest().body("Incorrect Password");
        }

        String profileStatus = userService.CheckUser(user);
        return ResponseEntity.ok("Login Successful");
    }


}
