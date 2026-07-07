package com.sparkCoder.raktbhet.tokenutility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

        @Autowired
        private JWTStuff jwtStuff;

        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String requestUri = request.getRequestURI();
            logger.debug("[JwtAuthenticationFilter] Processing request: {} {}", request.getMethod(), requestUri);

            // Skip JWT authentication for login and registration endpoints
            if (requestUri.contains("/auth/login") || requestUri.contains("/auth/register") || 
                requestUri.contains("/donors/save") || requestUri.contains("/swagger-ui") || 
                requestUri.contains("/v3/api-docs")) {
                logger.debug("[JwtAuthenticationFilter] Skipping JWT validation for: {}", requestUri);
                filterChain.doFilter(request, response);
                return;
            }

            String requestHeader = request.getHeader("Authorization");
            logger.debug("[JwtAuthenticationFilter] Authorization header: {}", requestHeader);
            
            String username = null;
            String token = null;
            
            if (requestHeader != null && requestHeader.startsWith("Bearer")) {
                token = requestHeader.substring(7);
                logger.debug("[JwtAuthenticationFilter] Extracted JWT token from Authorization header");
                
                try {
                    username = this.jwtStuff.getUsernameFromToken(token);
                    logger.info("[JwtAuthenticationFilter] Extracted username from token: {}", username);

                } catch (IllegalArgumentException e) {
                    logger.warn("[JwtAuthenticationFilter] Illegal Argument while fetching the username: {}", e.getMessage());
                } catch (ExpiredJwtException e) {
                    logger.warn("[JwtAuthenticationFilter] JWT token is expired");
                } catch (MalformedJwtException e) {
                    logger.warn("[JwtAuthenticationFilter] JWT token is malformed or tampered");
                } catch (Exception e) {
                    logger.warn("[JwtAuthenticationFilter] Exception while parsing JWT token: {}", e.getMessage());
                }

            } else {
                logger.debug("[JwtAuthenticationFilter] Invalid or missing Authorization header");
            }

            // Set authentication if token is valid
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.info("[JwtAuthenticationFilter] Validating JWT token for username: {}", username);

                try {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    logger.debug("[JwtAuthenticationFilter] Loaded UserDetails for username: {}", username);
                    
                    Boolean validateToken = this.jwtStuff.validateToken(token, userDetails);
                    
                    if (validateToken) {
                        logger.info("[JwtAuthenticationFilter] JWT token validated successfully for: {}", username);
                        
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        
                        logger.info("[JwtAuthenticationFilter] Authentication set in SecurityContextHolder for: {}", username);

                    } else {
                        logger.warn("[JwtAuthenticationFilter] JWT token validation failed for: {}", username);
                    }
                } catch (Exception e) {
                    logger.error("[JwtAuthenticationFilter] Error during token validation: {}", e.getMessage(), e);
                }
            }

            filterChain.doFilter(request, response);
        }
}


