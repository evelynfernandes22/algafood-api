package com.evelyn.algafood.api.DTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {

	private Long id;
	
	@NotBlank
	private String nome;

	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaDTO cozinha;
	
	@NotNull
	private OffsetDateTime dataCadastro;
	
	@NotNull
	private OffsetDateTime dataAtualizacao;
	

}
