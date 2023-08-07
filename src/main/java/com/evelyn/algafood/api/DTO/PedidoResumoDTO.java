package com.evelyn.algafood.api.DTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.evelyn.algafood.domain.model.Endereco;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoDTO {

	private Long id;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal ValorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;
}
