package com.poolc.springproject.poolcreborn.security;

import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.security.jwt.AuthTokenFilter;
import com.poolc.springproject.poolcreborn.security.jwt.JwtAuthEntryPoint;
import com.poolc.springproject.poolcreborn.security.service.UserDetailsServiceImpl;
import com.poolc.springproject.poolcreborn.util.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthEntryPoint unauthorizedHandler;
    private final UserRepository userRepository;

    @Bean
    public CustomMapper customMapper() { return new CustomMapper(); }
    @Bean
    public  UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                    // 멤버는 모두 자기 페이지 접근 가능
                    .antMatchers("/my-info").hasAnyAuthority("ROLE_CLUB_MEMBER", "ROLE_ADMIN", "ROLE_USER")
                    .antMatchers(HttpMethod.GET, "/book", "/book/**")
                        .hasAnyAuthority("ROLE_CLUB_MEMBER", "ROLE_ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/book/**")
                        .hasAnyAuthority("ROLE_ADMIN")
                    .antMatchers("/activity/new", "/activity/**/participants", "/activity/**/participants/**", "/library", "/library/**")
                        .hasAnyAuthority("ROLE_CLUB_MEMBER", "ROLE_ADMIN")
                    .antMatchers("/admin", "/admin/**", "/book/new")
                        .hasAnyAuthority("ROLE_ADMIN")
                    .antMatchers("/login/confirm/mail").hasAnyAuthority("ROLE_USER")
                    .antMatchers("/login", "/signup", "/activity", "/activity/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .securityContext((securityContext) -> securityContext
                        .securityContextRepository(new DelegatingSecurityContextRepository(
                                new RequestAttributeSecurityContextRepository(),
                                new HttpSessionSecurityContextRepository()
                        ))
                );


        http.authenticationProvider(authenticationProvider());

        http.userDetailsService(userDetailsService());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


}
