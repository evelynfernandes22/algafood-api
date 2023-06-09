package com.evelyn.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.input.RestauranteInput;
import com.evelyn.algafood.domain.model.Cidade;
import com.evelyn.algafood.domain.model.Cozinha;
import com.evelyn.algafood.domain.model.Restaurante;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RestauranteInputDisassembler {
	
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return  modelMapper.map(restauranteInput, Restaurante.class);
		
	}
	
//	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
////		return modelMapper.map(restauranteInput, Restaurante.class); //com  modelmapper não está funcionando o método adicionar
//		Restaurante restaurante = new Restaurante();
//		restaurante.setNome(restauranteInput.getNome());
//		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
//		
//		Cozinha cozinha = new Cozinha();
//		cozinha.setId(restauranteInput.getCozinhaId().getId());
//		restaurante.setCozinha(cozinha);
//		
//		return restaurante;
//	}

	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		
		
		/*
		 * Para evitar org.hibernate.HibernateException: identifier of an 
		 * instance of com.evelyn.algafood.domain.model.Cidade was altered from 2 to 1
		 */
		restaurante.setCozinha(new Cozinha()); 
		
		//Para tbm evitar o erro 500 de cima
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante); 
	}
}
