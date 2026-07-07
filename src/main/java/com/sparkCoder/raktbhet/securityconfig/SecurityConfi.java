package com.sparkCoder.raktbhet.securityconfig;

import com.sparkCoder.raktbhet.service.UserDetailsServiceImpl;
import com.sparkCoder.raktbhet.tokenutility.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfi {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfi.class);

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        logger.info("[SecurityConfi] Creating BCryptPasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(BCryptPasswordEncoder passwordEncoder) {
        logger.info("[SecurityConfi] Creating DaoAuthenticationProvider with UserDetailsServiceImpl");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        logger.info("[SecurityConfi] DaoAuthenticationProvider configured successfully");
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, 
                                                       DaoAuthenticationProvider authenticationProvider) throws Exception {
        logger.info("[SecurityConfi] Creating AuthenticationManager");
        AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        logger.info("[SecurityConfi] AuthenticationManager created with custom DaoAuthenticationProvider");
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain getSecurity(HttpSecurity http) throws Exception {
        logger.info("[SecurityConfi] Configuring SecurityFilterChain");

        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/donors/save").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}