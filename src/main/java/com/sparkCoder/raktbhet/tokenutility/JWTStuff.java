package com.sparkCoder.raktbhet.tokenutility;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTStuff {

        private static final Logger logger = LoggerFactory.getLogger(JWTStuff.class);
        
        public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
        private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCkasdvFasdvhj";

        //retrieve username from jwt token
        public String getUsernameFromToken(String token) {
            logger.debug("[JWTStuff] Extracting username from token");
            return getClaimFromToken(token, Claims::getSubject);
        }

        //retrieve expiration date from jwt token
        public Date getExpirationDateFromToken(String token) {
            logger.debug("[JWTStuff] Extracting expiration date from token");
            return getClaimFromToken(token, Claims::getExpiration);
        }

        public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        }

        //for retrieveing any information from token we will need the secret key
        private Claims getAllClaimsFromToken(String token) {
            logger.debug("[JWTStuff] Parsing token claims using secret key");
            return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
        }

        //check if the token has expired
        private Boolean isTokenExpired(String token) {
            final Date expiration = getExpirationDateFromToken(token);
            boolean expired = expiration.before(new Date());
            logger.debug("[JWTStuff] Token expiration check: expired={}", expired);
            return expired;
        }

        //generate token for user
        public String generateToken(UserDetails userDetails) {
            logger.info("[JWTStuff] Generating JWT token for username: {}", userDetails.getUsername());
            Map<String, Object> claims = new HashMap<>();
            return doGenerateToken(claims, userDetails.getUsername());
        }

        //while creating the token -
        //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
        //2. Sign the JWT using the HS512 algorithm and secret key.
        //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
        //   compaction of the JWT to a URL-safe string
        private String doGenerateToken(Map<String, Object> claims, String subject) {
            logger.info("[JWTStuff] Creating JWT token for subject: {}", subject);
            logger.info("[JWTStuff] Token validity: {} seconds", JWT_TOKEN_VALIDITY);
            
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
            
            logger.debug("[JWTStuff] JWT token generated successfully (first 50 chars): {}...", token.substring(0, Math.min(50, token.length())));
            return token;
        }

        //validate token
        public Boolean validateToken(String token, UserDetails userDetails) {
            logger.info("[JWTStuff] Validating JWT token for username: {}", userDetails.getUsername());
            
            final String username = getUsernameFromToken(token);
            logger.debug("[JWTStuff] Token username: {}", username);
            logger.debug("[JWTStuff] Expected username: {}", userDetails.getUsername());
            
            boolean usernameMatch = username.equals(userDetails.getUsername());
            boolean notExpired = !isTokenExpired(token);
            boolean isValid = usernameMatch && notExpired;
            
            logger.info("[JWTStuff] Token validation result: usernameMatch={}, notExpired={}, isValid={}", usernameMatch, notExpired, isValid);
            
            return isValid;
        }
}

