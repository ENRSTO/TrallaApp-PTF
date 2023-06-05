package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Cliente;
import com.example.demo.model.Impostazioni;
import com.example.demo.model.Task;
import com.example.demo.model.TaskEvasi;
import com.example.demo.model.TaskSave;
import com.example.demo.service.QueryService;
import com.google.gson.Gson;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@RestController
@CrossOrigin("http://localhost:8080")
public class CompController {
	
	@Autowired
	QueryService qryService;
	
	
	@GetMapping("/decodeAll")  
	@ResponseBody
	public String getTaskAll() {
	
		ArrayList<Task> listRecord = (ArrayList<Task>)qryService.JPQLQueryAll();
		Gson g = new Gson();
		String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
		return valore;  // ritorna una stringa json

	}
	
	
	@GetMapping("/decodeAllscad")  
	@ResponseBody
	public String getTaskAllScaduti() {
	
		ArrayList<Task> listRecord = (ArrayList<Task>)qryService.JPQLQueryScaduti();
		Gson g = new Gson();
		String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
		return valore;  // ritorna una stringa json
				
	}
	
	
	//filtro dal picker data
	@GetMapping("/decodeData")  
	@ResponseBody
	public String getTaskData(@RequestParam("getDate")  @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dataconsegna) {
	
		ArrayList<Task> listRecord = (ArrayList<Task>)qryService.JPQLQueryDaCalendario(dataconsegna);
		Gson g = new Gson();
		String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
		return valore;  // ritorna una stringa json

	}
	
	//filtro dal picker data EVASI
	@GetMapping("/decodeDataEvasi")  
	@ResponseBody
	public String getTaskDataEvasi(@RequestParam("getDate")  @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dataconsegna) {
		
			ArrayList<TaskEvasi> listRecord = (ArrayList<TaskEvasi>)qryService.JPQLQueryDaCalendarioEvasi(dataconsegna);
			Gson g = new Gson();
			String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
			return valore;  // ritorna una stringa json che va sulla tabella dedicata

	}
	
	
	//implementazione
	@GetMapping("/scaduti")  
	@ResponseBody
	public String getTaskScaduti() {
	
		ArrayList<Task> listRecord = (ArrayList<Task>)qryService.JPQLQueryScaduti();
		Gson g = new Gson();
		String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
		return valore;  // ritorna una stringa json

	}
	
	@GetMapping("/evasiAll")  
	@ResponseBody
	public String getTaskEvasiAll() {
	
		ArrayList<TaskEvasi> listRecord = (ArrayList<TaskEvasi>)qryService.JPQLQueryEvasiAll();
		Gson g = new Gson();
		String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
		//return valore;  // ritorna una stringa json
        return valore;
	}
	
	
	@GetMapping("/evasi")  
	@ResponseBody
	public String getEvasi() {
	
		ArrayList<TaskEvasi> listRecord = (ArrayList<TaskEvasi>)qryService.JPQLQueryEvasi();
		Gson g = new Gson();
		String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
		return valore;  // ritorna una stringa json

	}
	
		
	
	@GetMapping("/decode")  // METODO per popolamento tabella 
	@ResponseBody
	public String getTask(@RequestParam String id_Days) {
	
		ArrayList<Task> listRecord = (ArrayList<Task>)qryService.JPQLQueryTaskGiorni(id_Days);
		Gson g = new Gson();
		String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
		return valore;  // ritorna una stringa json

	}
	
	
	
	//----------------------  controller x view deck  WIP   --------------------------------------------------------------
	
	@GetMapping("/decodescadenze")  // METODO per popolamento tabella Scadenza nella dashboard 
	@ResponseBody
	public String getScadenzeRosse() {
	
	//	ArrayList<Task> listRecord = (ArrayList<Task>)qryService.JPQLQueryTaskGiorni(id_Days);
		Gson g = new Gson();
	//	String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
	    return null; //valore;  // ritorna una stringa json

	}
	
	
	//---------------------- fine controller x view deck 
	
	
	//repnota
	@GetMapping("/repnota")  // METODO per popolamento tabella 
	@ResponseBody
	public String getNota(@RequestParam("idreminder") BigInteger idreminder) {
	
		String nota = (String) qryService.trovaNota(idreminder);
		//Gson g = new Gson();
		//String notaJson = g.toJson(nota);	// trasforma in stringa array json l'arraylist passato in ingresso
		return nota;  // ritorna una stringa json

	}
	
	
	
	
	@PostMapping("/save")  // METODO persistenza dato 
	@ResponseBody
	//  var vObj ={cliente: cliente, lavoro: task, dataconsegna: date, phone: phone, email: email, priorita: priorita, tipo: tipoAttivita, note: nota}
	public String getJsonSave(@RequestParam("cliente") String cliente, @RequestParam("lavoro") String lavoro, @RequestParam("dataconsegna")  @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna, @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("priorita") int priorita, @RequestParam("tipo") char tipo,
			 @RequestParam("note") String note) {
	     
		TaskSave insertRecord;
		if (!note.isEmpty()) {
			 insertRecord =  new TaskSave(cliente, lavoro, dataconsegna, phone, email, priorita, tipo,'A',note,'S'); //ci sono delle note			
		} else {	
			insertRecord = new TaskSave(cliente, lavoro, dataconsegna, phone, email, priorita, tipo,'A',null,' '); // aggiungi le note e lo stato che cè la nota
		}
		return qryService.JPQLsaveRecord(insertRecord);
	}
	
	
	
	@PostMapping("/modifica")  // METODO persistenza dato in modifica 
	@ResponseBody
	public String getJsonModifica(@RequestParam("idreminder") BigInteger idreminder, @RequestParam("cliente") String cliente, @RequestParam("lavoro") String lavoro, @RequestParam("dataconsegna")  @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna, @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("priorita") int priorita,  @RequestParam("note") String note) {
	
		TaskSave modificaRecord;
		if (!note.isEmpty()) {
			modificaRecord =  new TaskSave(idreminder, cliente, lavoro, dataconsegna, phone, email, priorita,note);
			modificaRecord.setStatonota('S');
		} else {
			modificaRecord =  new TaskSave(idreminder, cliente, lavoro, dataconsegna, phone, email, priorita);
			modificaRecord.setStatonota('N');
		}
		   
	    return qryService.JPQLsaveModifica(modificaRecord);
	}
	
		
	

	
	@PostMapping("/saveAmm")  // METODO persistenza dato 
	@ResponseBody
	public String getJsonSaveAmm(@RequestParam("cliente") String cliente, @RequestParam("lavoro") String lavoro, @RequestParam("dataconsegna")  @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna, @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("priorita") int priorita, @RequestParam("tipo") char tipo, 
			@RequestParam("ripetizione") int ripetizione, @RequestParam("numeroRipetizioni") int numeroRipetizioni ) {
	    
	
		TaskSave insertRecord = new TaskSave(cliente, lavoro, dataconsegna, phone, email, priorita, tipo,'A');
		return qryService.JPQLsaveRecordAmmRepeat(insertRecord, ripetizione, numeroRipetizioni);		
		
	}
	
	
	@PostMapping("/saveScad")  // METODO persistenza dato  da view dashBoard
	@ResponseBody
	public String getJsonSaveScadenza(@RequestParam("scadenza") String lavoro, @RequestParam("dataconsegna")  @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna) {
	    	
		TaskSave insertRecord = new TaskSave("user", lavoro, dataconsegna, "", "",1,'S','A');
		return qryService.JPQLsaveRecordAmm(insertRecord);	
		
	}
	
	

	@PostMapping("/evadi")  // METODO persistenza dato in modifica 
	@ResponseBody
	public String evasione(@RequestParam("idreminder") BigInteger idreminder) {
			return qryService.JPQLsaveEvasione(idreminder);
		
	}
	
	
	
	@PostMapping("/ripristina")  // METODO persistenza x il ripristino del Task 
	@ResponseBody
	public String Ripristina(@RequestParam("idRem") int id) {
				
	    return qryService.JPQLRipristina(id);
	}
	
	
	
	@GetMapping("/delete/{id}")	 
    public String DeleteTask(@PathVariable("id") long id, Model model) {
	 
        //User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        //userRepository.delete(user);
        return  "redirect:/redirect";
     	       
    }
	
	
	@PostMapping("/deletes")
	@ResponseBody
	public String DeleteTask(@RequestParam(value="myArray[]") List<Integer> myArray) {		
		return qryService.JPQLDeleteRecords(myArray);				 
	}
	
	
	@PostMapping("/modificdata")  // METODo x spostamento eventi 
	@ResponseBody
	public String getJsonModificaData(@RequestParam(value="myArray[]") List<Integer> myArray, @RequestParam("dataNuova")  @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna) {
	
		return qryService.ModificaData(myArray,dataconsegna);
		
	}
	
	@PostMapping("/duplicaTask")  // METODo x spostamento eventi 
	@ResponseBody
	public String getJsonDuplicaData(@RequestParam(value="myArray[]") List<Integer> myArray, @RequestParam("dataNuova")  @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna) {
	
		return qryService.DuplicaRecord(myArray, dataconsegna);
		
	}
	
	//copiaTask
	@PostMapping("/copiaTask")  // METODo x spostamento eventi 
	@ResponseBody
	public String copiaTask(@RequestParam("chiave") BigInteger idreminder) {
	
		return qryService.CopiaRecord(idreminder);
		
	}
	
	
	
	//eliminaTask
	@PostMapping("/eliminaTask")  // METODo x spostamento eventi 
	@ResponseBody
	public String EliminaTask(@RequestParam("chiave") BigInteger idreminder) {
	
		return qryService.EliminaRecord(idreminder);
			
	}
		
		
	@PostMapping("/MoveTask")  // METODo x spostamento eventi 
	@ResponseBody
	public String MoveTask(@RequestParam("key") BigInteger idreminder,@RequestParam("dataNuova")  @DateTimeFormat(pattern="yyyy-MM-dd") Date dataconsegna) {
		
		return qryService.MoveRecord(idreminder,dataconsegna);
			
	}
			
	
	
	
	@GetMapping("/decomailphone")  
	@ResponseBody
	public String getDecodificaMailPhone(@RequestParam("getCliente") String cliente) {
	
		ArrayList<Cliente> listRecord = (ArrayList<Cliente>)qryService.JPQLQueryClientiReperimentoDati(cliente);
		Gson g = new Gson();
		String valore = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
		return valore;  // ritorna una stringa json

	}
	
//----  VIEW IMPOSTAZIONI 	
	
	
	@PostMapping("/saveImpo")  // METODO per salvataggio impostazioni 	
	@ResponseBody		
	public void getJsonSaveImpostazioni(@RequestBody Map<String, Object> parametri) throws Exception  {
	
		  Impostazioni insertRecord;		
		  String  mittP, mailP, hostP,googleP,portP; 		
		
	      if (parametri.get("mittente") == null) {
	    	  System.out.println("Mittente non valorizzato");	
	    	  mittP = "";
	      } else {mittP =parametri.get("mittente").toString();}
	      if (parametri.get("mail") == null) {
	    	  System.out.println("mail non valorizzata");	
	    	  mailP ="";
	      } else {mailP = parametri.get("mail").toString();}
	      if (parametri.get("host") == null) {
	    	  System.out.println("host non valorizzato");
	    	  hostP ="";
	      } else {hostP = parametri.get("host").toString();}
	      if (parametri.get("port") == null) {
	    	  System.out.println("port non valorizzata");	
	    	  portP = "";
	      } else {portP =  parametri.get("port").toString();}
	      if (parametri.get("googleKey") == null) {
	    	  System.out.println("googleKey non valorizzata");
	    	  googleP ="";
	      } else {googleP = parametri.get("googleKey").toString();}
	   
		
		  insertRecord =  new Impostazioni(parametri.get("utente").toString(),mittP, hostP, Integer.parseInt(portP), googleP, mailP);		
	      qryService.JPQLsaveRecordImpostazioni(insertRecord);
	    
	}
	
	
	
	
//------------------------VIEW clienti ---------------------------------------------------
	
	@PostMapping("/saveC")  // METODO persistenza dato 
	@ResponseBody
	//{codcliente: cliente, ragione: ragione, indirizzo: indirizzo, phone: telefono, email: email};
	public String getJsonSaveC(@RequestParam("codcliente") String cliente, @RequestParam("ragione") String ragione, @RequestParam("indirizzo") String indirizzo, @RequestParam("phone") String phone, @RequestParam("email") String email) {
	     
		Cliente insertRecord;		
		insertRecord =  new Cliente(cliente, ragione, indirizzo, phone, email); 
		
		return qryService.JPQLsaveRecordC(insertRecord);
	}	
	
	@PostMapping("/modificaC")  // METODO persistenza dato 
	@ResponseBody
	public String getJsonModificaC(@RequestParam("idclienti") BigInteger id,  @RequestParam("codcliente") String cliente, @RequestParam("ragione") String ragione, @RequestParam("indirizzo") String indirizzo, @RequestParam("phone") String phone, @RequestParam("email") String email) {
	     
		Cliente insertRecord;		
		insertRecord =  new Cliente(id, cliente, ragione, indirizzo, phone, email); 
		
		return qryService.JPQLmodificaRecordC(insertRecord);
	}	
	

	@GetMapping("/verifica")  // METODO per popolamento tabella 
	@ResponseBody
	public boolean esistoComeCliente(@RequestParam("codcliente") String cliente){		
		 
		return qryService.verificaCliente(cliente);
		
	}
	
	
	@GetMapping("/daticliente")
	@ResponseBody
	public String agganciaDatiCliente(@RequestParam("codcliente") String cliente){		
				
		ArrayList<Cliente> listRecord = (ArrayList<Cliente>)qryService.agganciaDatiC(cliente);
		Gson g = new Gson();
		String daticliente = g.toJson(listRecord);	// trasforma in stringa array json l'arraylist passato in ingresso
		return daticliente;
		
		
	}
	
	@PostMapping("/eliminacliente")
	@ResponseBody 
	public String eliminacliente(@RequestParam("idclienti") BigInteger id) {
		return qryService.eliminacliente(id);
	}
	
	
	
	
	
//------------------------pagina clienti ---------------------------------------------------	
	
	
	
	
// -----------------REPORTISTICA------------------------------------------------------------------------------- 
	
	
	
	@GetMapping("/reportistica")
	@ResponseBody
	public String creaPdf(@RequestParam(value="myArray[]") List<Integer> myArray) throws FileNotFoundException, JRException{
		
		
				File theDir = new File("C:/report");
				if (!theDir.exists()){
					theDir.mkdirs();
				}
				String reportPath = "C:\\report";
				JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/report.jrxml"));
				// Add parameters
				Map<String, Object> parameters = new HashMap<>();

				parameters.put("createdBy", "enrsto-SW");
				ArrayList<Task> listRecord = qryService.JPQLQueryforReport(myArray);
				
				JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listRecord);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,jrBeanCollectionDataSource);
				
				JasperExportManager.exportReportToPdfFile(jasperPrint,reportPath+"\\report1.pdf");
			
				try {
					visualizzaPdf(reportPath+"\\report1.pdf");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      //  return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
				return "Done";
	}

	
	public void visualizzaPdf (String generatedFile) throws IOException{
		
		ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", "explorer "+generatedFile);
		processBuilder.start();
		
	}
	
	
	
	   
	   

}
