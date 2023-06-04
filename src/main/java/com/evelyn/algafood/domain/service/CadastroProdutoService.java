package com.evelyn.algafood.domain.service;

import org.springframework.stereotype.Service;

import com.evelyn.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.evelyn.algafood.domain.model.Produto;
import com.evelyn.algafood.domain.repository.ProdutoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroProdutoService {

	private ProdutoRepository produtoRepository;
	
	public Produto salvar (Produto produto) {
		return produtoRepository.save(produto);
	}
	
	 public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
	        return produtoRepository.findById(restauranteId, produtoId)
	            .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
	    } 
}
