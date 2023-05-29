package com.evelyn.algafood.api.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoDTO {

	@NotNull
	private Long id;
	
	@NotBlank
	private String nome;
}
