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

import com.evelyn.algafood.api.DTO.FormaPagamentoDTO;
import com.evelyn.algafood.api.DTO.input.FormaPagamentoInput;
import com.evelyn.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.evelyn.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.evelyn.algafood.domain.model.FormaPagamento;
import com.evelyn.algafood.domain.repository.FormaPagamentoRepository;
import com.evelyn.algafood.domain.service.CadastroFormaPagamentoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	private FormaPagamentoRepository formaPagamentoRepository;
	private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	

	@GetMapping
	public List<FormaPagamentoDTO> listar (){
		return formaPagamentoDtoAssembler.toCollectionModel(
				formaPagamentoRepository.findAll());
	}
	
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		return formaPagamentoDtoAssembler.toModel(formaPagamento);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	
	public FormaPagamentoDTO adicionar (@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		 
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
		return formaPagamentoDtoAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));
	}
	
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		formaPagamentoInputDisassembler.copyToDomaiObject(formaPagamentoInput, formaPagamentoAtual);
		return formaPagamentoDtoAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamentoAtual));
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamentoService.excluir(formaPagamentoId);
	}
}
