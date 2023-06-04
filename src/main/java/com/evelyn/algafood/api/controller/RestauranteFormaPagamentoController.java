package com.evelyn.algafood.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.algafood.api.DTO.FormaPagamentoDTO;
import com.evelyn.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.evelyn.algafood.domain.model.Restaurante;
import com.evelyn.algafood.domain.service.CadastroRestauranteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	private CadastroRestauranteService cadastroRestauranteService;
	private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId){
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		return formaPagamentoDtoAssembler.toCollectionModel(restaurante.getFormasPagamento());
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
	}

}