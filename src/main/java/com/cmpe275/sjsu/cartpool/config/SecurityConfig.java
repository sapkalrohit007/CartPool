package com.cmpe275.sjsu.cartpool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cmpe275.sjsu.cartpool.security.CustomUserDetailsService;
import com.cmpe275.sjsu.cartpool.security.RestAuthenticationEntryPoint;
import com.cmpe275.sjsu.cartpool.security.TokenAuthenticationFilter;

import org.springframework.security.config.BeanIds;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	CustomUserDetailsService customerDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customerDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.csrf()
				.disable()
			.formLogin()
				.disable()
			.httpBasic()
				.disable()
			.exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
			.authorizeRequests()
				.antMatchers("/auth/**","/user/**","/pool/confirm-request-owner/**","/pool/confirm-request/**","/reject-request/**")
					.permitAll()
				.anyRequest()
					.authenticated();
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
        
    }
    
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
    
}
