package com.example.demo.model;

public class Priority {
	
	private int rossi; 
	private int gialli; 
	private int verdi;
	
	
	public Priority(int rossi, int gialli, int verdi) {
		super();
		this.rossi = rossi;
		this.gialli = gialli;
		this.verdi = verdi;
	}


	public int getRossi() {
		return rossi;
	}


	public void setRossi(int rossi) {
		this.rossi = rossi;
	}


	public int getGialli() {
		return gialli;
	}


	public void setGialli(int gialli) {
		this.gialli = gialli;
	}


	public int getVerdi() {
		return verdi;
	}


	public void setVerdi(int verdi) {
		this.verdi = verdi;
	} 
	

}
