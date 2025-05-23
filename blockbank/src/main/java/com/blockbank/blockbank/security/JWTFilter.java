package com.blockbank.blockbank.security;

import com.blockbank.blockbank.service.impl.UserDetailsServiceImpl;
import com.blockbank.blockbank.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JWTFilter(JWTUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JWT FILTER TETİKLENDİ " + request.getRequestURI());
        System.out.println("Authorization header: " + request.getHeader(HttpHeaders.AUTHORIZATION));

        String token = extractToken(request);
        System.out.println("token: " + token);
        if (token != null && jwtUtil.validateToken(token)) {
            try {
                String username = jwtUtil.extractUsername(token);
                String ethAddress = jwtUtil.extractEthAddress(token);
                String userId = jwtUtil.extractUserId(token);

                BlockbankUserDetails userDetails = new BlockbankUserDetails(
                        username,
                        ethAddress,
                        userId,
                        List.of() // roller varsa buraya
                );

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                System.out.println("JWT geçerli, kullanıcı bağlandı: " + username);
            } catch (Exception e) {
                System.err.println("JWT doğrulama hatası: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token geçersiz.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring("Bearer ".length());
        }
        return null;
    }

}
