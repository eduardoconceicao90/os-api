package com.projeto.os.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class Pessoa {

	private Long id;
	private String nome;
	private String cpf;
	private String telefone;
	
	
}
