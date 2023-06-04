package com.evelyn.algafood.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.algafood.api.DTO.ProdutoDTO;
import com.evelyn.algafood.api.assembler.ProdutoDtoAssembler;
import com.evelyn.algafood.domain.model.Produto;
import com.evelyn.algafood.domain.model.Restaurante;
import com.evelyn.algafood.domain.repository.ProdutoRepository;
import com.evelyn.algafood.domain.service.CadastroProdutoService;
import com.evelyn.algafood.domain.service.CadastroRestauranteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	private CadastroRestauranteService cadastroRestauranteService;
	private CadastroProdutoService cadastroProdutoService;
	private ProdutoDtoAssembler produtoDtoAssembler;

	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId){
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		return produtoDtoAssembler.toCollectionModel(restaurante.getProdutos());
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar (@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		return produtoDtoAssembler.toModel(produto);
	}
	
	


}