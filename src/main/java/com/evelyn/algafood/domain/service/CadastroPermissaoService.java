package com.evelyn.algafood.domain.service;

import org.springframework.stereotype.Service;

import com.evelyn.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.evelyn.algafood.domain.model.Permissao;
import com.evelyn.algafood.domain.repository.PermissaoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroPermissaoService {

	private PermissaoRepository permissaoRepository;
	
	public Permissao buscarOuFalhar (Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(()-> new PermissaoNaoEncontradaException(permissaoId));
	}
}
