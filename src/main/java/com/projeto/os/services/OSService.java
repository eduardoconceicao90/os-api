package com.projeto.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.os.dtos.OSDTO;
import com.projeto.os.entities.Cliente;
import com.projeto.os.entities.OS;
import com.projeto.os.entities.Tecnico;
import com.projeto.os.entities.enuns.Prioridade;
import com.projeto.os.entities.enuns.Status;
import com.projeto.os.repositories.OSRepository;
import com.projeto.os.services.exceptions.ObjectNotFoundException;

@Service
public class OSService {

	@Autowired
	private OSRepository repository; 
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public OS findById(Long id) {
		Optional<OS> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + OS.class.getName()));
	}
	
	public List<OS> findAll(){
		return repository.findAll();
	}

	public OS create(@Valid OSDTO obj) throws IllegalAccessException {
		return fromDTO(obj);
	}
	
	public OS update(@Valid OSDTO obj) throws IllegalAccessException {
		findById(obj.getId());
		return fromDTO(obj);
	}
	
	private OS fromDTO(OSDTO obj) throws IllegalAccessException {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade().getCod()));
		newObj.setStatus(Status.toEnum(obj.getStatus().getCod()));
		
		Tecnico tec = tecnicoService.findById(obj.getTecnico());
		Cliente cli = clienteService.findById(obj.getCliente());
		
		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		if (newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}
		
		return repository.save(newObj);
	}

}
