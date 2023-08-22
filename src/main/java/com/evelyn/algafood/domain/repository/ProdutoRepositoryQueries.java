package com.evelyn.algafood.domain.repository;

import com.evelyn.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto foto);
	void excluir(FotoProduto foto);
}
