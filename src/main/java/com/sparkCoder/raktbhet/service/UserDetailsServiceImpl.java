package com.sparkCoder.raktbhet.service;

import com.sparkCoder.raktbhet.entity.AllDataEntity;
import com.sparkCoder.raktbhet.repository.AllDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

        @Autowired
        private AllDataRepository allDataRepository;


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            logger.info("[UserDetailsServiceImpl] Loading user from database with username: {}", username);
            
            // First try: Load single user (expected behavior after fix)
            try {
                AllDataEntity user = allDataRepository.findByUserName(username)
                        .orElseThrow(() -> {
                            logger.error("[UserDetailsServiceImpl] User not found in database: {}", username);
                            return new UsernameNotFoundException("User not found: " + username);
                        });
                
                logger.info("[UserDetailsServiceImpl] User found in database: {}", username);
                logger.info("[UserDetailsServiceImpl] User role: {}", user.getRole());
                logger.info("[UserDetailsServiceImpl] User authorities: {}", user.getAuthorities());
                logger.info("[UserDetailsServiceImpl] User ID: {}", user.getId());
                
                return user;
                
            } catch (IllegalArgumentException e) {
                // This happens when multiple results are returned
                logger.warn("[UserDetailsServiceImpl] Multiple users found with username: {}", username);
                logger.warn("[UserDetailsServiceImpl] This indicates duplicate usernames in the database");
                
                // Fallback: Get all and use latest (by ID)
                List<AllDataEntity> users = allDataRepository.findAllByUserName(username);
                
                if (users.isEmpty()) {
                    logger.error("[UserDetailsServiceImpl] No users found with username: {}", username);
                    throw new UsernameNotFoundException("User not found: " + username);
                }
                
                if (users.size() > 1) {
                    logger.error("[UserDetailsServiceImpl] Found {} users with duplicate username: {}", users.size(), username);
                    logger.error("[UserDetailsServiceImpl] This should not happen after applying the fix. Delete duplicate usernames from database.");
                }
                
                // Use the user with highest ID (latest)
                AllDataEntity user = users.stream()
                        .max((u1, u2) -> u1.getId().compareTo(u2.getId()))
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
                
                logger.warn("[UserDetailsServiceImpl] Using latest user record (ID: {}) for authentication", user.getId());
                logger.info("[UserDetailsServiceImpl] User role: {}", user.getRole());
                
                return user;
            }
        }

    }
