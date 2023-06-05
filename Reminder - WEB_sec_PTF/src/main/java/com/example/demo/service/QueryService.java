package com.example.demo.service;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cliente;
import com.example.demo.model.Impostazioni;
import com.example.demo.model.Priority;
import com.example.demo.model.Task;
import com.example.demo.model.TaskEvasi;
import com.example.demo.model.TaskSave;

import javassist.expr.NewArray;

@Service
public class QueryService implements IqueryService {
	
	@Autowired
    EntityManagerFactory emf;
	//private int cont;
	
	
	//tools
	
	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public Date convertToDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	

	
	public ArrayList<Integer> numberOfTask(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		LocalDate dataOggi = LocalDate.now();
		em.getTransaction().begin();
		int[] arrayPri = new int[3]; 
		ArrayList<Integer> listResult = new ArrayList<Integer>(); 	
		for (int i=0; i <= 2; i++) {
			Query query = em.createNativeQuery("SELECT count(*) FROM reminderdb.reminder where dataconsegna='"+dataOggi+"' and state ='A' and priorita ="+(i+1));
			if(query.getSingleResult().equals(0)) {
				arrayPri[i] = 0; 
			} else {
				arrayPri[i] = ((BigInteger) query.getSingleResult()).intValue();
			}
			
		}
		for (int i : arrayPri ) {
			//  Integer val = ((BigInteger) o).intValue();
			listResult.add(i);
		}		
		em.getTransaction().commit();
		em.close();
		return listResult; 
	}
	

	
	public ArrayList<Task> JPQLQueryAll() {
		
		//tipo ='"+"L"+"' and state ='"+"A"+"'
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		//LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email, tipo, note, statonota from reminder where state ='"+"A"+"'");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Task> listResult = new ArrayList<Task>();
		list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
	        String cliente = (String) record[1];
	        String lavoro = (String) record[2];
	        int priorita = (int) record [3]; 
	        String dataConsegna = sdf.format(record[4]);
	        String phone = (String) record[5];
	        String email = (String) record[6];
	        String tipo =  (String) record[7];
	        String note = (String) record[8];
	        String statonota = (String) record[9];
	        char tipoc=tipo.charAt(0);
	        char statonotac;
	        if (statonota == null) {
	        	statonotac ='N';
	        } else { statonotac = statonota.charAt(0);}
	        
	        
	        listResult.add(new Task(id, cliente, lavoro, dataConsegna, phone, email, tipoc, note, statonotac, priorita));
	    });
		em.getTransaction().commit();
		em.close();
		
		return listResult;
	}
	
		
	
	
	public ArrayList<TaskEvasi>JPQLQueryEvasiAll(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email, state from reminder where state ='"+"X"+"'");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
		ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
		ArrayList<TaskEvasi> listResult = new ArrayList<TaskEvasi>();
		list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
			String cliente = (String) record[1];
			String lavoro = (String) record[2];
			int priorita = (int) record [3]; 
			String dataConsegna = sdf.format((java.sql.Date) record[4]);
			String phone = (String) record[5];
			String email = (String) record[6];
			String state = (String) record[7];
			listResult.add(new TaskEvasi(id, cliente, lavoro, dataConsegna, phone,email, priorita, state));
        
        // public TaskEvasi(BigInteger idreminder, String cliente, String lavoro, Date dataconsegna, String phone, String email, int priorita, String state)
		});
		em.getTransaction().commit();
		em.close();		
		return listResult;
		
	}
	
	
	public ArrayList<Task> dashScadenze(){
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		LocalDate dataOggi = LocalDate.now();		
		Query query = em.createNativeQuery("select idreminder, lavoro, dataconsegna, state from reminder where tipo ='S' and YEAR(dataconsegna) ="+ dataOggi.getYear() +" order by dataconsegna asc");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Task> listResult = new ArrayList<Task>();
		list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
	        String lavoro = (String) record[1];
	        String dataConsegna = sdf.format(record[2]);    
	        String state =  (String) record[3];
	        listResult.add(new Task(id, lavoro, dataConsegna,state));	         
	    });
		em.getTransaction().commit();
		em.close();		
		return listResult;			
	}

	
	public ArrayList<Task> JPQLQueryScaduti(){  
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email, tipo, note, statonota from reminder where state ='"+"A"+"' and dataconsegna < '"+dataOggi+"' order by priorita asc");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Task> listResult = new ArrayList<Task>();
        list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
	        String cliente = (String) record[1];
	        String lavoro = (String) record[2];
	        int priorita = (int) record [3]; 
	        String dataConsegna = sdf.format(record[4]);
	        String phone = (String) record[5];
	        String email = (String) record[6];
	        String tipo =  (String) record[7];
	        String note = (String) record[8];
	        String statonota = (String) record[9];
	        char tipoc=tipo.charAt(0);
	        char statonotac;
	        if (statonota == null) {
	        	statonotac ='N';
	        } else { statonotac = statonota.charAt(0);}
	   
	        listResult.add(new Task(id, cliente, lavoro, dataConsegna, phone, email, tipoc, note, statonotac, priorita));
	    });
		em.getTransaction().commit();
		em.close();		
		return listResult;
		
	}


	
	public ArrayList<Task> JPQLQuery() {
		//Task(BigInteger idreminder, String cliente, String lavoro, String dataconsegna,String phone, String email, char tipo, String note, char statonota,int priorita)
		//tipo ='"+"L"+"' and state ='"+"A"+"'
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email, tipo, note, statonota from reminder where state ='"+"A"+"' and dataconsegna='"+dataOggi+"' order by priorita asc");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Task> listResult = new ArrayList<Task>();
		list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
	        String cliente = (String) record[1];
	        String lavoro = (String) record[2];
	        int priorita = (int) record [3]; 
	        String dataConsegna = sdf.format(record[4]);
	        String phone = (String) record[5];
	        String email = (String) record[6];
	        String tipo =  (String) record[7];
	        String note = (String) record[8];
	        String statonota = (String) record[9];
	        char tipoc=tipo.charAt(0);
	        char statonotac;
	        if (statonota == null) {
	        	statonotac ='N';
	        } else { statonotac = statonota.charAt(0);}
	        
	        
	        listResult.add(new Task(id, cliente, lavoro, dataConsegna, phone, email, tipoc, note, statonotac, priorita));
	         //listResult.add(new Task(id,cliente,lavoro,dataConsegna,phone,email,priorita,tipoc));
	    });
		em.getTransaction().commit();
		em.close();		
		return listResult;		
	}
	
	
	
	public ArrayList<Task> JPQLQueryTaskGiorni(String giorni) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email, tipo, note, statonota from reminder where state ='"+"A"+"' and dataconsegna between"+"'"+dataOggi+"' AND '"+dataOggi.plusDays(Long.parseLong(giorni))+"' order by dataconsegna, priorita asc");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Task> listResult = new ArrayList<Task>();
		list.stream().forEach((record) -> {
			
			BigInteger id = ((BigInteger) record[0]);
	        String cliente = (String) record[1];
	        String lavoro = (String) record[2];
	        int priorita = (int) record [3]; 
	        String dataConsegna = sdf.format(record[4]);
	        String phone = (String) record[5];
	        String email = (String) record[6];
	        String tipo =  (String) record[7];
	        String note = (String) record[8];
	        String statonota = (String) record[9];
	        char tipoc=tipo.charAt(0);
	        char statonotac;
	        if (statonota == null) {
	        	statonotac ='N';
	        } else { statonotac = statonota.charAt(0);}
	        
	        listResult.add(new Task(id, cliente, lavoro, statonotac, dataConsegna, phone, email, priorita,tipoc));
	        
	    });
		em.getTransaction().commit();
		em.close();
		
		return listResult;
		
	}
	
	public ArrayList<Task> JPQLQueryAmm() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email from reminder where tipo ='"+"A"+"' and state ='"+"A"+"' and dataconsegna="+"'"+dataOggi+"' order by priorita asc");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Task> listResult = new ArrayList<Task>();
		list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
	        String cliente = (String) record[1];
	        String lavoro = (String) record[2];
	        int priorita = (int) record [3]; 
	        String dataConsegna = sdf.format((java.sql.Date) record[4]);
	        String phone = (String) record[5];
	        String email = (String) record[6];
	        listResult.add(new Task(id, cliente, lavoro, priorita, dataConsegna, phone,email));
	    });
		em.getTransaction().commit();
		em.close();		
		return listResult;
	}
	
	public ArrayList<TaskEvasi> JPQLQueryEvasi() {
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email, state from reminder where state ='"+"X"+"' and dataconsegna="+"'"+dataOggi+"' order by priorita asc");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
		ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
		ArrayList<TaskEvasi> listResult = new ArrayList<TaskEvasi>();
		list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
			String cliente = (String) record[1];
			String lavoro = (String) record[2];
			int priorita = (int) record [3]; 
			String dataConsegna = sdf.format((java.sql.Date) record[4]);
			String phone = (String) record[5];
			String email = (String) record[6];
			String state = (String) record[7];
			listResult.add(new TaskEvasi(id, cliente, lavoro, dataConsegna, phone,email, priorita, state));
        
        // public TaskEvasi(BigInteger idreminder, String cliente, String lavoro, Date dataconsegna, String phone, String email, int priorita, String state)
		});
		em.getTransaction().commit();
		em.close();		
		return listResult;
	}
	
	
	public ArrayList<TaskEvasi> JPQLQueryDaCalendarioEvasi(LocalDate giorno) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		//LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email,state from reminder where state ='"+"X"+"' and dataconsegna ="+"'"+giorno+"'"+" order by dataconsegna, priorita asc");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<TaskEvasi> listResult = new ArrayList<TaskEvasi>();
		list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
	        String cliente = (String) record[1];
	        String lavoro = (String) record[2];
	        int priorita = (int) record [3]; 
	        String dataConsegna = sdf.format((java.sql.Date) record[4]);
	        String phone = (String) record[5];
	        String email = (String) record[6];
	        String state =  (String) record[7];	        
	        listResult.add(new TaskEvasi(id, cliente, lavoro, dataConsegna, phone,email, priorita, state));
	    });
		em.getTransaction().commit();
		em.close();		
		return listResult;
		
	}
	
	

	public ArrayList<Task> JPQLQueryDaCalendario(LocalDate giorno) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		//LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email, tipo, note, statonota from reminder where state ='"+"A"+"' and dataconsegna ="+"'"+giorno+"'"+" order by dataconsegna, priorita asc");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Task> listResult = new ArrayList<Task>();
		list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
	        String cliente = (String) record[1];
	        String lavoro = (String) record[2];
	        int priorita = (int) record [3]; 
	        String dataConsegna = sdf.format(record[4]);
	        String phone = (String) record[5];
	        String email = (String) record[6];
	        String tipo =  (String) record[7];
	        String note = (String) record[8];
	        String statonota = (String) record[9];
	        char tipoc=tipo.charAt(0);
	        char statonotac;
	        if (statonota == null) {
	        	statonotac ='N';
	        } else { statonotac = statonota.charAt(0);}
	        
	        listResult.add(new Task(id, cliente, lavoro, statonotac, dataConsegna, phone, email, priorita,tipoc));
	    });
		em.getTransaction().commit();
		em.close();
		
		return listResult;
		
	}
	
	
	
	
	@Transactional  
	public String JPQLsaveModifica(TaskSave modificaRecord) {
		
		EntityManager em = emf.createEntityManager();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		em.getTransaction().begin();
	
		int query = em.createNativeQuery("UPDATE reminder set cliente =:cliente, lavoro =:lavoro, dataconsegna =:dataconsegna, phone=:phone, email =:email, priorita =:priorita, note =:note, statonota =:statonota  where idreminder =:idreminder").
				setParameter("idreminder", modificaRecord.getIdreminder())
				.setParameter("cliente", modificaRecord.getCliente())
				.setParameter("lavoro", modificaRecord.getLavoro())
				.setParameter("dataconsegna", (String) sdf1.format(modificaRecord.getDataconsegna()))
				.setParameter("phone", modificaRecord.getPhone())
				.setParameter("email", modificaRecord.getEmail())
				.setParameter("priorita", modificaRecord.getPriorita())	
				.setParameter("note", modificaRecord.getNote())	
				.setParameter("statonota", modificaRecord.getStatonota())	
				.executeUpdate();
		em.getTransaction().commit();
		em.close();		
		return "Task modificato con successo"; 
	}
	

	@Override
	public String JPQLsaveRecord(TaskSave recordSave) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Query query = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state, note, statonota) VALUES (:cliente,:lavoro,:dataconsegna,:phone,:email,:priorita,:tipo,:state,:note,:statonota);");
		
		em.getTransaction().begin();
		query.setParameter("cliente", recordSave.getCliente());
		query.setParameter("lavoro", recordSave.getLavoro());
		query.setParameter("dataconsegna", (String) sdf1.format(recordSave.getDataconsegna())); // (String) sdf.format(recordSave.getDataValidita()))
		query.setParameter("phone", recordSave.getPhone());
		query.setParameter("email", recordSave.getEmail());
		query.setParameter("priorita", recordSave.getPriorita());
		query.setParameter("tipo", recordSave.getTipo());
		query.setParameter("state", recordSave.getState());
		query.setParameter("note", recordSave.getNote());
		query.setParameter("statonota", recordSave.getStatonota());
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();				
		return "Task inserito con successo";
		
	
	};
	
	public String JPQLsaveRecordAmm(TaskSave recordSave) {
		
		EntityManager em = emf.createEntityManager();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Query query = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state) VALUES (:cliente,:lavoro,:dataconsegna,:phone,:email,:priorita,:tipo,:state);");		
		em.getTransaction().begin();
		query.setParameter("cliente", recordSave.getCliente());
		query.setParameter("lavoro", recordSave.getLavoro());
		query.setParameter("dataconsegna", (String) sdf1.format(recordSave.getDataconsegna())); // (String) sdf.format(recordSave.getDataValidita()))		
		query.setParameter("phone", recordSave.getPhone());
		query.setParameter("email", recordSave.getEmail());
		query.setParameter("priorita", recordSave.getPriorita());
		query.setParameter("tipo", recordSave.getTipo());
		query.setParameter("state", recordSave.getState());
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();				
		return "Record inserito con successo";
		
		
	}
	
	
	
	//da implementare Metodo per la perisistenza Ripetitiva
	public String JPQLsaveRecordAmmRepeat(TaskSave recordSave, int accumulo, int ripetizioni) {
				
		EntityManager em = emf.createEntityManager();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	//	String dataConsegna = (String) sdf1.format(recordSave.getDataconsegna());
		
		Date datesql = recordSave.getDataconsegna();
		switch(accumulo) {
		  case 0:	
			  em.getTransaction().begin();
			  Query queryD = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state) VALUES (:cliente,:lavoro,:dataconsegna,:phone,:email,:priorita,:tipo,:state);");				
				queryD.setParameter("cliente", recordSave.getCliente());
				queryD.setParameter("lavoro", recordSave.getLavoro());
				queryD.setParameter("dataconsegna", (String) sdf1.format(datesql)); //  di tipo DAte	-->	(String) sdf1.format(recordSave.getDataconsegna())
				queryD.setParameter("phone", recordSave.getPhone());
				queryD.setParameter("email", recordSave.getEmail());
				queryD.setParameter("priorita", recordSave.getPriorita());
				queryD.setParameter("tipo", recordSave.getTipo());
				queryD.setParameter("state", recordSave.getState());
				queryD.executeUpdate();			  
			  em.getTransaction().commit();
			  em.close();
			  break;
		  case 1:
			  
			  break;
		  case 7:  //settimanale
			 // LocalDate nuovaData = convertToLocalDateViaInstant(recordSave.getDataconsegna());
			
			  em.getTransaction().begin();
			  for (int i=0; i < ripetizioni; i++) {	  
				   
				  	Query query = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state) VALUES (:cliente,:lavoro,:dataconsegna,:phone,:email,:priorita,:tipo,:state);");				
					query.setParameter("cliente", recordSave.getCliente());
					query.setParameter("lavoro", recordSave.getLavoro());
					query.setParameter("dataconsegna", (String) sdf1.format(datesql)); //  di tipo DAte	-->	(String) sdf1.format(recordSave.getDataconsegna())
					query.setParameter("phone", recordSave.getPhone());
					query.setParameter("email", recordSave.getEmail());
					query.setParameter("priorita", recordSave.getPriorita());
					query.setParameter("tipo", recordSave.getTipo());
					query.setParameter("state", recordSave.getState());
					query.executeUpdate();
					
					Calendar c = Calendar.getInstance();
				    try {
						c.setTime(sdf1.parse((String) sdf1.format(datesql)));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    c.add(Calendar.DAY_OF_MONTH, accumulo);
				    try {
						datesql = new SimpleDateFormat("yyyy-MM-dd").parse(sdf1.format(c.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				  
			  }
			  em.getTransaction().commit();
			  em.close();				 
		    break;
		  case 30:  // mensile 
			  em.getTransaction().begin();
			  for (int i=0; i < ripetizioni; i++) {	  
				   
				  	Query query = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state) VALUES (:cliente,:lavoro,:dataconsegna,:phone,:email,:priorita,:tipo,:state);");				
					query.setParameter("cliente", recordSave.getCliente());
					query.setParameter("lavoro", recordSave.getLavoro());
					query.setParameter("dataconsegna", (String) sdf1.format(datesql)); //  di tipo DAte	-->	(String) sdf1.format(recordSave.getDataconsegna())
					query.setParameter("phone", recordSave.getPhone());
					query.setParameter("email", recordSave.getEmail());
					query.setParameter("priorita", recordSave.getPriorita());
					query.setParameter("tipo", recordSave.getTipo());
					query.setParameter("state", recordSave.getState());
					query.executeUpdate();
					
					Calendar c = Calendar.getInstance();
				    try {
						c.setTime(sdf1.parse((String) sdf1.format(datesql)));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    c.add(Calendar.MONTH, 1);
				    try {
						datesql = new SimpleDateFormat("yyyy-MM-dd").parse(sdf1.format(c.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				  
			  }
			  em.getTransaction().commit();
			  em.close();				
			  
		    // code block
		    break;
		  case 180:  // 6 mesi 
			  em.getTransaction().begin();
			  for (int i=0; i < ripetizioni; i++) {	  
				   
				  	Query query = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state) VALUES (:cliente,:lavoro,:dataconsegna,:phone,:email,:priorita,:tipo,:state);");				
					query.setParameter("cliente", recordSave.getCliente());
					query.setParameter("lavoro", recordSave.getLavoro());
					query.setParameter("dataconsegna", (String) sdf1.format(datesql)); //  di tipo DAte	-->	(String) sdf1.format(recordSave.getDataconsegna())
					query.setParameter("phone", recordSave.getPhone());
					query.setParameter("email", recordSave.getEmail());
					query.setParameter("priorita", recordSave.getPriorita());
					query.setParameter("tipo", recordSave.getTipo());
					query.setParameter("state", recordSave.getState());
					query.executeUpdate();
					
					Calendar c = Calendar.getInstance();
				    try {
						c.setTime(sdf1.parse((String) sdf1.format(datesql)));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    c.add(Calendar.MONTH, 6);
				    try {
						datesql = new SimpleDateFormat("yyyy-MM-dd").parse(sdf1.format(c.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				  
			  }
			  em.getTransaction().commit();
			  em.close();				
			  
			break;
		  case 365:
			    // Calendar.YEAR, 20
			  em.getTransaction().begin();
			  for (int i=0; i < ripetizioni; i++) {	  
				   
				  	Query query = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state) VALUES (:cliente,:lavoro,:dataconsegna,:phone,:email,:priorita,:tipo,:state);");				
					query.setParameter("cliente", recordSave.getCliente());
					query.setParameter("lavoro", recordSave.getLavoro());
					query.setParameter("dataconsegna", (String) sdf1.format(datesql)); //  di tipo DAte	-->	(String) sdf1.format(recordSave.getDataconsegna())
					query.setParameter("phone", recordSave.getPhone());
					query.setParameter("email", recordSave.getEmail());
					query.setParameter("priorita", recordSave.getPriorita());
					query.setParameter("tipo", recordSave.getTipo());
					query.setParameter("state", recordSave.getState());
					query.executeUpdate();
					
					Calendar c = Calendar.getInstance();
				    try {
						c.setTime(sdf1.parse((String) sdf1.format(datesql)));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    c.add(Calendar.YEAR, 1);
				    try {
						datesql = new SimpleDateFormat("yyyy-MM-dd").parse(sdf1.format(c.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				  
			  }
			  em.getTransaction().commit();
			  em.close();				
			  
			break;
		  default:
		    // code block
		}
		 return "Attività inserita con successo";
			
	}
	
	
	
	public String JPQLsaveEvasioneOLd(BigInteger id) {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate dataOggi = LocalDate.now(); // per salvare la data di evasione 
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		int query = em.createNativeQuery("UPDATE reminder set state =:state, datainsert =:datainsert where idreminder =:idreminder").
		setParameter("state", "X").
		setParameter("datainsert", sdf1.format(convertToDate(dataOggi))).
		setParameter("idreminder", id)
		.executeUpdate();
		em.getTransaction().commit();
		em.close();		
        // reperisci la mail se il campo è valorizzato nel DB
		String mailDaPassare = trovaMail(id).get(2);
		if (mailDaPassare.isEmpty()) {
			return "il Task è stato evaso"; 
		} else {
			//return "";
		   try {
			    return sendEmail(mailDaPassare,trovaMail(id).get(0),trovaMail(id).get(1));
		   } catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			   	e.printStackTrace();
			   	return "Invio e-mail non risucito!";
		   }
		}
	}
	
	//-------refactor per evasione dei tipo 'S' e inserimento nuova scadenza con anno+1
	public String JPQLsaveEvasione(BigInteger id) {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate dataOggi = LocalDate.now(); // per salvare la data di evasione 
		EntityManager em = emf.createEntityManager();
		//----
		Query queryS = em.createNativeQuery("select lavoro, dataconsegna,tipo from reminder where idreminder ="+id);
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
	    ArrayList<Object[]> list = (ArrayList<Object[]>) queryS.getResultList();
	    ArrayList<Task> listResult = new ArrayList<Task>();
	  	list.stream().forEach((record) -> {    
	    	String lavoro = (String) record[0];	
	    	String dataConsegna = sdf1.format(record[1]);
	    	String tipo = (String) record[2];
	    	char tipoc=tipo.charAt(0);	    	
	    	listResult.add(new Task(lavoro, dataConsegna, tipoc));
		});
	  	// se è una scadenza  crea un record nuovo con anno+1
		if (listResult.get(0).getTipo()=='S') {
			
			//System.out.print(listResult.get(0).getDataconsegna()); // test
			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("yyyy-MM-dd").parse(listResult.get(0).getDataconsegna());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			
			Calendar c = Calendar.getInstance();
		    try {
				c.setTime(sdf1.parse((String) sdf1.format(date1)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    c.add(Calendar.YEAR, 1);
		    Date datesql = null;
		    try {
				datesql = new SimpleDateFormat("yyyy-MM-dd").parse(sdf1.format(c.getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
			
			Query query = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state, note, statonota) VALUES (:cliente,:lavoro,:dataconsegna,:phone,:email,:priorita,:tipo,:state,:note,:statonota);");
			
			//em.getTransaction().begin();
			query.setParameter("cliente", "User");
			query.setParameter("lavoro", listResult.get(0).getLavoro());
			query.setParameter("dataconsegna", (String) sdf1.format(datesql)); // (String) sdf.format(recordSave.getDataValidita()))
			query.setParameter("phone", null);
			query.setParameter("email", null);
			query.setParameter("priorita", 1);
			query.setParameter("tipo",listResult.get(0).getTipo());
			query.setParameter("state", "A");
			query.setParameter("note",null);
			query.setParameter("statonota",null);
			query.executeUpdate();
		//	em.getTransaction().commit();
		//	em.close();				
			
		}
	  	//----	
		//em.getTransaction().begin();
		int query = em.createNativeQuery("UPDATE reminder set state =:state, datainsert =:datainsert where idreminder =:idreminder").
		setParameter("state", "X").
		setParameter("datainsert", sdf1.format(convertToDate(dataOggi))).
		setParameter("idreminder", id)
		.executeUpdate();
		em.getTransaction().commit();
		em.close();		
        // reperisci la mail se il campo è valorizzato nel DB
		String mailDaPassare = trovaMail(id).get(2);
		
		if (mailDaPassare == null) {  //mailDaPassare.isEmpty() 
			return "il Task è stato evaso"; 
		} else {
			      if(mailDaPassare.isEmpty()) {
			    	  return "il Task è stato evaso"; 
			      } else {			    	  
			    	  		try {
			    	  				return sendEmail(mailDaPassare,trovaMail(id).get(0),trovaMail(id).get(1));
			    	  		} catch (MessagingException | IOException e) {
						// TODO Auto-generated catch block
						   	e.printStackTrace();
						   	return "Invio e-mail non risucito!";
					   }
			    	  
			      }
			//return "";
		   
		}
	}
	
	
	
	
	
	
	public String trovaNota (BigInteger id) {
		
		EntityManager em = emf.createEntityManager();
		
		Query query = em.createNativeQuery("select idreminder, note from reminder where idreminder ="+id);
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
	    ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
	    ArrayList<String> listResult = new ArrayList<String>();
	  	  
	    list.stream().forEach((record) -> {    
	    	BigInteger nota = (BigInteger) record[0];	
	    	String note = (String) record[1];
	    	listResult.add(note);
		});
	    
		em.getTransaction().commit();
		em.close();
			
		return listResult.get(0);
	}
	
	
	
	
	public ArrayList<String> trovaMail(BigInteger id) {
		

		EntityManager em = emf.createEntityManager();
		
		Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email,tipo from reminder where idreminder ="+id);
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<String> listResult = new ArrayList<String>();
        
        list.stream().forEach((record) -> {    
        	String cliente = (String) record[1];
        	String lavoro = (String) record[2]; 
	        String email = (String) record[6];	       
			listResult.add(email);
			listResult.add(lavoro);
			listResult.add(email);
		});
		em.getTransaction().commit();
		em.close();
		return listResult;		
	}
	
	
	
	
	public String JPQLDeleteRecords (List<Integer> myArray) {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		for(int i = 0; i < myArray.size(); i++) {
			Query query = em.createNativeQuery("delete from reminder where idreminder =:idreminder");			
			query.setParameter("idreminder", myArray.get(i));
			query.executeUpdate();
		}
		
		em.getTransaction().commit();
		em.close();				
		return "Records eliminati con successo";
		
	}
	
	
	public String JPQLRipristina (int id) {  // metodo per il ripristino del task 
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("UPDATE reminder set state =:state, dataconsegna =:dataconsegna where idreminder =:idreminder");			
		query.setParameter("idreminder", id);
		query.setParameter("dataconsegna",sdf1.format(convertToDate(dataOggi)));
		query.setParameter("state", "A");
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();				
		return "Task ripristinato con successo!";
		
	}
	
	
	public String ModificaData (List<Integer> listaItem, @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna) {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		for (int id : listaItem) {
			
			Query query = em.createNativeQuery("UPDATE reminder set dataconsegna =:dataconsegna where idreminder =:idreminder");	
			query.setParameter("idreminder", id);
			query.setParameter("dataconsegna", (String) sdf1.format(dataconsegna));
			query.executeUpdate();
			
		}
		em.getTransaction().commit();
		em.close();				
		
		return "Task spostati con successo!";
		
	}
	
	// copia da popUP 
	public String CopiaRecord (BigInteger id) {
				
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state, note, statonota) SELECT cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state, note, statonota FROM "
				+ " reminder WHERE idreminder =:idreminder");
		query.setParameter("idreminder", id);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();				
		return  "Task copiato con successo!";
	}
	
	// elimna da popup
	public String EliminaRecord(BigInteger id) {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();		
		Query query = em.createNativeQuery("delete from reminder where idreminder =:idreminder");
		query.setParameter("idreminder", id);
		query.executeUpdate();		
		em.getTransaction().commit();
		em.close();
		return "Task ELIMINATO con successo!";
	}
	
	
	public String MoveRecord(BigInteger id, @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna) {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createNativeQuery("UPDATE reminder set dataconsegna =:dataconsegna where idreminder =:idreminder");	
		query.setParameter("idreminder", id);
		query.setParameter("dataconsegna", (String) sdf1.format(dataconsegna));
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();						
		return "Task spostato con successo!";
				
	}
	
	
	
	public String DuplicaRecord (List<Integer> listaItem, @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna) {		
		
		EntityManager em = emf.createEntityManager();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		ArrayList<Task> listDaDuplicare = new ArrayList<Task>();
		em.getTransaction().begin();
		for (int i :  listaItem) {
			
			Query query = em.createNativeQuery("select idreminder, cliente, lavoro, priorita, dataconsegna, phone, email,tipo from reminder where idreminder ="+i);
						
			@SuppressWarnings("unchecked")
			ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
	        
			list.stream().forEach((record)-> {
				
				String cliente = (String) record[1];
				String lavoro = (String) record[2];
				int priorita = (int) record[3];			
				String dataCons = sdf1.format((java.sql.Date) record[4]);  //sdf1.format((java.sql.Date) record[4]);
				String phone = (String) record[5];
			    String email = (String) record[6];
			    String tipo =  (String) record[7];
			    char tipoc=tipo.charAt(0);		
	    		listDaDuplicare.add(new Task(cliente, lavoro, dataCons, priorita,  phone, email, tipoc));
			});
						
		}
		for (Task task : listDaDuplicare) {
			
			Query query = em.createNativeQuery("INSERT INTO reminder (cliente, lavoro, dataconsegna, phone, email, priorita, tipo, state) VALUES (:cliente,:lavoro,:dataconsegna,:phone,:email,:priorita,:tipo,:state);");				
			query.setParameter("cliente", task.getCliente());
			query.setParameter("lavoro", task.getLavoro());
			query.setParameter("dataconsegna", (String) sdf1.format(dataconsegna)); //  di tipo DAte	-->	(String) sdf1.format(recordSave.getDataconsegna())
			query.setParameter("phone", task.getPhone());
			query.setParameter("email", task.getEmail());
			query.setParameter("priorita", task.getPriorita());
			query.setParameter("tipo", task.getTipo());
			query.setParameter("state", 'A');
			query.executeUpdate();
			
		}
		
		em.getTransaction().commit();
		em.close();	
		return "Duplicazione Task eseguita con successo!";
	}
	
	
	
	
	public ArrayList<Task> JPQLQueryforReport(List<Integer> myArray) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		LocalDate dataOggi = LocalDate.now();
		ArrayList<Task> listResult = new ArrayList<Task>();
		em.getTransaction().begin();
		for (int i = 0; i < myArray.size(); i++) {
			
			Query query = em.createNativeQuery("select cliente, lavoro, dataconsegna from reminder where state ='"+"A"+"' and idreminder ='"+myArray.get(i)+"'");
		
			@SuppressWarnings("unchecked")
	        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
	      
			list.stream().forEach((record) -> {
			    String cliente = (String) record[0];
		        String lavoro = (String) record[1];		   
		        String dataConsegna = sdf.format(record[2]);
		        listResult.add(new Task(cliente,lavoro,dataConsegna));
		    });
			
		}
		
		em.getTransaction().commit();
		em.close();
		
		return listResult;		
		
	}
	
// --------------SERVICE x IMPOSTAZIONI ----------------------------------------------------------	
	
	
	public String JPQLsaveRecordImpostazioni(Impostazioni record) {
	    
	    
	    EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("UPDATE users set mittente =:mitt, email =:email, host =:host, porta =:porta, googlekey =:googlekey where username =:id");    
        query.setParameter("id", record.getUtente());    
        query.setParameter("mitt", record.getMittente());
        query.setParameter("email", record.getEmail());
        query.setParameter("host", record.getHost());
        query.setParameter("porta", record.getPorta());
        query.setParameter("googlekey", record.getGooglekey());
        
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();                     
        return "setup salvato!";

	
	}
	
	
	
	public ArrayList<Impostazioni> GetImpostazioni (String utente) {
	    
	    
        EntityManager em = emf.createEntityManager();       
        Query query = em.createNativeQuery("select username,email,host,porta,googlekey,mittente from  reminderdb.users where username ='"+utente+"'");
        em.getTransaction().begin();
        @SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Impostazioni> listResult = new ArrayList<Impostazioni>();
        
        list.stream().forEach((record) -> {
            String username = ((String) record[0]);
            String email= (String) record[1];
            String host = (String) record[2];
            int porta =  record [3] == null? 0 : (int) record [3] ;
           // int porta = (int) record [3];             
            String googlekey = (String) record[4];
            String mittente = (String) record [5];
                   
       //Impostazioni(String utente, String mittente, String host, int porta, String googlekey, String email)
            listResult.add(new Impostazioni(username, mittente, host, (int) porta, googlekey, email));
        });
        em.getTransaction().commit();
        em.close();  	    
	    return listResult; 
	}
	
	
// --------------- SERVICE x CLIENTI-------------------------------------------------
	

		
	public static String preparaPerSqlQry(String parametro){
			
		  parametro = parametro.replaceAll("'","''");
		  parametro = parametro.replaceAll("%","%%");
		  //parametro = parametro.replaceAll("?","??");
		  return parametro;
			
	}
	   
	
	
	
/*METODO PER REPERIMENTO DATI CLIENTE SUI CAMPI DEL FORM ANAGRAFICA CLIENTI*/	
public ArrayList<Cliente> agganciaDatiC(String codcliente) {
	        QueryService.preparaPerSqlQry(codcliente);
			//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
			EntityManager em = emf.createEntityManager();
			//LocalDate dataOggi = LocalDate.now();
			Query query = em.createNativeQuery("select idclienti, codcliente, ragione, indirizzo, phone, email from clienti where codcliente ='"+ QueryService.preparaPerSqlQry(codcliente) +"'");
			em.getTransaction().begin();
			@SuppressWarnings("unchecked")
	        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
	        ArrayList<Cliente> listResult = new ArrayList<Cliente>();
			list.stream().forEach((record) -> {
				BigInteger id = ((BigInteger) record[0]);
		        String cliente = (String) record[1];
		        String ragione = (String) record[2];
		        String indirizzo = (String) record [3]; 	        
		        String phone = (String) record[4];
		        String email = (String) record[5];	        
		   
		        listResult.add(new Cliente(id, cliente, ragione, indirizzo, phone, email));
		    });
			em.getTransaction().commit();
			em.close();
			
			return listResult;
	
}
	
public String eliminacliente(BigInteger id) {


		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNativeQuery("delete from clienti where idclienti =:id");
		query.setParameter("id", id);
		query.executeUpdate();

		em.getTransaction().commit();
		em.close();
		return "Cliente ELIMINATO con successo!";
}
	
	
	
public ArrayList<Cliente> JPQLQueryClienti() {
		
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		EntityManager em = emf.createEntityManager();
		//LocalDate dataOggi = LocalDate.now();
		Query query = em.createNativeQuery("select idclienti, codcliente, ragione, indirizzo, phone, email from clienti order by codcliente asc");
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
        ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
        ArrayList<Cliente> listResult = new ArrayList<Cliente>();
		list.stream().forEach((record) -> {
			BigInteger id = ((BigInteger) record[0]);
	        String cliente = (String) record[1];
	        String ragione = (String) record[2];
	        String indirizzo = (String) record [3]; 	        
	        String phone = (String) record[4];
	        String email = (String) record[5];	        
	   
	        listResult.add(new Cliente(id, cliente, ragione, indirizzo, phone, email));
	    });
		em.getTransaction().commit();
		em.close();
		
		return listResult;
}
	

public ArrayList<Cliente> JPQLQueryClientiCombo() {
	
	
	//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
	EntityManager em = emf.createEntityManager();
	//LocalDate dataOggi = LocalDate.now();
	Query query = em.createNativeQuery("select codcliente, ragione from clienti order by codcliente asc");
	em.getTransaction().begin();
	@SuppressWarnings("unchecked")
    ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
    ArrayList<Cliente> listResult = new ArrayList<Cliente>();
	list.stream().forEach((record) -> {
		String cliente = (String) record[0];
        String ragione = (String) record[1];   
        listResult.add(new Cliente(cliente,ragione));
    });
	em.getTransaction().commit();
	em.close();
	
	return listResult;
}
	

public ArrayList<Cliente> JPQLQueryClientiReperimentoDati(String codcliente) {
	
	
	//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
	EntityManager em = emf.createEntityManager();
	//LocalDate dataOggi = LocalDate.now();
	Query query = em.createNativeQuery("select email, phone from clienti where codcliente ='"+codcliente+"' order by codcliente asc");
	em.getTransaction().begin();
	@SuppressWarnings("unchecked")
    ArrayList<Object[]> list = (ArrayList<Object[]>) query.getResultList();
    ArrayList<Cliente> listResult = new ArrayList<Cliente>();
	list.stream().forEach((record) -> {
		String email = (String) record[0];
        String phone = (String) record[1];   
        listResult.add(new Cliente(email,phone));
    });
	em.getTransaction().commit();
	em.close();
	
	return listResult;
}



public String JPQLsaveRecordC(Cliente insertRecord) {
		
	EntityManager em = emf.createEntityManager();
	Query query = em.createNativeQuery("INSERT INTO clienti (codcliente, ragione, indirizzo, phone, email) VALUES (:cliente,:ragione,:indirizzo,:phone,:email);");
	
	em.getTransaction().begin();
	query.setParameter("cliente", insertRecord.getCodcliente());
	query.setParameter("ragione", insertRecord.getRagione());
	query.setParameter("indirizzo",insertRecord.getIndirizzo() ); // (String) sdf.format(recordSave.getDataValidita()))
	query.setParameter("phone",insertRecord.getPhone());
	query.setParameter("email", insertRecord.getEmail());
	query.executeUpdate();
	em.getTransaction().commit();
	em.close();				
	return "Cliente inserito con successo";
	
}


public String JPQLmodificaRecordC(Cliente insertRecord) {
	
	EntityManager em = emf.createEntityManager();
	em.getTransaction().begin();

	int query = em.createNativeQuery("UPDATE clienti set codcliente =:cliente, indirizzo =:indirizzo, phone=:phone, email =:email, ragione =:ragione  where idclienti =:idclienti").
			setParameter("idclienti", insertRecord.getIdclienti())
			.setParameter("cliente", insertRecord.getCodcliente())
			.setParameter("indirizzo", insertRecord.getIndirizzo())
			.setParameter("phone",insertRecord.getPhone() )
			.setParameter("email", insertRecord.getEmail())
			.setParameter("ragione", insertRecord.getRagione())			
			.executeUpdate();
	em.getTransaction().commit();
	em.close();		
	return "Cliente modificato con successo"; 
		
}



public boolean verificaCliente(String cliente) {
	
	EntityManager em = emf.createEntityManager();
	//em.getTransaction().begin();
	//em.getTransaction().begin();

	Query query = em.createNativeQuery("select count(*) from clienti where codcliente ='"+cliente.replaceAll("'", "''")+"'");
	
	if (query.getSingleResult().toString() == "0") {			
		return false;			
	} else {			
		return true;  // c'è già il cliente
	}		

}

	
//----------------fine clienti	


// POSTA elettronica 

//E-mail ----------------------------------------------------------------------------


	public String sendEmail(String email, String cliente, String task) throws AddressException, MessagingException, IOException {
		sendmail(email,cliente,task);
		return "Email inviata con successo.";
	}   
		
	
	private void sendmail(String mail,String cliente, String task) throws AddressException, MessagingException, IOException {
	   Properties props = new Properties();
	   props.put("mail.smtp.auth", "true");
	   props.put("mail.smtp.starttls.enable", "true");
	   props.put("mail.smtp.host", "smtp.gmail.com");
	   props.put("mail.smtp.port", "587");
	   
	   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	      protected PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication("enrico.stocchetti@gmail.com", "foat nee fyprp wgqg");  //mettere passw dell'account 4
	      }
	   });
	   Message msg = new MimeMessage(session);
	   msg.setFrom(new InternetAddress("enrico.stocchetti@gmail.com", false));

	   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
	   msg.setSubject("Questa mail arriva dall'App Reminder-WEb by ENRSTO");
	   msg.setContent("Gentile "+cliente+" l'attività:: "+task+" è stata evasa", "text/html");
	   msg.setSentDate(new Date());
// x allegatti --
	/*   MimeBodyPart messageBodyPart = new MimeBodyPart();
	   messageBodyPart.setContent("Tutorials point email", "text/html");

	   Multipart multipart = new MimeMultipart();
	   multipart.addBodyPart(messageBodyPart);
	   MimeBodyPart attachPart = new MimeBodyPart();

	   attachPart.attachFile("/var/tmp/image19.png");
	   multipart.addBodyPart(attachPart);
	   msg.setContent(multipart);*/
// x allegati	   
	   Transport.send(msg);   
	}

	

}
