package com.cthye.lil.spring101.configuration;

import com.cthye.lil.spring101.security.CthyeUserDetailsService;
import com.cthye.lil.spring101.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
//this enabled @Preauthorize annotation
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CthyeUserDetailsService userService;

    @Autowired
    public SecurityConfiguration(CthyeUserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/api/**","/apix/**").permitAll()
                //unit test
                .antMatchers("/reservations").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http.cors().and().csrf().disable();
        // No session will be created or used by spring security. However, since we have Web, we need sessions.
        // If its purely API/Webservices, we may not need sessions.
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //filter added to ensure Preauthorization role is evaluated before method is run
        http.addFilterBefore(new JwtTokenFilter(userService), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        // no longer using in memory database
//    inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("pass")).roles("USER", "ADMIN", "ENDPOINT_ADMIN");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
