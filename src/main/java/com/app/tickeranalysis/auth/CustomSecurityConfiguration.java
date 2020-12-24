package com.app.tickeranalysis.auth;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static String REALM = "HG_REALM";

	private CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

	private PasswordEncoder passwordEncoder;

	public CustomSecurityConfiguration(CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint,
			PasswordEncoder passwordEncoder) {
		this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

		Properties users = PropertiesLoaderUtils.loadAllProperties("user.properties");
		for (Map.Entry<Object, Object> user : users.entrySet()) {
			String value = (String) user.getValue();
			String[] userCreds = value.split(":");
			auth.inMemoryAuthentication().withUser(userCreds[0]).password(passwordEncoder.encode(userCreds[1]))
					.authorities("ROLE_USER");
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers("/sampleAPI/analysis/**").hasAuthority("ROLE_USER").and()
				.httpBasic().realmName(REALM).authenticationEntryPoint(customBasicAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().antMatchers("/sampleAPI/**").permitAll().anyRequest().authenticated().and().httpBasic()
				.authenticationEntryPoint(customBasicAuthenticationEntryPoint);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

}
