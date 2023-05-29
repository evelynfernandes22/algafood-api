package com.evelyn.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.CozinhaDTO;
import com.evelyn.algafood.api.DTO.RestauranteDTO;
import com.evelyn.algafood.domain.model.Restaurante;

@Component
public class RestauranteDtoAssembler {
	
	public RestauranteDTO toModel(Restaurante restaurante) {
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
	
	public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes){
		
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}

}
