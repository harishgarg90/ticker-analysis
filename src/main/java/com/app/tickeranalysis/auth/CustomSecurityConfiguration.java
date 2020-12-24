package com.app.tickeranalysis.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.tickeranalysis.config.UserCredentialsConfig;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static String REALM="MY_TEST_REALM";
	@Autowired
	private UserCredentialsConfig userCredentialsConfig;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        String[] user1Creds = userCredentialsConfig.getUser1().split(":");
        String[] user2Creds = userCredentialsConfig.getUser2().split(":");
        
    	auth.inMemoryAuthentication().withUser(user1Creds[0])
        .password(passwordEncoder().encode(user1Creds[1]))
        .authorities("ROLE_USER");
        
        auth.inMemoryAuthentication().withUser(user2Creds[0])
        .password(passwordEncoder().encode(user2Creds[1]))
        .authorities("ROLE_USER");
    }
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  
    	http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/sampleAPI/analysis/**").hasAuthority("ROLE_USER")
        .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	
    	
      http.authorizeRequests()
        .antMatchers("/sampleAPI/**").permitAll().anyRequest().authenticated()
        .and().httpBasic().authenticationEntryPoint(getBasicAuthEntryPoint());
    
    }
     
    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
    
}
