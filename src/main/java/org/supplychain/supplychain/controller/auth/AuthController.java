package org.supplychain.supplychain.controller.auth;

import lombok.RequiredArgsConstructor;
import org.supplychain.supplychain.enums.Role;
import org.supplychain.supplychain.model.User;
import org.supplychain.supplychain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User created successfully!";
    }

    @GetMapping("/login")
    public String login() {
        return "Login successful!";
    }
}
