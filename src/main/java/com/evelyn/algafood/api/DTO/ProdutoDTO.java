package com.evelyn.algafood.api.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {

	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private boolean ativo;
}
