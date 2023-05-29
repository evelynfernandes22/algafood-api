package com.evelyn.algafood.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	/*
	 * Mostrar para o spring que agora temos uma instância de ModelMapper no seu contexto, 
	 * por isso é necessário ser um Bean, senão não reconhece.
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
