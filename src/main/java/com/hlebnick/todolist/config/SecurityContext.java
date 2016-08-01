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
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityContext extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService = new CustomUserDetailsService();

//    @Autowired
//    PersistentTokenRepository tokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//      <security:intercept-url pattern="/register" access="isAnonymous()"/>
//      <security:intercept-url pattern="/auth/login" access="isAnonymous()"/>

//      authentication-failure-url="/auth/login?error=true"

        http.authorizeRequests()
                .antMatchers("/").hasAnyAuthority()
                .antMatchers("/auth/login").permitAll();
        http.formLogin().loginPage("/auth/login");
        http.logout().invalidateHttpSession(true).logoutSuccessUrl("/").logoutUrl("/logout");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder,
                                BCryptPasswordEncoder passwordEncoder) throws Exception {
        authBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*    @Bean
    public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
        return new PersistentTokenBasedRememberMeServices("remember-me", userDetailsService, tokenRepository);
    }*/
}
