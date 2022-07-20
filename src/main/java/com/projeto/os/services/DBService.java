package com.projeto.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.os.entities.Cliente;
import com.projeto.os.entities.OS;
import com.projeto.os.entities.Tecnico;
import com.projeto.os.entities.enuns.Prioridade;
import com.projeto.os.entities.enuns.Status;
import com.projeto.os.repositories.ClienteRepository;
import com.projeto.os.repositories.OSRepository;
import com.projeto.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private OSRepository osRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Eduardo", "591.212.220-45", "(81) 98888-5555");
		Cliente c1 = new Cliente(null, "Danielle", "069.494.660-56", "(81) 95555-8888");
		OS os1 = new OS(null, Prioridade.ALTA, Status.ANDAMENTO, "Trocar fonte do notebook", t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));
	}
}