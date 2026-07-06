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

            this.doAuthenticate(request.getUsername(), request.getPassword());


            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = this.jwtStuff.generateToken(userDetails);

            JWTRespose response = JWTRespose.builder()
                    .token(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        private void doAuthenticate(String username, String password) {

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
            try {
                manager.authenticate(authentication);

            } catch (BadCredentialsException e) {
                throw new BadCredentialsException(" Invalid Username or Password  !!");
            }
            catch (Exception e) {
              //  System.out.println("In invalid username or password");
                throw  new RuntimeException("Bad Credentials !!",e);
            }

        }
    }


