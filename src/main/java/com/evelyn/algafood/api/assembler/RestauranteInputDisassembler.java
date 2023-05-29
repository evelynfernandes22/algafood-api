package com.evelyn.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.input.RestauranteInput;
import com.evelyn.algafood.domain.model.Cozinha;
import com.evelyn.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinhaId().getId());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}

}
