package com.evelyn.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.CozinhaDTO;
import com.evelyn.algafood.domain.model.Cozinha;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CozinhaDtoAssembler {
	
	private ModelMapper modelMapper; 
	
	public CozinhaDTO toModel(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}
	
	public List<CozinhaDTO> toCollectionModel(List<Cozinha> cozinhas){
		
		return cozinhas.stream()
				.map(cozinha -> toModel(cozinha))
				.collect(Collectors.toList());
	}

}
