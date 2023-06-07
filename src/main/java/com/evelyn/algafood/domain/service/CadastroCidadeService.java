package com.evelyn.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evelyn.algafood.domain.exception.CidadeNaoEncontradaException;
import com.evelyn.algafood.domain.exception.EntidadeEmUsoException;
import com.evelyn.algafood.domain.model.Cidade;
import com.evelyn.algafood.domain.model.Estado;
import com.evelyn.algafood.domain.repository.CidadeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroCidadeService {

	private static final String MSG_CODIGO_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";


	private CidadeRepository cidadeRepository;
	private CadastroEstadoService cadastroEstadoService;

	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}
	
	@Transactional
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CODIGO_CIDADE_EM_USO, cidadeId));
		}
	}

	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(
				() -> new CidadeNaoEncontradaException(cidadeId));
	}
}
