package com.evelyn.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evelyn.algafood.domain.exception.EntidadeEmUsoException;
import com.evelyn.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.evelyn.algafood.domain.model.FormaPagamento;
import com.evelyn.algafood.domain.repository.FormaPagamentoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroFormaPagamentoService {

	private static final String FORMA_PAGAMENTO_EM_USO = "A Forma de Pagamento de código %d não pode ser removida, pois está em uso";
	FormaPagamentoRepository formaPagamentoRepository;
	
	@Transactional
	public FormaPagamento salvar (FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush(); //forçar a descarga do jpa
			
		}catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradoException(id);
			
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(FORMA_PAGAMENTO_EM_USO, id));
		}
	}
	
	public FormaPagamento buscarOuFalhar (Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradoException(formaPagamentoId));
	}
}
