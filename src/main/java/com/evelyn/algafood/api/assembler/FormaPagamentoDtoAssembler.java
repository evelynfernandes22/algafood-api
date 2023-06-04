package com.evelyn.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.FormaPagamentoDTO;
import com.evelyn.algafood.domain.model.FormaPagamento;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class FormaPagamentoDtoAssembler {

	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> formasPagamento){
		return formasPagamento.stream()
				.map(formaPagamento -> toModel(formaPagamento))
				.collect(Collectors.toList());
	}
}
