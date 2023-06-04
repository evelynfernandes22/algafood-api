package com.evelyn.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.input.GrupoInput;
import com.evelyn.algafood.domain.model.Grupo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class GrupoInputDisassembler {

	private ModelMapper modelMapper;
	
	public Grupo toDomainModel(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}
}
