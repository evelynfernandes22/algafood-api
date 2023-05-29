package com.evelyn.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
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

import com.evelyn.algafood.api.DTO.CozinhaDTO;
import com.evelyn.algafood.api.DTO.RestauranteDTO;
import com.evelyn.algafood.api.DTO.input.RestauranteInput;
import com.evelyn.algafood.core.validation.ValidacaoException;
import com.evelyn.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.evelyn.algafood.domain.exception.NegocioException;
import com.evelyn.algafood.domain.model.Cozinha;
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
	
	@Autowired
	private SmartValidator validator;
	
	@GetMapping
	public List<RestauranteDTO> listar(){
		return toCollectionModel(restauranteRepository.findAll());
		
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable ("restauranteId") Long restauranteId){
		Restaurante restaurante =  cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		RestauranteDTO dto = toModel(restaurante);
		
		return dto;
	}
	

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public RestauranteDTO adicionar (@RequestBody @Valid RestauranteInput restauranteInput){
		try {
			Restaurante restaurante = toDomainObject(restauranteInput);
			return toModel(cadastroRestauranteService.salvar(restaurante));
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,@RequestBody @Valid RestauranteInput restauranteInput){
		
		try {
			Restaurante restaurante = toDomainObject(restauranteInput);
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			BeanUtils.copyProperties(restaurante, restauranteAtual,"id", "formasPagamento", "endereco", 
					"dataCadastro", "dataAtualizacao", "produtos");
			return toModel(cadastroRestauranteService.salvar(restauranteAtual));
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
//	@PatchMapping ("/{restauranteId}")
//	public RestauranteDTO atualizarParcial (@PathVariable Long restauranteId, 
//			@RequestBody @Valid Map<String, Object> dados, HttpServletRequest request ){
//		
//		try {
//			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
//			
//			merge(dados, restauranteAtual, request);
//			validate(restauranteAtual, "restaurante");
//			
////			return atualizar(restauranteId, restauranteAtual);
//			return restauranteAssembler.toModel(catalogoPrestadorService.salvar(restauranteAtual));
//			
//		} catch (CozinhaNaoEncontradaException e) {
//			throw new NegocioException(e.getMessage(), e);
//		}
//		
//	}
//	
	
//	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
//			HttpServletRequest request) {
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//		
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//			
//			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//			
//			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//				field.setAccessible(true);
//				
//				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//				
//				ReflectionUtils.setField(field, restauranteDestino, novoValor);
//			});
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}

	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){
		
		cadastroRestauranteService.excluir(restauranteId);
	}
	
	//METODOS PRIVADOS
	
	private RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO dto = new RestauranteDTO();
		dto.setId(restaurante.getId());
		dto.setNome(restaurante.getNome());
		dto.setTaxaFrete(restaurante.getTaxaFrete());
		
		CozinhaDTO cozinhaDto = new CozinhaDTO();
		cozinhaDto.setId(restaurante.getCozinha().getId());
		cozinhaDto.setNome(restaurante.getCozinha().getNome());
		
		dto.setCozinha(cozinhaDto);
		return dto;
	}
	
	private List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes){
		
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
	
	private Restaurante toDomainObject(RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinhaId().getId());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
	
	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		validator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}
}