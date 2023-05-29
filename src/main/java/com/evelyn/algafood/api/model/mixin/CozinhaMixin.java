package com.evelyn.algafood.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.evelyn.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CozinhaMixin {

	//@JsonProperty("titulo")
//	@JsonIgnore
	private String nome;
	
	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();
}
