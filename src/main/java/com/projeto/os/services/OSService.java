package com.projeto.os.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.os.entities.OS;
import com.projeto.os.repositories.OSRepository;
import com.projeto.os.services.exceptions.ObjectNotFoundException;

@Service
public class OSService {

	@Autowired
	private OSRepository repository; 
	
	public OS findById(Long id) {
		Optional<OS> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + OS.class.getName()));
	}
	
	public List<OS> findAll(){
		return repository.findAll();
	}
}
