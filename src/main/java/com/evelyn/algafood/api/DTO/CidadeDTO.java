package com.evelyn.algafood.api.DTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDTO {

	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoDTO estado; 
}
