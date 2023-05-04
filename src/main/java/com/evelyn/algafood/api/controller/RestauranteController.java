package com.evelyn.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.evelyn.algafood.domain.exception.NegocioException;
import com.evelyn.algafood.domain.model.Restaurante;
import com.evelyn.algafood.domain.repository.RestauranteRepository;
import com.evelyn.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
		
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable ("restauranteId") Long restauranteId){
		return cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
	}


	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Restaurante adicionar (@RequestBody @Valid Restaurante restaurante){
		try {
			return cadastroRestauranteService.salvar(restaurante);
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId,@RequestBody Restaurante restaurante){
		
		try {
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			BeanUtils.copyProperties(restaurante, restauranteAtual,"id", "formasPagamento", "endereco", 
					"dataCadastro", "dataAtualizacao", "produtos");
			return cadastroRestauranteService.salvar(restauranteAtual);
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PatchMapping ("/{restauranteId}")
	public Restaurante atualizarParcial (@PathVariable Long restauranteId, @RequestBody Map<String, Object> dados, HttpServletRequest request ){
		
		try {
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			
			merge(dados, restauranteAtual, request);
			
			return atualizar(restauranteId, restauranteAtual);
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
			HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
				Throwable rootCause = ExceptionUtils.getRootCause(e);
				throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){
		
		cadastroRestauranteService.excluir(restauranteId);
	}
}