package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
         String rawPassword = "";	
         String encodedPassw = encoder.encode(rawPassword);
         
         System.out.println(encodedPassw);
         
	}

	

}
