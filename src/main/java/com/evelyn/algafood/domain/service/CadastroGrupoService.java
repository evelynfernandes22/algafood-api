package com.evelyn.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evelyn.algafood.domain.exception.EntidadeEmUsoException;
import com.evelyn.algafood.domain.exception.GrupoNaoEncontradoException;
import com.evelyn.algafood.domain.model.Grupo;
import com.evelyn.algafood.domain.model.Permissao;
import com.evelyn.algafood.domain.repository.GrupoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroGrupoService {

	private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso"; 
	
	private GrupoRepository grupoRepository;
	private CadastroPermissaoService cadastroPermissaoService;
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void excluir(Long grupoId) {
		
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
			
		}catch(EmptyResultDataAccessException e){
			throw new GrupoNaoEncontradoException(grupoId);
			
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
		}
		
	}
	
	public Grupo buscarOuFalhar(Long grupoId) {
		return	grupoRepository.findById(grupoId).orElseThrow(
				() -> new GrupoNaoEncontradoException(grupoId));
		
	}
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		
		grupo.removerPermissoes(permissao);
	}
	
	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		
		grupo.adicionarPermissoes(permissao);
	}
}
