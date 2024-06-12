package com.example.rptlvks.Config;

import com.example.rptlvks.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(hd -> {
                    hd.frameOptions(fo -> {
                        fo.sameOrigin();
                    });
                })
                .csrf(cs -> cs.disable())
                .formLogin(f -> {
                    f
                            .loginPage("/login")
                            .permitAll()
                            .usernameParameter("id")
                            .passwordParameter("password")
                            .loginProcessingUrl("/loginpro")
                            .defaultSuccessUrl("/")
                            .failureUrl("/loginFail");
                })
                .logout(l -> {
                    l.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                })
                .httpBasic(h -> {
                    h.disable();
                });
        http.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/css/**","/js/**","/image/**","/loginpro","/register","/checkId","/submit_registration","/","/mailCheck","/mailSend","/completeRegister","/login","/loginFail","/noticeIndexCheck").permitAll();
            authorize.requestMatchers("/**","/goWrite","/writePage").hasRole("USER");
            authorize.requestMatchers("/**").hasRole("ADMIN");
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}