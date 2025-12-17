package com.self.SpringJDBCdemo.security;

import com.self.SpringJDBCdemo.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Every HTTP request enters the Spring Security Filter Chain
// This filter runs BEFORE the controller
//
// Client
//   ↓
// Spring Security Filter Chain
//   ↓
// DispatcherServlet
//   ↓
// Controller
//
// OncePerRequestFilter guarantees that this filter is executed
// ONLY ONCE per request (important for security correctness)
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /*
        Flow inside this filter:

        1. Read Authorization header
        2. Extract JWT token
        3. Extract username from token
        4. Load UserDetails from DB
        5. Validate token (signature + expiry)
        6. Create Authentication object
        7. Store Authentication in SecurityContext
        8. Continue filter chain
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Authorization: Bearer <token>
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7); // remove "Bearer "
            String username = jwtUtil.extractUsername(token);

            // Only set authentication if it is not already set
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user from database
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                // Validate token (username match + expiration)
                if (jwtUtil.validateToken(token, userDetails)) {

                    // This constructor with authorities MARKS the user as authenticated
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // Store authentication in SecurityContext
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }
        }

        // Always continue the filter chain
        filterChain.doFilter(request, response);
    }

    /*
        This method decides WHETHER this filter should run for a request.

        We want to SKIP JWT validation ONLY for authentication endpoints
        like /auth/login and /auth/register.

        All other endpoints MUST pass through this filter.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        // Skip JWT filter only for auth endpoints
        return path.startsWith("/auth/");
    }
}
