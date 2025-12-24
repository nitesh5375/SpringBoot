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
/*
JWT authentication is STATELESS (memorize this)
In JWT / token-based authentication:
Server does NOT remember users
Server does NOT remember login
Every request must prove its identity again
That proof = JWT token
 */

// Any spring boot project which extends 'OncePerRequestFilter' abstract class, will be redirected all incoming request
// to shouldNotFilter and doFilterInternal method.
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

            String token = header.substring(7); // remove "Bearer " and store the token
            String username = jwtUtil.extractUsername(token);   //extract username from token

            /* if username and context is null, means this user is not authenticated before.
            The check is NOT for: “Has this user already logged in before?”
            The check IS for: “Has this request already been authenticated earlier in the filter chain?”
            Imagine:
                Multiple security filters
                Or a request forwarded internally
                Or filter chain executed twice
                Without this check:
                Authentication could be overwritten which will create Performance issue and Security bugs might occure
             */
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

                    // Store authentication in SecurityContext for this user, so that next time validation should be skipped
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }
        }

        /* It passes the same HTTP request and response to the next filter in the Spring Security Filter Chain (or to the controller if no filters remain).
        Authentication is now available in SecurityContext
        The request continues through remaining filters
        Authorization checks can now be performed
        If doFilter() is not called:
            Request processing stops
            Controller is never invoked
            Client gets no response (or gets error)
         */
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
