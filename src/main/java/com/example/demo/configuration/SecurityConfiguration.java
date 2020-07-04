package com.example.demo.configuration;

import com.example.demo.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private String[] urls = {
            "/webjars/**",
            "/css/**",
            "/img/**",
            "/js/**",
            "/**/favicon.ico",
            "/error",
            "/403",
            "/fonts/**"
    };

    public SecurityConfiguration(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return username -> this.userService.findByUsername(username);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(urls);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/student/download").
                hasAnyRole("INSTRUCTOR", "STUDENT").
                antMatchers("/admin/**").
                hasAnyRole("ADMIN").
                antMatchers("/instructor/**").
                hasAnyRole("INSTRUCTOR").
                antMatchers("/student/**").
                hasAnyRole("STUDENT").
                anyRequest().
                authenticated().
                and().
                formLogin().
                loginPage("/login").
                defaultSuccessUrl("/").
                permitAll().
                and().
                logout().
                logoutSuccessUrl("/").
                permitAll().
                and().
                rememberMe().
                and().
                exceptionHandling().
                accessDeniedPage("/403");
    }
}
