package com.evelyn.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.evelyn.algafood.api.DTO.CidadeDTO;
import com.evelyn.algafood.api.DTO.input.CidadeInput;
import com.evelyn.algafood.api.assembler.CidadeDtoAssembler;
import com.evelyn.algafood.api.assembler.CidadeInputDisassembler;
import com.evelyn.algafood.domain.exception.EstadoNaoEncontradoException;
import com.evelyn.algafood.domain.exception.NegocioException;
import com.evelyn.algafood.domain.model.Cidade;
import com.evelyn.algafood.domain.repository.CidadeRepository;
import com.evelyn.algafood.domain.service.CadastroCidadeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	private CidadeRepository cidadeRepository;
	private CadastroCidadeService cadastroCidadeService;
	private CidadeDtoAssembler cidadeDtoAssembler;
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public List<CidadeDTO> listar() {

		return cidadeDtoAssembler.toColletionModel(cidadeRepository.findAll());
	}

	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {

		return cidadeDtoAssembler.toModel(cadastroCidadeService.buscarOuFalhar(cidadeId));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			return  cidadeDtoAssembler.toModel(cadastroCidadeService.salvar(cidade));

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
//			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			return cidadeDtoAssembler.toModel(cadastroCidadeService.salvar(cidadeAtual));

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidadeService.excluir(cidadeId);
	}

}
