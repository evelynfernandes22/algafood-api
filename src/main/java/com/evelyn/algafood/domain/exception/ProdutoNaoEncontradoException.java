package com.evelyn.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{


	private static final long serialVersionUID = 1L;
	
	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	

	public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
		this(String.format("Não existe no restaurante %d o produto de código %d", restauranteId, produtoId));
	}
}
