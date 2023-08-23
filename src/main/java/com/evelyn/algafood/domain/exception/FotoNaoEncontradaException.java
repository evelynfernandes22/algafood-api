package com.evelyn.algafood.domain.exception;

public class FotoNaoEncontradaException extends EntidadeNaoEncontradaException {

private static final long serialVersionUID = 1L;
	
	public FotoNaoEncontradaException(String mensagem){
		super(mensagem);
	}

	public FotoNaoEncontradaException (Long restauranteId, Long produtoId) {
		this(String.format("Não existe uma foto cadastrada do produto %d referente ao restaurante %d",
				produtoId, restauranteId));
	}
}
