package com.example.demo.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "reminder")
public class Task {
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name = "idreminder")	
	 private BigInteger idreminder;
	 @Column(name = "cliente")	
	 private String cliente;
	 @Column(name = "lavoro")	
	 private String lavoro;
	 @Column(name = "datainsert")	
	 private String datainsert;
	 @Column(name = "dataconsegna")	
	 private String dataconsegna;
	 @Column(name = "phone")	
	 private String phone;
	 @Column(name = "email")	
	 private String email;
     @Column(name = "tipo")	
     private char tipo;
     @Column(name = "note")	
     private String note;
     @Column(name = "statonota")	
     private char statonota;
	 
	 private String state; 
	 private int priorita; 
	 
	 
	 
	public Task(BigInteger idreminder, String cliente, String lavoro, String datainsert, String dataconsegna,
			String phone, String email, char tipo, String note, char statonota, String state, int priorita) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro = lavoro;
		this.datainsert = datainsert;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.tipo = tipo;
		this.note = note;
		this.statonota = statonota;
		this.state = state;
		this.priorita = priorita;
	}

	//costruttore per tabella scadenze in dashBoard
	public Task(BigInteger idreminder, String lavoro, String dataconsegna, String state) {
		super();
		this.idreminder = idreminder;
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;		
		this.state = state;
	}
	
	public Task(String lavoro, String dataconsegna, char tipo) {
		super();		
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;		
		this.tipo = tipo;
	}
	
	
	public Task(BigInteger idreminder, String cliente, String lavoro, String dataconsegna,String phone, String email, char tipo, String note, char statonota,int priorita) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro = lavoro;
		this.statonota = statonota;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.tipo = tipo;
		this.note = note;
		this.priorita = priorita;
	}
	
	// new Task(id,cliente,lavoro,dataConsegna,phone,email,priorita,tipoc))
	
	public Task(BigInteger idreminder, String cliente, String lavoro, char statonota, String dataconsegna,String phone, String email, int priorita,char tipo) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro = lavoro;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.tipo = tipo;		
		this.statonota = statonota;
		this.priorita = priorita;
	}
	
	
	public Task(BigInteger idreminder, String cliente, String lavoro , String datainsert, String dataconsegna, String phone, String email) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro =lavoro;
		this.datainsert = datainsert;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		
	}
	
	public Task(BigInteger idreminder, String cliente, String lavoro, String datainsert, String dataconsegna,
			String phone, String email, int priorita) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro = lavoro;
		this.datainsert = datainsert;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		//this.tipo = tipo;
		this.priorita = priorita;
	}

	//costruttore x la tabella 
	public Task(BigInteger idreminder, String cliente, String lavoro , int priorita, String dataconsegna, String phone, String email) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro =lavoro;
		this.priorita = priorita;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
				
	}
	
	public Task(BigInteger idreminder, String cliente, String lavoro , int priorita, String dataconsegna, String phone, String email, String state) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro =lavoro;
		this.priorita = priorita;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.state = state; 		
	}
	
	public Task(BigInteger idreminder, String cliente, String lavoro , String dataconsegna, String phone, String email, int priorita, char tipo) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro = lavoro;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.priorita=priorita;
		this.tipo = tipo;
		
	}
	
	public Task(String cliente, String lavoro , String dataconsegna) {
		super();		
		this.cliente = cliente;
		this.lavoro = lavoro;
		this.dataconsegna = dataconsegna;
				
	}
	
	
	public Task(BigInteger idreminder, String cliente, String lavoro, String datainsert, String dataconsegna, String phone, String email,
			String state) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro =lavoro;
		this.datainsert = datainsert;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.state = state;
	}
	
	
	public Task(String cliente, String lavoro, String datainsert, String dataconsegna, String phone, String email,
			String state) {
		super();
		this.cliente = cliente;
		this.lavoro =lavoro;
		this.datainsert = datainsert;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.state = state;
	}
	
	
	public Task(String cliente, String lavoro, String dataconsegna, int priorita, String phone, String email, char tipo) {
		super();
		this.cliente = cliente;
		this.lavoro =lavoro;		
		this.dataconsegna = dataconsegna;
		this.priorita = priorita;
		this.phone = phone;
		this.email = email;
		this.tipo = tipo;
		//this.state = state;
	}
	

	 public char getTipo() {
			return tipo;
	 }

	 public void setTipo(char tipo) {
			this.tipo = tipo;
	 }
	


	public int getPriorita() {
		return priorita;
	}

	public void setPriorita(int priorita) {
		this.priorita = priorita;
	}

	public BigInteger getIdreminder() {
		return idreminder;
	}
	public void setIdreminder(BigInteger idreminder) {
		this.idreminder = idreminder;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getDatainsert() {
		return datainsert;
	}
	public void setDatainsert(String datainsert) {
		this.datainsert = datainsert;
	}
	public String getDataconsegna() {
		return dataconsegna;
	}
	public void setDataconsegna(String dataconsegna) {
		this.dataconsegna = dataconsegna;
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


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getLavoro() {
		return lavoro;
	}


	public void setLavoro(String lavoro) {
		this.lavoro = lavoro;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public char getStatonota() {
		return statonota;
	}

	public void setStatonota(char statonota) {
		this.statonota = statonota;
	}
	 
	 
}
