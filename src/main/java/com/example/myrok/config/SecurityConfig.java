package com.example.myrok.config;


import com.example.myrok.domain.MemberRole;
import com.example.myrok.oauth2.CustomSuccessHandler;
import com.example.myrok.repository.RefreshRepository;
import com.example.myrok.service.CustomOauth2UserService;
import com.example.myrok.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity
//@EnableWebSecurity
public class SecurityConfig {


    private final CustomOauth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RefreshRepository refreshRepository;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("---------------------security config---------------------------");

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/oauth-login/admin").hasRole(MemberRole.ADMIN.name())
                        .requestMatchers("/oauth-login/info").authenticated()
                        .anyRequest().permitAll()
                );

        http
                .formLogin((auth) -> auth.loginPage("/oauth-login/login")
                        .loginProcessingUrl("/oauth-login/loginProc")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/oauth-login")
                        .failureUrl("/oauth-login")
                        .permitAll());

        http
                .oauth2Login((auth) -> auth.loginPage("/oauth-login/login")
                        .defaultSuccessUrl("/oauth-login")
                        .failureUrl("/oauth-login/login")
                        .permitAll());

        http
                .logout((auth) -> auth
                        .logoutUrl("/oauth-login/logout"));

        http
                .csrf((auth) -> auth.disable());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
