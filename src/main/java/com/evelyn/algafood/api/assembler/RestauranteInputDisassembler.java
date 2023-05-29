package com.evelyn.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.input.RestauranteInput;
import com.evelyn.algafood.domain.model.Restaurante;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RestauranteInputDisassembler {
	
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}

}
