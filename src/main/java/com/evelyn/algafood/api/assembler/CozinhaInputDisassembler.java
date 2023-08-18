package com.evelyn.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.input.CozinhaInput;
import com.evelyn.algafood.domain.model.Cozinha;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CozinhaInputDisassembler {

	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}

	public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
		
		modelMapper.map(cozinhaInput,cozinha);
	}
}
