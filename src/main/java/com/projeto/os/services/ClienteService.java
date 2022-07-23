package com.projeto.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.os.dtos.ClienteDTO;
import com.projeto.os.entities.Pessoa;
import com.projeto.os.entities.Cliente;
import com.projeto.os.repositories.PessoaRepository;
import com.projeto.os.repositories.ClienteRepository;
import com.projeto.os.services.exceptions.DataIntegratyViolationException;
import com.projeto.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	// Busca Cliente pelo ID
	public Cliente findById(Long id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + Cliente.class.getName()));
	}

	// Lista todos os Clientes
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	// Cria Cliente
	public Cliente create(ClienteDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return clienteRepository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	// Atualiza Cliente
	public Cliente update(Long id, @Valid ClienteDTO objDto) {
		Cliente oldObj = findById(id);

		if (findByCPF(objDto) != null && findByCPF(objDto).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}

		oldObj.setNome(objDto.getNome());
		oldObj.setCpf(objDto.getCpf());
		oldObj.setTelefone(objDto.getTelefone());
		return clienteRepository.save(oldObj);
	}

	// Deleta Cliente
	public void delete(Long id) {
		Cliente obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui ordem de serviços, não pode ser deletado!");
		}
		clienteRepository.deleteById(id);
	}

	// Busca Cliente pelo CPF
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
