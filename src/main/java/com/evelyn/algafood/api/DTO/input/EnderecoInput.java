package com.evelyn.algafood.api.DTO.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {
	
	/*
	 * forçar o cliente a colocar o endereço, já que no banco está null e tem alguns 
	 * endereços sem endereço
	 * Adicionar notblank
	 */
	
	@NotBlank
	private String cep;
	@NotBlank
	private String logradouro;
	@NotBlank
	private String  numero;
	
	private String complemento;
	@NotBlank
	private String bairro;
	@Valid
	@NotNull
	private CidadeIdInput cidade;

}
