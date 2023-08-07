package com.evelyn.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.PedidoResumoDTO;
import com.evelyn.algafood.domain.model.Pedido;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PedidoResumoDtoAssembler {

	private ModelMapper modelMapper;
	
	public PedidoResumoDTO toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoDTO.class);
	}
	
	public List<PedidoResumoDTO> toCollectionModel(List<Pedido> pedidos){
		return pedidos.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
