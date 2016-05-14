package com.mr.springmvc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
 
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;
 
    @Autowired
    CustomSuccessHandler customSuccessHandler;
     
     
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    
//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("a").password("a").roles("USER");
//        auth.inMemoryAuthentication().withUser("2").password("2").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("2").password("3").roles("ADMIN","DBA");
//    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       
      http.authorizeRequests()
        //.antMatchers("/", "/home").permitAll()
        .antMatchers("/").access("hasRole('ADMIN')")
        //.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
        .and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
        .usernameParameter("username").passwordParameter("password")
        .and().csrf()
        .and().exceptionHandling().accessDeniedPage("/Access_Denied");
    }
 
}