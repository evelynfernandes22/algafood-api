package com.evelyn.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.evelyn.algafood.domain.exception.NegocioException;
import com.evelyn.algafood.domain.exception.PedidoNaoEncontradoException;
import com.evelyn.algafood.domain.model.Cidade;
import com.evelyn.algafood.domain.model.FormaPagamento;
import com.evelyn.algafood.domain.model.Pedido;
import com.evelyn.algafood.domain.model.Produto;
import com.evelyn.algafood.domain.model.Restaurante;
import com.evelyn.algafood.domain.model.Usuario;
import com.evelyn.algafood.domain.repository.PedidoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GeracaoPedidoService {
	
	private PedidoRepository pedidoRepository;
	private CadastroRestauranteService cadastroRestauranteService;
	private CadastroCidadeService cadastroCidadeService;
	private CadastroUsuarioService cadastroUsuarioService;
	private CadastroProdutoService cadastroProdutoService;
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;

	 public Pedido buscarOuFalhar(Long pedidoId) {
	        return pedidoRepository.findById(pedidoId)
	            .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	    }
	 
	 @Transactional
	 public Pedido gerar (Pedido pedido) {
		 
		 validarPedido(pedido);
		 validarItens(pedido);
		 
		 pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		 pedido.calcularValorTotal();
		 
		 return pedidoRepository.save(pedido);
	 }
	 
	 private void validarPedido(Pedido pedido) {
		 Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		 Usuario cliente = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
		 Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		 FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		 
		 pedido.getEnderecoEntrega().setCidade(cidade);
		 pedido.setCliente(cliente);
		 pedido.setRestaurante(restaurante);
		 pedido.setFormaPagamento(formaPagamento);
		 
		 if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			 throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por este restaurante.", 
					 formaPagamento.getDescricao()));
		 }
	 }
	 
	 private void validarItens(Pedido pedido) {
		 pedido.getItens().forEach(item -> {
			 Produto produto = cadastroProdutoService.buscarOuFalhar(
					 pedido.getRestaurante().getId(), item.getProduto().getId());
			 
			 item.setPedido(pedido);
			 item.setProduto(produto);
			 item.setPrecoUnitario(produto.getPreco());
		 });
	 }
}

