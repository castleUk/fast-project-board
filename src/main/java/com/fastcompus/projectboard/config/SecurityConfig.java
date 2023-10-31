package com.fastcompus.projectboard.config;

//SpringBoot2.7 부터는 이런식으로 Security설정을 진행해야 한다.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity (스프링부트에서 시큐리티 쓸때는 안넣어도 됨)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .formLogin().and()
            .build();

    }


}
