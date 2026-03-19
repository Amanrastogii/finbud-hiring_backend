package com.financebuddha.finbud.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath(); // ✅ CORRECT

        // ✅ PUBLIC ROUTES (VERY IMPORTANT)
        if (
                path.startsWith("/auth") || // login APIs
                        (path.startsWith("/applications") && request.getMethod().equals("POST")) || // form submit
                        request.getMethod().equals("OPTIONS") // CORS preflight
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // 🔐 CHECK AUTH HEADER
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing Authorization header");
            return;
        }

        try {
            String token = header.substring(7);
            String user = jwtUtil.validateToken(token);

            if (user == null) {
                throw new RuntimeException("Invalid token");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}