package com.example.demo.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clienti")
public class Cliente {
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name = "idclienti")	
	 private BigInteger idclienti;
	 @Column(name = "codcliente")	
	 private String codcliente;
	 @Column(name = "ragione")	
	 private String ragione;
	 @Column(name = "indirizzo")	
	 private String indirizzo;
	 @Column(name = "phone")	
	 private String phone;
	 @Column(name = "email")	
	 private String email;
	 
	 
	 
	 public Cliente(BigInteger idclienti, String codcliente,String ragione, String indirizzo, String phone, String email) {
		super();
		this.idclienti = idclienti;
		this.codcliente = codcliente;
		this.ragione = ragione;
		this.indirizzo = indirizzo;
		this.phone = phone;
		this.email = email;
	 }
	 
	 public Cliente(String codcliente,String ragione, String indirizzo, String phone, String email) {
			super();
			this.codcliente = codcliente;
			this.ragione = ragione;
			this.indirizzo = indirizzo;
			this.phone = phone;
			this.email = email;
		 }
	 
	 public Cliente(String cliente,String ragione) {
			super();			
			this.codcliente = cliente;
			this.ragione = ragione;
			
	 }
	 

	public BigInteger getIdclienti() {
		return idclienti;
	}



	public void setIdclienti(BigInteger idclienti) {
		this.idclienti = idclienti;
	}



	public String getCodcliente() {
		return codcliente;
	}



	public void setCodcliente(String codcliente) {
		this.codcliente = codcliente;
	}



	public String getRagione() {
		return ragione;
	}



	public void setRagione(String ragione) {
		this.ragione = ragione;
	}



	public String getIndirizzo() {
		return indirizzo;
	}



	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}
	 

}
