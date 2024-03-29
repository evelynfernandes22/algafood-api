package com.evelyn.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.algafood.api.DTO.RestauranteDTO;
import com.evelyn.algafood.api.DTO.input.RestauranteInput;
import com.evelyn.algafood.api.assembler.RestauranteDtoAssembler;
import com.evelyn.algafood.api.assembler.RestauranteInputDisassembler;
import com.evelyn.algafood.core.validation.ValidacaoException;
import com.evelyn.algafood.domain.exception.CidadeNaoEncontradaException;
import com.evelyn.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.evelyn.algafood.domain.exception.EstadoNaoEncontradoException;
import com.evelyn.algafood.domain.exception.NegocioException;
import com.evelyn.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.evelyn.algafood.domain.model.Restaurante;
import com.evelyn.algafood.domain.model.view.RestauranteView;
import com.evelyn.algafood.domain.repository.RestauranteRepository;
import com.evelyn.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	private RestauranteRepository restauranteRepository;
	private CadastroRestauranteService cadastroRestauranteService;
	private SmartValidator validator;
	private RestauranteDtoAssembler restauranteDtoAssembler;
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteDTO> listar(){
		return restauranteDtoAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteDTO> listarApenasNomes(){
		return listar();
	}

//DINAMICAMENTE
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required=false) String projecao){
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteDTO> restauranteDto =  restauranteDtoAssembler.toCollectionModel(restaurantes);
//		
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restauranteDto);
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//		
//		if("apenas-nome".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		}else if("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//		
//		return restaurantesWrapper;
//	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable ("restauranteId") Long restauranteId){
		
		return restauranteDtoAssembler.toModel(cadastroRestauranteService.buscarOuFalhar(restauranteId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			return  restauranteDtoAssembler.toModel(cadastroRestauranteService.salvar(restaurante));

		} catch (EstadoNaoEncontradoException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,@RequestBody @Valid RestauranteInput restauranteInput){
		
		try {
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			return restauranteDtoAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
			
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
	}
	
	// PUT/restaurantes/ativacoes
	//	[1,3,4]
	
	// DELETE/restaurantes/ativacoes
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestauranteService.ativar(restaurantesIds);
			
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestauranteService.inativar(restaurantesIds);
			
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abertura(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrir(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fechar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId){
		
		cadastroRestauranteService.excluir(restauranteId);
	}
	
	//METODOS PRIVADOS
	
	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		validator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}
	
//	ATUALIZAR PARCIAL
	
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
//			return restauranteDtoAssembler.toModel(catalogoPrestadorService.salvar(restauranteAtual));
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

}