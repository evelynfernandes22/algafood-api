package com.evelyn.algafood.api.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoDTO {

	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;

}
