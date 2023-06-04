package com.evelyn.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.input.ProdutoInput;
import com.evelyn.algafood.domain.model.Produto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ProdutoInputDisassembler {

	private ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoInput produtoInput){
		return modelMapper.map(produtoInput, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
		modelMapper.map(produtoInput, produto);
	}
}
