package com.evelyn.algafood.infraestructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.evelyn.algafood.domain.model.FotoProduto;
import com.evelyn.algafood.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto) {
		return manager.merge(foto);
	}

	@Override
	public void excluir(FotoProduto foto) {
		manager.remove(foto);
	}
}
