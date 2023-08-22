package com.evelyn.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.FotoProdutoDTO;
import com.evelyn.algafood.domain.model.FotoProduto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class FotoProdutoDtoAssembler {

	private ModelMapper modelMapper;
	
	public FotoProdutoDTO toModel(FotoProduto foto) {
		return modelMapper.map(foto, FotoProdutoDTO.class);
	}
	
}
