package com.evelyn.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.GrupoDTO;
import com.evelyn.algafood.domain.model.Grupo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class GrupoDtoAssembler {
	
	private ModelMapper modelMapper;
	
	public GrupoDTO toModel(Grupo grupo) {
		return modelMapper.map(grupo, GrupoDTO.class);
	}
	
	public List<GrupoDTO> toCollectionModel(List<Grupo> grupos){
		return grupos.stream()
				.map(grupo -> toModel(grupo))
				.collect(Collectors.toList());
	}
}
