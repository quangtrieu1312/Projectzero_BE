package com.trieutruong.webpage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.trieutruong.webpage.filter.JwtAuthenticationFilter;
import com.trieutruong.webpage.service.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userService;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService) 
				.passwordEncoder(encoder()); 
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http .csrf().disable()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/signUp").permitAll()
				.antMatchers("/login").permitAll() 
				.antMatchers("/activate").permitAll()
				.antMatchers("/sendActivateToken").permitAll()
				.antMatchers("/testUser").hasRole("USER")
				.antMatchers("/testAdmin").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/")
				.deleteCookies("AUTH_JWT","JSESSIONID")
				.invalidateHttpSession(true)
				;
	}

}