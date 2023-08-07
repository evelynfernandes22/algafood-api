package com.evelyn.algafood.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.evelyn.algafood.domain.exception.NegocioException;
import com.evelyn.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.evelyn.algafood.domain.model.Grupo;
import com.evelyn.algafood.domain.model.Usuario;
import com.evelyn.algafood.domain.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroUsuarioService {
	
	private UsuarioRepository usuarioRepository;
	private CadastroGrupoService cadastroGrupoService;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent()&& !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		if(usuario.senhaNaoCoincideCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário, tente novamente!");
		}
		usuario.setSenha(novaSenha);
	}

	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(
				() -> new UsuarioNaoEncontradoException(usuarioId));
		
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
	    Usuario usuario = buscarOuFalhar(usuarioId);
	    Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
	    
	    usuario.removerGrupo(grupo);
	}

	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
	    Usuario usuario = buscarOuFalhar(usuarioId);
	    Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
	    
	    usuario.adicionarGrupo(grupo);
	}
	
	
	
}
