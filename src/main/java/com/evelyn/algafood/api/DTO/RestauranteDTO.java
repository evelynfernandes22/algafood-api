package com.evelyn.algafood.api.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {

	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDTO cozinha;
	private Boolean ativo;
	private EnderecoDTO endereco;
	
//	private OffsetDateTime dataCadastro;
//	private OffsetDateTime dataAtualizacao;
	

}
