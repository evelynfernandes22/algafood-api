package com.evelyn.algafood.api.DTO.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdInput {

	@NotNull
	private Long id;
}
