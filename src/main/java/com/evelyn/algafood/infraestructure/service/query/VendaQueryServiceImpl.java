package com.evelyn.algafood.infraestructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.evelyn.algafood.domain.filter.VendaDiariaFilter;
import com.evelyn.algafood.domain.model.Pedido;
import com.evelyn.algafood.domain.model.StatusPedido;
import com.evelyn.algafood.domain.model.aggregates.VendaDiaria;
import com.evelyn.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService{

	@PersistenceContext
	private EntityManager manager; //responsável por interagir com o BD.
	
	@Override
	public List<VendaDiaria> consultarVendasDiariasFilter(VendaDiariaFilter filtro, String timeOffset) {
		
		var builder = manager.getCriteriaBuilder(); //cria a consulta dinâmica
		var query = builder.createQuery(VendaDiaria.class); // query =  a classe de resposta
		var root = query.from(Pedido.class); //root = corresponde a entidade usada na cláusula from, no caso Pedido
		
		var predicates = new ArrayList<Predicate>();
		
		var functionConvertTzDataCriacao = builder.function(
				"convert_tz",
				Date.class,
				root.get("dataCriacao"),
				builder.literal("+00:00"),builder.literal(timeOffset));
		
		var functionDateDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);
		
		var selection = builder.construct(VendaDiaria.class, 
					functionDateDataCriacao, 
					builder.count(root.get("id")),
					builder.sum(root.get("valorTotal")));
		
		if(filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}
		if(filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
		}
		if(filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
		}
		
		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		query.select(selection);
		query.groupBy(functionDateDataCriacao);
		query.where(predicates.toArray(new Predicate[0]));
		
		return manager.createQuery(query).getResultList();
	}

}
