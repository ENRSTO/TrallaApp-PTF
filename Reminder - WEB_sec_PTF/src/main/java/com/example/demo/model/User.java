package com.example.demo.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; 

@Entity 
@Table(name = "users") 
public class User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id	
	private String username;
    @Column(name = "password")
	private String password;
	@Column(name = "account_lock")
	private boolean account_lock;
	@Column(name = "role")
	private String role;
	
	
	public User() { 
	} 
	
	
	public User(String username, String password, boolean account_lock, String role) {
		super();
		this.username = username;
		this.password = password;
		this.account_lock = account_lock;
		this.role = role;
	}
	
	
	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return  null;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	
	public void setPassword(String password) { 
	      this.password = password; 
	} 
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	
	public void setUsername(String username) { 
	      this.username = username; 
	} 
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return account_lock;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setAccountNonLocked(Boolean accountNonLocked) { 
	      this.account_lock = accountNonLocked; 
	   } 
	   public boolean getAccountNonLocked() { 
	      return account_lock; 
	   } 
	

}
