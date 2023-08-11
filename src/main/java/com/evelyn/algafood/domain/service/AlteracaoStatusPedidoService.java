package com.evelyn.algafood.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evelyn.algafood.domain.model.Pedido;

@Service
public class AlteracaoStatusPedidoService {

	@Autowired
	private GeracaoPedidoService geracaoPedidoService;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = geracaoPedidoService.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = geracaoPedidoService.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = geracaoPedidoService.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
	}
}
