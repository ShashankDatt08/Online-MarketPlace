import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            if (newPassword.length() < 8) {
                return ResponseEntity.badRequest().body("New password is too weak. It should be at least 8 characters long.");
            }
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Old password is incorrect.");
        }
    }
}