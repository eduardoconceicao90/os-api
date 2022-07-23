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
		Tecnico t2 = new Tecnico(null, "Gabriel", "710.003.750-64", "(81) 99999-5555");
		Tecnico t3 = new Tecnico(null, "Ronaldo", "177.504.790-31", "(81) 97777-5555");
		
		Cliente c1 = new Cliente(null, "Danielle", "069.494.660-56", "(81) 95555-8888");
		Cliente c2 = new Cliente(null, "Telma", "794.659.920-03", "(81) 95555-7777");
		
		OS os1 = new OS(null, Prioridade.ALTA, Status.ANDAMENTO, "Trocar fonte do notebook", t1, c1);
		OS os2 = new OS(null, Prioridade.BAIXA, Status.ABERTO, "Leitor com problema", t2, c2);

		t1.getList().add(os1);
		t2.getList().add(os2);
		c1.getList().add(os1);
		c2.getList().add(os2);

		tecnicoRepository.saveAll(Arrays.asList(t1, t2, t3));
		clienteRepository.saveAll(Arrays.asList(c1, c2));
		osRepository.saveAll(Arrays.asList(os1, os2));
	}
}
