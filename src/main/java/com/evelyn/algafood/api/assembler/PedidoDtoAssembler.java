package com.evelyn.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.PedidoDTO;
import com.evelyn.algafood.domain.model.Pedido;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PedidoDtoAssembler {

	private ModelMapper modelMapper;
	
	public PedidoDTO toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}
	
	public List<PedidoDTO> toCollectionModel(List<Pedido> pedidos){
		return pedidos.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
