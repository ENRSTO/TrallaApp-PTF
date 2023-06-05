package com.example.demo.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Cliente;
import com.example.demo.model.Impostazioni;
import com.example.demo.model.Priority;
import com.example.demo.model.Task;
import com.example.demo.model.TaskEvasi;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SecurityUserDetailsService;
import com.example.demo.service.QueryService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class TaskController {
	
	
	
	@Autowired private SecurityUserDetailsService userDetailsManager; 
	@Autowired private PasswordEncoder passwordEncoder; 
	@Autowired private UserRepository user;
	@Autowired private QueryService queryservice;
	
	
	@Autowired
	private ApplicationContext context;

	//metodo per chiusura APP
	@GetMapping("/shutdown-app")
	public void shutdownApp() {

	    int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
	    System.exit(exitCode);
	}
	
	@GetMapping("/login")   //buono
    public String login(HttpServletRequest request, HttpSession session) { 
       session.setAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION")); 
       return "login"; 
    } 
	

	
	private String getErrorMessage(HttpServletRequest request, String key) {
	        Exception exception = (Exception) request.getSession().getAttribute(key); 
	        String error = ""; 
	        if (exception instanceof BadCredentialsException) { 
	           error = "Invalid username and password!"; 
	        } else if (exception instanceof LockedException) { 
	           error = exception.getMessage(); 
	        } else { 
	           error = "Invalid username and password!"; 
	        } 
	        return error;
	}
	
	
	@GetMapping("/tasks")     //visualizza il form di inserimento all'avvio
    public String showMain(Model model) {
		//default chimare il JPQLQueri con filtro oggi 
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	 if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		    String currentUserName = authentication.getName();
    		   // model.addAttribute("ruolo",user.getUserByUsername(currentUserName).getRole());	
    		    model.addAttribute("ruolo",currentUserName);    		    
    		    ArrayList<Task> listTask = (ArrayList<Task>)queryservice.JPQLQuery();
   		        ArrayList<TaskEvasi> listTask2 = (ArrayList<TaskEvasi>)queryservice.JPQLQueryEvasi();
   		        ArrayList<Cliente> listClienti = (ArrayList<Cliente>)queryservice.JPQLQueryClientiCombo();
   		        model.addAttribute("clienti",listClienti);
   		        model.addAttribute("tasks", listTask);	
   		        model.addAttribute("tasks2", listTask2);	
    	 }
    	 return "mainTask1";	    	 
	}

	@GetMapping("/") //visualizza il form di inserimento all'avvio
    public String showDashBoard(Model model) {
				
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  //aggancia chi è autentificato 
    	 if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		    String currentUserName = authentication.getName();
    		   // model.addAttribute("ruolo",user.getUserByUsername(currentUserName).getRole());	
    		    model.addAttribute("ruolo",currentUserName);   
    		    ArrayList<Integer> elencoPrioritaOggi = (ArrayList<Integer>)queryservice.numberOfTask();
    		    ArrayList<Task> scadenze = (ArrayList<Task>)queryservice.dashScadenze();
    		    int[] arrayPri = new int[3]; 
    		    Priority elencoPriorita;
    		    if (!elencoPrioritaOggi.isEmpty()) {
    		    	 for(int i=0 ; i<3; i++) {
    	    		    	    	arrayPri[i] =  elencoPrioritaOggi.get(i);    	            
    	    		 } 
    	    		 elencoPriorita = new Priority(arrayPri[0],arrayPri[1],arrayPri[2]);
    	    		 model.addAttribute("priorita",elencoPriorita); 
    		    	
    		    } else {
    		         elencoPriorita = new Priority(0,0,0);
    		    }    		    
    		    model.addAttribute("priorita",elencoPriorita); 
    		    model.addAttribute("scadenze",scadenze);     		   
    	 }
    	 return "deck";	
    	
	}
	
	
	
	@RequestMapping("/clienti")     //visualizza il form di inserimento all'avvio
    public String showClienti(Model model) {

				ArrayList<Cliente> listClienti = (ArrayList<Cliente>)queryservice.JPQLQueryClienti();
      		    model.addAttribute("clienti", listClienti);	
      		    return "clienti";	
		
	}


	@RequestMapping("/impostazioni")     //visualizza il form di inserimento all'avvio
    public String impostazioni(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  //aggancia chi è autentificato 
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            // model.addAttribute("ruolo",user.getUserByUsername(currentUserName).getRole());    
             model.addAttribute("ruolo",currentUserName);   
            // aggancia i dati dalla tabella impostazioni, se presenti 
             ArrayList<Impostazioni> impostazioni = queryservice.GetImpostazioni(currentUserName);
             if (!impostazioni.get(0).getMittente().equals("")) {
                 model.addAttribute("impostazioni",impostazioni);  
             }
        }	    
      	return "impostazioni";			
	}
	
	
	

}
