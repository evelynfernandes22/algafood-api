package com.evelyn.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.evelyn.algafood.core.validation.Groups;
import com.evelyn.algafood.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable=false)
	private String nome;

//	@Multiplo(numero = 5)  // personalizar e criar lógica na anotação, nesse caso, validar só multiplos de algum número
//	@TaxaFrete //personalizando a anotação com composição "compondo a PositiveOrZero nela"
	@NotNull
	@PositiveOrZero
	@Column (name= "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name="cozinha_id", nullable=false)
	private Cozinha cozinha;
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;	
	
//	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
//	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
		joinColumns = @JoinColumn (name = "restaurante_id"),
		inverseJoinColumns = @JoinColumn (name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
}
