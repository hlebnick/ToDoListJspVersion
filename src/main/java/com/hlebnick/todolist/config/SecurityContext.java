package com.hlebnick.todolist.config;

import com.hlebnick.todolist.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityContext extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//      <security:intercept-url pattern="/register" access="isAnonymous()"/>
//      <security:intercept-url pattern="/auth/login" access="isAnonymous()"/>

//      authentication-failure-url="/auth/login?error=true"

        http.authorizeRequests().antMatchers("/**").permitAll();
        http.formLogin().loginPage("/signin");
        http.logout().invalidateHttpSession(true).logoutSuccessUrl("/").logoutUrl("/signout");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder,
                                BCryptPasswordEncoder passwordEncoder) throws Exception{
        authBuilder.userDetailsService(new CustomUserDetailsService())
                .passwordEncoder(passwordEncoder);
    }

    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
