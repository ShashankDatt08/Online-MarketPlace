import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
public class RegistrationController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        if (oldPassword == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Invalid input");
        }

        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid current password");
        }

        if (newPassword.length() < 8) {
            return ResponseEntity.badRequest().body("New password is too weak");
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }
}