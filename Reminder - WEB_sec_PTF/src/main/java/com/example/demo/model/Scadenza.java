package com.example.demo.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scadenza")
public class Scadenza {
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name = "idscadenza")	
	 private BigInteger idscadenza;	
	 @Column(name = "scadenza")	
	 private String scadenza;
	 @Column(name = "datascadenza")	
	 private Date datascadenza;
	
	 public Scadenza(BigInteger idscadenza, String scadenza, Date datascadenza) {
		super();
		this.idscadenza = idscadenza;
		this.scadenza = scadenza;
		this.datascadenza = datascadenza;
	}
	 
	public Scadenza(String scadenza, Date datascadenza) {
			super();		
			this.scadenza = scadenza;
			this.datascadenza = datascadenza;
	}		 

	public String getScadenza() {
		return scadenza;
	}

	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}

	public Date getDatascadenza() {
		return datascadenza;
	}

	public void setDatascadenza(Date datascadenza) {
		this.datascadenza = datascadenza;
	}	
	 
	 
	

}
