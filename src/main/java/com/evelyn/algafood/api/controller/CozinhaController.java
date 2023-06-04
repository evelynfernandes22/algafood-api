package com.evelyn.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.algafood.api.DTO.CozinhaDTO;
import com.evelyn.algafood.api.DTO.input.CozinhaInput;
import com.evelyn.algafood.api.assembler.CozinhaDtoAssembler;
import com.evelyn.algafood.api.assembler.CozinhaInputDisassembler;
import com.evelyn.algafood.domain.model.Cozinha;
import com.evelyn.algafood.domain.repository.CozinhaRepository;
import com.evelyn.algafood.domain.service.CadastroCozinhaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	private CozinhaRepository cozinhaRepository;
	private CadastroCozinhaService cadastroCozinhaService;
	private CozinhaDtoAssembler cozinhaDtoAssembler;
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@GetMapping
	public List<CozinhaDTO> listar() {
		return cozinhaDtoAssembler.toCollectionModel(cozinhaRepository.findAll());
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable("cozinhaId") Long Cozinhaid) {
		return cozinhaDtoAssembler.toModel(cadastroCozinhaService.buscarOuFalhar(Cozinhaid));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		return cozinhaDtoAssembler.toModel(cadastroCozinhaService.salvar(cozinha));
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		return cozinhaDtoAssembler.toModel(cadastroCozinhaService.salvar(cozinhaAtual));

	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinhaService.excluir(cozinhaId);

	}
}