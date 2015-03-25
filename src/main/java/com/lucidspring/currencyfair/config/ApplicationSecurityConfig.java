package com.lucidspring.currencyfair.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

/**
 * Configure spring security rules and add in memory authentication
 */

@EnableWebSecurity
@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()  // Refactor login form

				// See https://jira.springsource.org/browse/SPR-11496
				.headers().addHeaderWriter(
				new XFrameOptionsHeaderWriter(
						XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)).and()

				.formLogin()
					.defaultSuccessUrl("/")
					.loginPage("/login")
					.failureUrl("/login?error")
					.permitAll()
					.and()
				.logout()
					.logoutSuccessUrl("/login?logout")
//				.logoutUrl("/logout.html")
					.permitAll()
					.and()
				.authorizeRequests()
					.antMatchers("/css/**").permitAll()
					.antMatchers("/js/**").permitAll()
                    .antMatchers("/sendTrade").permitAll()
					.anyRequest().authenticated()
					.and();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.inMemoryAuthentication()
				.withUser("test").password("currencyfair").roles("USER").and();
	}
}
