package com.evelyn.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.PermissaoDTO;
import com.evelyn.algafood.domain.model.Permissao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PermissaoDtoAssembler {

	private ModelMapper modelMapper;
	
	public PermissaoDTO toModel (Permissao permissao) {
		return modelMapper.map(permissao, PermissaoDTO.class);
	}
	
	public List<PermissaoDTO> toCollectionModel (Collection<Permissao> permissoes){
		return permissoes.stream()
				.map(permissao -> toModel(permissao))
				.collect(Collectors.toList());
	}
}
