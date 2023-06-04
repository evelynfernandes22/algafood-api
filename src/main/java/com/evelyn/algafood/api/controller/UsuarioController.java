package com.evelyn.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.algafood.api.DTO.UsuarioDTO;
import com.evelyn.algafood.api.DTO.input.SenhaInput;
import com.evelyn.algafood.api.DTO.input.UsuarioComSenhaInput;
import com.evelyn.algafood.api.DTO.input.UsuarioInput;
import com.evelyn.algafood.api.assembler.UsuarioDtoAssembler;
import com.evelyn.algafood.api.assembler.UsuarioInputDisassembler;
import com.evelyn.algafood.domain.model.Usuario;
import com.evelyn.algafood.domain.repository.UsuarioRepository;
import com.evelyn.algafood.domain.service.CadastroUsuarioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private CadastroUsuarioService cadastroUsuarioService;
	private UsuarioDtoAssembler usuarioDtoAssembler;
	private UsuarioInputDisassembler usuarioInputDisassembler;
	private UsuarioRepository usuarioRepository;
	
	
	@GetMapping
	public List<UsuarioDTO> listar(){
		
		return usuarioDtoAssembler.toCollectionModel(usuarioRepository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioDTO buscar (@PathVariable Long usuarioId) {
		
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		return usuarioDtoAssembler.toModel(usuario);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		return usuarioDtoAssembler.toModel(cadastroUsuarioService.salvar(usuario));
		
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
		
		Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		return usuarioDtoAssembler.toModel(cadastroUsuarioService.salvar(usuarioAtual));
	}
	
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
		
		cadastroUsuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
		
	}
}
