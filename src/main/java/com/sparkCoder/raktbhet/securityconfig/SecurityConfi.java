package com.sparkCoder.raktbhet.securityconfig;

import com.sparkCoder.raktbhet.tokenutility.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfi {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain getSecurity(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request ->
                        request.anyRequest().permitAll()
                        /*request.requestMatchers(HttpMethod.POST, "/donors/save","/auth/login").permitAll()
                                //requestMatchers(HttpMethod.POST).permitAll()
                                .requestMatchers(HttpMethod.GET, "/donors/get/***").hasRole("ADMIN")
                                .anyRequest().authenticated()


                         */
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();


    }


}





