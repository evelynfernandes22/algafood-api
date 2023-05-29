package com.evelyn.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.CidadeDTO;
import com.evelyn.algafood.domain.model.Cidade;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CidadeDtoAssembler {

	private ModelMapper modelMapper;
	
	public CidadeDTO toModel(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDTO.class);
	}
	
	public List<CidadeDTO> toColletionModel(List<Cidade> cidades){
		return cidades.stream()
				.map(cidade -> toModel(cidade))
				.collect(Collectors.toList());
	}
}
