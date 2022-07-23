package com.projeto.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.os.dtos.TecnicoDTO;
import com.projeto.os.entities.Pessoa;
import com.projeto.os.entities.Tecnico;
import com.projeto.os.repositories.PessoaRepository;
import com.projeto.os.repositories.TecnicoRepository;
import com.projeto.os.services.exceptions.DataIntegratyViolationException;
import com.projeto.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	// Busca Tecnico pelo ID
	public Tecnico findById(Long id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + Tecnico.class.getName()));
	}

	// Lista todos os Tecnicos
	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	// Cria Tecnico
	public Tecnico create(TecnicoDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return tecnicoRepository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	// Atualiza Tecnico
	public Tecnico update(Long id, @Valid TecnicoDTO objDto) {
		Tecnico oldObj = findById(id);

		if (findByCPF(objDto) != null && findByCPF(objDto).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}

		oldObj.setNome(objDto.getNome());
		oldObj.setCpf(objDto.getCpf());
		oldObj.setTelefone(objDto.getTelefone());
		return tecnicoRepository.save(oldObj);
	}

	// Deleta Tecnico
	public void delete(Long id) {
		Tecnico obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Tecnico possui ordem de serviços, não pode ser deletado!");
		}
		tecnicoRepository.deleteById(id);
	}

	// Busca Tecnico pelo CPF
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
