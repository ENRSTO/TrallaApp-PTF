package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.model.TaskSave;
import com.example.demo.model.Task;

public interface IqueryService {
	
	ArrayList<Task> JPQLQuery();
	ArrayList<Task> JPQLQueryAmm();
	//ArrayList<Task> JPQLQueryFindName(String name);
	ArrayList<Task> JPQLQueryTaskGiorni(String giorni);
	String JPQLsaveRecord (TaskSave recordSave);
	
}
