package com.marketplace.onlinemarketplace.service;

import com.marketplace.onlinemarketplace.entity.RegisterRequest;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.repository.ClientProfileRepo;
import com.marketplace.onlinemarketplace.repository.FreelancerProfileRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String CheckUser(User user) {

        if(user.getRole() == User.Role.FREELANCER) {
            return freelancerProfileRepo.findByUser_Username(user.getUsername()) == null ? "Freelancer Profile is Required" : "Freelancer Profile Exists";
        }else if(user.getRole() == User.Role.CLIENT) {
            return clientProfileRepo.findByUser_Username(user.getUsername()) == null ? "Client Profile is Required" : "Client Profile Exists";
        }

        return "Inavlid User";
    }


}
