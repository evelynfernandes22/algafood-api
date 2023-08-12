package com.evelyn.algafood.infraestructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.evelyn.algafood.domain.model.Pedido;
import com.evelyn.algafood.domain.repository.filter.PedidoFilter;

public class PedidoSpec {
	
	public static Specification<Pedido> usandoFiltro(PedidoFilter filter){
		return (root, query, builder) -> {
			
			root.fetch("restaurante").fetch("cozinha");
			root.fetch("cliente");
			
			var predicates = new ArrayList<Predicate>();
			
			//adicionar predicates no array
			if(filter.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filter.getClienteId()));
			}
			
			if(filter.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
			}
			
			if(filter.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
			}
			
			if(filter.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
		
	}
	
}
