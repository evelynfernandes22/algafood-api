package com.evelyn.algafood.api.DTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter")  // comentado para testar PedidoSpec
@Getter
@Setter
public class PedidoResumoDTO {

	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal ValorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;
//	private String clienteNome;
}
