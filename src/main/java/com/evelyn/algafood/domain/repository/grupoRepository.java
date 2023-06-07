package com.evelyn.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evelyn.algafood.domain.model.Grupo;

@Repository
public interface grupoRepository extends JpaRepository<Grupo, Long> {

	
}
