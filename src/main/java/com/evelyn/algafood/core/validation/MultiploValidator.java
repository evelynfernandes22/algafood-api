package com.evelyn.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number>{

	private int numeroMultiplo;
	/*
	 * Inicializa o validador para chamadas futuras do método isValid.
	 */
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		
		this.numeroMultiplo = constraintAnnotation.numero();	//buscará o númreo especificado no uso da anotação
	}
	
	/*
	 * Implementa a lógica de validação
	 */
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido = true;
		
		if(value != null) {
			BigDecimal valorDecimal = BigDecimal.valueOf(value.doubleValue()); //transformando o value de number para double aceito pelo valueOf
			BigDecimal multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
			BigDecimal resto = valorDecimal.remainder(multiploDecimal); //remainder faz a função do %, divide um número pelo outro.
			
			valido = BigDecimal.ZERO.equals(resto); //é válido se zero em BigDecima for igual o resultado da divisão feita va variável resto. 
		}
		return valido;
	}
}
