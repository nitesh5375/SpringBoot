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
        //Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJuaXRlc2giLCJpYXQiOjE3MTAwMDAwMDAsImV4cCI6MTcxMDAwMzYwMH0.Vp6y9H4kQnT5zRkL0wA1m8B9XyJp0Q
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            // remove "Bearer " and store the token
            // any space after "Authorization:" will be removed and only starting with "Bearer..." will be saved in the token
            String token = header.substring(7);

            try {
                // extract username from token
                String username = jwtUtil.extractUsername(token);

            /*
             if username is present and SecurityContext is null, it means
             THIS REQUEST has not been authenticated yet.

             The check is NOT for:
             "Has this user already logged in before?"

             The check IS for:
             "Has this request already been authenticated earlier in the filter chain?"

             This is important in cases like:
                - Multiple security filters
                - Internal request forwarding
                - Filter chain being executed more than once

             Without this check:
                - Authentication may be overwritten
                - Performance issues
                - Potential security bugs
             */
                if (username != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Load user details from database
                    UserDetails userDetails =
                            userDetailsService.loadUserByUsername(username);

                    // Validate token (username match + expiration + signature)
                    boolean tokenValid = jwtUtil.validateToken(token, userDetails);

                    if (tokenValid) {

                    /*
                     This constructor with authorities MARKS the user
                     as authenticated for THIS REQUEST.
                     */
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );

                    /*
                     Store authentication in SecurityContext so that:
                        - Controllers can access the authenticated user
                        - @AuthenticationPrincipal works
                        - Authorization checks can be performed

                     NOTE:
                     This authentication is valid ONLY for the current request.
                     It will be cleared automatically once the request completes.
                     */
                        SecurityContextHolder.getContext()
                                .setAuthentication(authentication);
                    }

                }

            } catch (Exception e) {
                // Token is invalid, expired, or malformed
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid or expired JWT token"
                );
                return;
            }
        }

    /*
     Passes the same HTTP request and response to the next filter
     in the Spring Security Filter Chain (or to the controller if no filters remain).

     At this point:
        - Authentication (if valid) is available in SecurityContext
        - Remaining security filters can perform authorization
        - Controller method can be invoked

     If doFilter() is NOT called:
        - Request processing stops
        - Controller is never invoked
        - Client receives no valid response
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
