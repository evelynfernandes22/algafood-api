package com.evelyn.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.ProdutoDTO;
import com.evelyn.algafood.domain.model.Produto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ProdutoDtoAssembler {

	private ModelMapper modelMapper;
	
	public ProdutoDTO toModel (Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> toCollectionModel(List<Produto> produtos){
		return produtos.stream()
				.map(produto -> toModel(produto))
				.collect(Collectors.toUnmodifiableList());
	}
}
