package com.zhao.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("user").password("guest").roles("USER").and()
        .withUser("admin").password("admin").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/login")
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .logoutUrl("/logout")
                .and()
                .rememberMe()
                    .tokenRepository(new InMemoryTokenRepositoryImpl())
                    .tokenValiditySeconds(2419200)
                    .key("blogKey")
                .and()
                .httpBasic()
                    .realmName("blog")
                .and()
                .authorizeRequests()
                    .antMatchers("/").authenticated()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().permitAll();
    }
}
