package com.example.demo.model;

import java.math.BigInteger;
import java.util.Date;

public class TaskEvasi {
	
	 private BigInteger idreminder;
	 private String cliente;
	 private String lavoro;
	 private String dataconsegna;	
	 private String phone;	
	 private String email;	
	 private int priorita;
	 private String state;
	 
	public TaskEvasi(BigInteger idreminder, String cliente, String lavoro, String dataconsegna, String phone,
			String email, int priorita, String state) {
		super();
		this.idreminder = idreminder;
		this.cliente = cliente;
		this.lavoro = lavoro;
		this.dataconsegna = dataconsegna;
		this.phone = phone;
		this.email = email;
		this.priorita = priorita;
		this.state = state;
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

	public int getPriorita() {
		return priorita;
	}

	public void setPriorita(int priorita) {
		this.priorita = priorita;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	 
	 
	
}
