package com.evelyn.algafood.api.DTO.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioIdnput {

	@NotNull
	private Long id;
}
