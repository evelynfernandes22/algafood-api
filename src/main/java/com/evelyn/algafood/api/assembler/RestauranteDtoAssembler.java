package com.evelyn.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.RestauranteDTO;
import com.evelyn.algafood.domain.model.Restaurante;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RestauranteDtoAssembler {
	
	private ModelMapper modelMapper; 
	
	public RestauranteDTO toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}
	
	public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes){
		
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}

}
