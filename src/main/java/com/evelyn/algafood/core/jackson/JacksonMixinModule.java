package com.evelyn.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.model.mixin.CidadeMixin;
import com.evelyn.algafood.api.model.mixin.CozinhaMixin;
import com.evelyn.algafood.api.model.mixin.RestauranteMixin;
import com.evelyn.algafood.domain.model.Cidade;
import com.evelyn.algafood.domain.model.Cozinha;
import com.evelyn.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule(){
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}
}
