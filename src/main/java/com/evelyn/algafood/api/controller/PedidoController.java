package com.evelyn.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.algafood.api.DTO.PedidoDTO;
import com.evelyn.algafood.api.DTO.PedidoResumoDTO;
import com.evelyn.algafood.api.DTO.input.PedidoInput;
import com.evelyn.algafood.api.assembler.PedidoDtoAssembler;
import com.evelyn.algafood.api.assembler.PedidoInputDisassembler;
import com.evelyn.algafood.api.assembler.PedidoResumoDtoAssembler;
import com.evelyn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.evelyn.algafood.domain.exception.NegocioException;
import com.evelyn.algafood.domain.model.Pedido;
import com.evelyn.algafood.domain.model.Usuario;
import com.evelyn.algafood.domain.repository.PedidoRepository;
import com.evelyn.algafood.domain.service.GeracaoPedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	private PedidoRepository pedidoRepository;
	private GeracaoPedidoService geracaoPedidoService;
	private PedidoResumoDtoAssembler pedidoResumoDtoAssembler;
	private PedidoDtoAssembler pedidoDtoAssembler;
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@GetMapping
	public MappingJacksonValue listar(@RequestParam(required=false) String campos){
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoResumoDTO> pedidosDto = pedidoResumoDtoAssembler.toCollectionModel(pedidos);
		
		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDto);
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
		
		if(StringUtils.isNotBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}
		
		pedidosWrapper.setFilters(filterProvider);
		
		return pedidosWrapper;
	}
	
	
//	@GetMapping
//	public List<PedidoResumoDTO> listar(){
//		return pedidoResumoDtoAssembler.toCollectionModel(pedidoRepository.findAll());
//	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		
		Pedido pedido = geracaoPedidoService.buscarOuFalhar(codigoPedido);
		return pedidoDtoAssembler.toModel(pedido);
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
			
			//TODO pegar usuário autenticado
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L); //cliente fixo de id=1 até implementar a autenticação
			
			novoPedido = geracaoPedidoService.gerar(novoPedido);
			
			return pedidoDtoAssembler.toModel(novoPedido);
			
		} catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
		}
	}
}
