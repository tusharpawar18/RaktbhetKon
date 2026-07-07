package com.sparkCoder.raktbhet.controller;

import com.sparkCoder.raktbhet.dto.JWTRequest;
import com.sparkCoder.raktbhet.dto.JWTRespose;
import com.sparkCoder.raktbhet.tokenutility.JWTStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private AuthenticationManager manager;

        @Autowired
        private JWTStuff jwtStuff;

        private Logger logger = LoggerFactory.getLogger(AuthController.class);


        @PostMapping("/login")
        public ResponseEntity<JWTRespose> login(@RequestBody JWTRequest request) {
            logger.info("[AuthController] /login endpoint called");
            logger.info("[AuthController] Username: {}", request.getUsername());
            
            try {
                this.doAuthenticate(request.getUsername(), request.getPassword());
                logger.info("[AuthController] Authentication successful for username: {}", request.getUsername());

                UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
                logger.info("[AuthController] UserDetails loaded: {}", userDetails.getUsername());
                
                String token = this.jwtStuff.generateToken(userDetails);
                logger.info("[AuthController] JWT token generated successfully");

                JWTRespose response = JWTRespose.builder()
                        .token(token)
                        .username(userDetails.getUsername()).build();
                
                logger.info("[AuthController] Returning token for username: {}", response.getUsername());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                logger.error("[AuthController] Login failed for username: {}", request.getUsername(), e);
                throw e;
            }
        }

        private void doAuthenticate(String username, String password) {
            logger.info("[AuthController] doAuthenticate called for username: {}", username);
            logger.info("[AuthController] AuthenticationManager type: {}", manager.getClass().getName());
            
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
            logger.info("[AuthController] Created UsernamePasswordAuthenticationToken");
            
            try {
                logger.info("[AuthController] Calling manager.authenticate()");
                manager.authenticate(authentication);
                logger.info("[AuthController] Authentication succeeded");

            } catch (BadCredentialsException e) {
                logger.error("[AuthController] BadCredentialsException: Invalid Username or Password");
                throw new BadCredentialsException(" Invalid Username or Password  !!");
            }
            catch (Exception e) {
                logger.error("[AuthController] Exception during authentication: {}", e.getMessage(), e);
                throw new RuntimeException("Bad Credentials !!", e);
            }
        }
    }


