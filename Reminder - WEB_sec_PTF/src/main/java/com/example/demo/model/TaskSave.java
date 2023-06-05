package com.example.demo.model;


import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TaskSave {

	
	 private BigInteger idreminder;
	 private String cliente;
	 private String lavoro;
	 private Date datainsert;
	 private Date dataconsegna;	
	 private String phone;	
	 private String email;	
	 private int priorita;
	 private char tipo; 
	 private char state; 
	 private String note;
	 private char statonota;
	 
	 
	 

	public TaskSave(BigInteger idreminder, Date dataconsegna) {  
        this.idreminder = idreminder;  
		this.dataconsegna = dataconsegna;		
	}
	 
	

	public TaskSave(BigInteger idreminder, String cliente, String lavoro, Date dataconsegna,
			String phone, String email, int priorita) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.priorita = priorita;
	}
	
	public TaskSave(BigInteger idreminder, String cliente, String lavoro, Date dataconsegna,
			String phone, String email, int priorita, String nota) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.priorita = priorita;
		this.note = nota;
	}
	
	
	public TaskSave(String cliente, String lavoro, Date dataconsegna,
			String phone, String email, int priorita, char tipo, char state) {
		super();
		this.cliente = cliente;
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.priorita = priorita;
		this.tipo = tipo;
		this.state = state;
	}
	
	public TaskSave(String cliente, String lavoro, Date dataconsegna,
			String phone, String email, int priorita, char state) {
		super();
		this.cliente = cliente;
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.priorita = priorita;
		this.state = state;
	}
	
	public TaskSave(String cliente, String lavoro, Date dataconsegna,
			String phone, String email, int priorita, char tipo, char state, String note, char statonota) {
		super();
		this.cliente = cliente;
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.priorita = priorita;
		this.tipo = tipo;
		this.state = state;
		this.note = note;
		this.statonota = statonota;
	}
	
	public TaskSave (String lavoro, Date dataconsegna,char tipo) {
		super();
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;
		this.tipo = tipo;
	}
	

	public TaskSave(String cliente, String lavoro, Date dataconsegna, int priorita) {
		super();
		this.cliente = cliente;
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;
		this.priorita = priorita;
	}
	
	public TaskSave(String cliente, String lavoro, Date dataconsegna, int priorita, char tipo, char state) {
		super();
		this.cliente = cliente;
		this.lavoro = lavoro;		
		this.dataconsegna = dataconsegna;
		this.priorita = priorita;
		this.tipo = tipo;
		this.state = state;
	}
	
	public TaskSave(String cliente, String lavoro,  Date dataconsegna, int priorita, String phone, String email, char tipo) {
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
	
	
	
	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}
	
	
	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
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
	public String getLavoro() {
		return lavoro;
	}
	public void setLavoro(String lavoro) {
		this.lavoro = lavoro;
	}
	public Date getDatainsert() {
		return datainsert;
	}
	public void setDatainsert(Date datainsert) {
		this.datainsert = datainsert;
	}
	public Date getDataconsegna() {
		return dataconsegna;
	}
	public void setDataconsegna(Date dataconsegna) {
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
	public int getPriorita() {
		return priorita;
	}
	public void setPriorita(int priorita) {
		this.priorita = priorita;
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
