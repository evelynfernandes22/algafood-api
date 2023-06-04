package com.evelyn.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.input.FormaPagamentoInput;
import com.evelyn.algafood.domain.model.FormaPagamento;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class FormaPagamentoInputDisassembler {

	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	}
	
	public void copyToDomaiObject(FormaPagamentoInput fPagamentoInput, FormaPagamento formaPagamento) {
		modelMapper.map(fPagamentoInput, formaPagamento);
	}

}
