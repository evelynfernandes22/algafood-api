package com.evelyn.algafood.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.evelyn.algafood.api.DTO.FotoProdutoDTO;
import com.evelyn.algafood.api.DTO.input.FotoProdutoInput;
import com.evelyn.algafood.api.assembler.FotoProdutoDtoAssembler;
import com.evelyn.algafood.domain.model.FotoProduto;
import com.evelyn.algafood.domain.model.Produto;
import com.evelyn.algafood.domain.service.CadastroProdutoService;
import com.evelyn.algafood.domain.service.CatalogoFotoProdutoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	private CadastroProdutoService cadastroProdutoService;
	private FotoProdutoDtoAssembler fotoProdutoDtoAssembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, 
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		foto.setTamanho(arquivo.getSize());
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());
		
		return fotoProdutoDtoAssembler.toModel(fotoSalva);
	}
	
	@GetMapping
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return fotoProdutoDtoAssembler.toModel(catalogoFotoProdutoService
				.buscarOuFalhar(restauranteId, produtoId));
	}
}
