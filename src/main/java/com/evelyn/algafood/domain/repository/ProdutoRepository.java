package com.evelyn.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.evelyn.algafood.domain.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	 @Query("from Produto where restaurante.id = :restaurante and id = :produto")
	 Optional<Produto> findById(@Param("restaurante") Long restauranteId,@Param("produto") Long produtoId);
}
