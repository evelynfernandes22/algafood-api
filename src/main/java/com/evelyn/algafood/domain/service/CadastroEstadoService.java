package com.evelyn.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evelyn.algafood.domain.exception.EntidadeEmUsoException;
import com.evelyn.algafood.domain.exception.EstadoNaoEncontradoException;
import com.evelyn.algafood.domain.model.Estado;
import com.evelyn.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO = "O Estado de código %d não pode ser removido, pois está em uso.";

 
	@Autowired
	private EstadoRepository estadoRepository;

	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void remover (Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));

		}
	}

	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId).orElseThrow(
				() -> new EstadoNaoEncontradoException(estadoId));
	}
}
