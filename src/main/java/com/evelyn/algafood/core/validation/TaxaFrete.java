package com.evelyn.algafood.core.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;

/*
 * Customizando uma anotation.
 * No caso, para onde eu colocar TaxaFrete, faça valer a PositiveOrZero, assim facilitando a manutencao,
 * caso tenha muitos lugares que usem esta anotation.
 * Note que ela herdou boa parte do conteúdo da PositiveOrZero se entrar nela.
 */

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE }) //onde pode ser utilizado
@Retention(RUNTIME)	//encontrar essa anotação em tempo de execucao
@Constraint(validatedBy = { }) 	//identifica com uma constraint do spring
@PositiveOrZero	//a regra de negócio que estou colocando para fazer valer a taxa frete.
public @interface TaxaFrete {

	@OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
	String message() default "{TaxaFrete.invalida}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
