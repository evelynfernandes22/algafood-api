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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.algafood.api.DTO.ProdutoDTO;
import com.evelyn.algafood.api.DTO.input.ProdutoInput;
import com.evelyn.algafood.api.assembler.ProdutoDtoAssembler;
import com.evelyn.algafood.api.assembler.ProdutoInputDisassembler;
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
	private ProdutoInputDisassembler produtoInputDisassembler;
	private ProdutoRepository produtoRepository;


	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId, 
			@RequestParam(required=false) boolean incluiInativos){
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		List<Produto> produtos = null;
		
		if(incluiInativos) {
			produtos = produtoRepository.findTodosByRestaurante(restaurante);
		}else {
			produtos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		
		return produtoDtoAssembler.toCollectionModel(produtos);
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar (@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		return produtoDtoAssembler.toModel(produto);
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		
		produto.setRestaurante(restaurante);
		
		return produtoDtoAssembler.toModel(cadastroProdutoService.salvar(produto));
	}

	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		return produtoDtoAssembler.toModel(cadastroProdutoService.salvar(produtoAtual));
	}

}