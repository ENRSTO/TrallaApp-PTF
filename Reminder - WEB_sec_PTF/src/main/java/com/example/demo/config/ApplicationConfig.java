package com.example.demo.config;



import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.security.SecurityUserDetailsService;

import ch.qos.logback.core.util.Duration;
import org.springframework.web.filter.GenericFilterBean;

import com.example.demo.config.AjaxTimeoutRedirectFilter;

@Configuration 
@EnableWebSecurity
public class ApplicationConfig extends WebSecurityConfigurerAdapter  {

	@Bean
	public UserDetailsService userDetailsService() {
	        return new SecurityUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	       return new BCryptPasswordEncoder();
	}
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
	
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	  
	@Override   //CLASSE PER DEFINIRE LE REGOLE DI AUTIRIZZAZIONE	
	protected void configure(HttpSecurity http) throws Exception {
		

//		 http
//         .headers()
//         .frameOptions().sameOrigin()
//     .and()
//         .authorizeRequests()
//         .antMatchers("/**/save", "/**/saveAmm", "/**/evadi", "/**/modifica").authenticated()
//         .antMatchers("/**").permitAll()
//     .and()
//         .formLogin()         
//         .loginPage("/login").permitAll()  //CARICAMENTO PAGINA LOGIN PERSONALIZZATA
//         .and()
//         .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")//.permitAll() al Logout ricarica la pagina del login per nuova autenticazione
//         .and().	     
//         csrf().disable().cors();
//        
     
    // .and().exceptionHandling().authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/login"));
//------------------------------------------------------------------------------------------------------------------------------		
		
		 http
		  .headers()
          .frameOptions().sameOrigin()
         .and().		 
		 authorizeRequests()
		    .antMatchers("/**/save", "/**/saveAmm", "/**/saveScad", "/**/evadi", "/**/modifica", "/**/anagraficlienti","/**/saveC","/**/eliminacliente").authenticated()
	        .antMatchers("/*.ico","/*.js").permitAll()
	        .anyRequest().authenticated()
	        .and()	         
	    .formLogin()
	        .loginPage("/login").permitAll()  //CARICAMENTO PAGINA LOGIN PERSONALIZZATA
	        .and()
	        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")//.permitAll() al Logout ricarica la pagina del login per nuova autenticazione
	        .and().	       // .cors();
	     csrf().disable().cors()
	    .and().exceptionHandling().authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/login")); 
	         
	         // QUESTO PERMETTE ALLE CHIAMATE AJAX DI AGIRE SENZA ESSERE BLOCCATE.
	    //------------------------------------------------------------------------------------------------------------------------------		
		
		
//		//buono
//	    
//	    http.authorizeRequests()
//	        .antMatchers("/*.ico").permitAll()
//	        .anyRequest().authenticated()
//	        .and()	         
//	        .formLogin()
//	        .loginPage("/login").permitAll()  //CARICAMENTO PAGINA LOGIN PERSONALIZZATA
//	        .and()
//	        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")//.permitAll() al Logout ricarica la pagina del login per nuova autenticazione
//	        .and().	      
//	         csrf().disable().cors();  // QUESTO PERMETTE ALLE CHIAMATE AJAX DI AGIRE SENZA ESSERE BLOCCATE.
//	    
	   
	
	}
	
	 
	 
}
