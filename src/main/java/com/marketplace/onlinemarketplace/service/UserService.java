package com.marketplace.onlinemarketplace.service;

import com.marketplace.onlinemarketplace.Jwt.JwtToken;
import com.marketplace.onlinemarketplace.Jwt.JwtUtil;
import com.marketplace.onlinemarketplace.entity.RegisterRequest;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.mongoRepo.JwtRepo;
import com.marketplace.onlinemarketplace.repository.ClientProfileRepo;
import com.marketplace.onlinemarketplace.repository.FreelancerProfileRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ClientProfileRepo clientProfileRepo;

    @Autowired
    private FreelancerProfileRepo freelancerProfileRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtRepo jwtRepo;

    public User registerUser(RegisterRequest userReq) {
        String encodedPassword = bCryptPasswordEncoder.encode(userReq.getPassword());
        User user = new User();
        user.setUsername(userReq.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(userReq.getEmail());
        user.setRole(User.Role.valueOf(userReq.getRole().toUpperCase()));
        user.setFirstname(userReq.getFirstname());
        user.setLastname(userReq.getLastname());
        user = userRepo.save(user);
        return user;
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    public String login(String email, String password) {

        User user = userRepo.findByEmail(email);
        if (user == null || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);
        return token;

    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public boolean isTokenValidForLogout(String token) {
        JwtToken jwtToken = jwtRepo.findByToken(token).orElse(null);

        boolean isValid = jwtToken != null && !jwtToken.isRevoked() && !jwtToken.isExpired();
        return isValid;
    }

    public void logout(String token) {
        jwtUtil.revokeToken(token);
    }

    public void changePassword(String email, String newPassword, String confirmPassword) {

        User user = userRepo.findByEmail(email);

        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }
}
