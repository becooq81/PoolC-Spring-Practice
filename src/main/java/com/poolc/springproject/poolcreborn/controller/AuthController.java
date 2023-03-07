package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.ERole;
import com.poolc.springproject.poolcreborn.model.Role;
import com.poolc.springproject.poolcreborn.model.User;
import com.poolc.springproject.poolcreborn.payload.request.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.LoginRequest;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("Email is already taken.", HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(signupRequest);

        return ResponseEntity.ok("User registered successfully!");
    }

}
