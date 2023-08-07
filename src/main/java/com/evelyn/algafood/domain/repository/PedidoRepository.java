package com.evelyn.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.evelyn.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
	/*
	 *	Fazendo um join em Pedido para trazer todas as consultas de uma só vez e evitar
	 *	perda de performance com vários select.
	 */
	
	@Query("FROM Pedido p JOIN FETCH p.cliente JOIN FETCH p.restaurante r JOIN FETCH r.cozinha")
	List<Pedido> findAll();
}
