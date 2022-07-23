package com.projeto.os.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.os.dtos.OSDTO;
import com.projeto.os.entities.OS;
import com.projeto.os.services.OSService;

@RestController
@RequestMapping(value = "/os")
public class OSController {
	
	@Autowired
	private OSService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<OSDTO> findById(@PathVariable Long id) throws IllegalAccessException{
		OSDTO obj = new OSDTO(service.findById(id));
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping
	public ResponseEntity<List<OSDTO>> findAll() throws IllegalAccessException{
		List<OS> list = service.findAll();
		List<OSDTO> listDTO = new ArrayList<>();
		
		for(OS obj : list) {
			listDTO.add(new OSDTO(obj));
		}
		
		return ResponseEntity.ok().body(listDTO);
	}
}
