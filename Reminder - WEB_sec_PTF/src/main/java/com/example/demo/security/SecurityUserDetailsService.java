package com.example.demo.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.model.MyUserDetails;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;


//QUESTO VA A PRENDERE GLI UTENTI REGISTRATI E VERIFICA LE CREDENZIALI
@Service
public class SecurityUserDetailsService implements UserDetailsService{
	
	//   @Autowired
     //  private WebApplicationContext applicationContext;
	
	   @Autowired 
	   private UserRepository userRepository; 
	   
	   
	   @Override 
	   public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException { 
	      User user = userRepository.getUserByUsername(username); 
	      
	      if (user == null) {
	            throw new UsernameNotFoundException("Could not find user");
	      }
	      return new MyUserDetails(user); 
	   } 
	   
	/*   public void createUser(UserDetails user) { 
		      userRepository.save((User) user); 
	   } 
*/
}
