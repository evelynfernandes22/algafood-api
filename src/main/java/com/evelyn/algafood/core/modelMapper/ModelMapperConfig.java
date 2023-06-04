package com.evelyn.algafood.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.evelyn.algafood.api.DTO.EnderecoDTO;
import com.evelyn.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	/*
	 * Mostrar para o spring que agora temos uma instância de ModelMapper no seu contexto, 
	 * por isso é necessário ser um Bean, senão não reconhece.
	 */
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		
		return modelMapper;
	}
	
	
	
	
}
