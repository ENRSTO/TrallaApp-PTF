package com.example.demo.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "impostazioni")
public class Impostazioni {
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name = "id")	
	 private BigInteger idImpostazioni;
	 @Column(name = "mittente")	
	 private String mittente;
	 @Column(name = "host")	
	 private String host;
	 @Column(name = "porta")	
	 private int porta;
	 @Column(name = "googlekey")	
	 private String googlekey;
	 @Column(name = "email")	
	 private String email;
	 private String utente;
	
	
	 
	 
	 public Impostazioni(BigInteger idImpostazioni, String mittente, String host, Integer porta, String googlekey,String email) {
		super();
		this.idImpostazioni = idImpostazioni;
		this.mittente = mittente;
		this.host = host;
		this.porta = porta;
		this.googlekey = googlekey;
		this.email = email;
	}
   
	 public Impostazioni(String mittente) {
			super();
			
			this.mittente = mittente;
			
	 }



	public Impostazioni(String utente, String mittente, String host, int porta, String googlekey, String email) {
		super();
		this.utente = utente;
		this.mittente = mittente;
		this.host = host;
		this.porta = porta;
		this.googlekey = googlekey;
		this.email = email;
		
	}



	public String getMittente() {
		return mittente;
	}

    public String getUtente() {
        return utente;
    }
    
    public void setUtente (String utente) {
        this.utente =utente;
    }

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}



	public String getHost() {
		return host;
	}



	public void setHost(String host) {
		this.host = host;
	}



	public Integer getPorta() {
		return porta;
	}



	public void setPorta(int porta) {
		this.porta = porta;
	}



	public String getGooglekey() {
		return googlekey;
	}



	public void setGooglekey(String googlekey) {
		this.googlekey = googlekey;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}
	 
	 
}
