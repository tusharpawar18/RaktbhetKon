package com.sparkCoder.raktbhet.service;

import com.sparkCoder.raktbhet.entity.AllDataEntity;
import com.sparkCoder.raktbhet.repository.AllDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


        @Autowired
        private AllDataRepository allDataRepository;


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            AllDataEntity user= allDataRepository.findByUserName(username).orElseThrow(()->new RuntimeException("User not found"));
            return user;
        }

    }
