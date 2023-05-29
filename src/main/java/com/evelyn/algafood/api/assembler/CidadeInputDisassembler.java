package com.evelyn.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.input.CidadeInput;
import com.evelyn.algafood.domain.model.Cidade;
import com.evelyn.algafood.domain.model.Estado;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CidadeInputDisassembler {

	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
		
		//Para evitar org.HibernateException: Identifier of an instance of ... was altered 1 to 2
		cidade.setEstado(new Estado());
		
		modelMapper.map(cidadeInput, cidade);
	}
}
