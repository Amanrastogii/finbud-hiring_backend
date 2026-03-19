package com.financebuddha.finbud.controller;

import com.financebuddha.finbud.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth") // ✅ correct base path
@RequiredArgsConstructor
@CrossOrigin("*") // ✅ allow frontend requests
public class AuthController {

    private final JwtUtil jwtUtil;

    // 🔐 LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        // ✅ NULL SAFETY
        if (body == null) {
            return ResponseEntity.badRequest().body("Request body is missing");
        }

        // ✅ TRIM INPUT (VERY IMPORTANT FIX)
        String email = body.get("email") != null ? body.get("email").trim() : null;
        String password = body.get("password") != null ? body.get("password").trim() : null;

        // ✅ VALIDATION
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }

        // 🧪 DEBUG LOG (remove later in production)
        System.out.println("LOGIN ATTEMPT:");
        System.out.println("EMAIL: " + email);
        System.out.println("PASSWORD: " + password);

        // 🔥 TEMP HARDCODED LOGIN (FOR NOW)
        if ("admin@finbud.com".equals(email) && "admin123".equals(password)) {

            String token = jwtUtil.generateToken(email);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "message", "Login successful"
            ));
        }

        // ❌ INVALID LOGIN
        return ResponseEntity.status(401).body(Map.of(
                "message", "Invalid credentials"
        ));    }


    @GetMapping("/test")
    public String test() {
        return "WORKING";
    }
}