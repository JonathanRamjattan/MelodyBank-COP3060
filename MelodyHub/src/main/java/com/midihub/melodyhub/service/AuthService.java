package com.midihub.melodyhub.service;

import com.midihub.melodyhub.dto.AuthResponse;
import com.midihub.melodyhub.dto.LoginRequest;
import com.midihub.melodyhub.dto.RegisterRequest;
import com.midihub.melodyhub.entity.AppUser;
import com.midihub.melodyhub.repository.AppUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request, HttpSession session) {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new RuntimeException("Username is required.");
        }

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("Email is required.");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("Password is required.");
        }

        if (appUserRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }

        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreator(request.isCreator());

        AppUser savedUser = appUserRepository.save(user);

        session.setAttribute("userId", savedUser.getId());

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.isCreator(),
                "Account created successfully."
        );
    }

    public AuthResponse login(LoginRequest request, HttpSession session) {
        AppUser user = appUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password.");
        }

        session.setAttribute("userId", user.getId());

        return new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isCreator(),
                "Login successful."
        );
    }

    public AuthResponse getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("Not logged in.");
        }

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        return new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isCreator(),
                "Logged in."
        );
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}