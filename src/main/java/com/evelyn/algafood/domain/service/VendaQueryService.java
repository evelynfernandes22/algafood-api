package com.evelyn.algafood.domain.service;

import java.util.List;

import com.evelyn.algafood.domain.filter.VendaDiariaFilter;
import com.evelyn.algafood.domain.model.aggregates.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiariasFilter(VendaDiariaFilter filtro, String timeOffset);
}
