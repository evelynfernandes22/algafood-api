package com.evelyn.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evelyn.algafood.domain.exception.FotoNaoEncontradaException;
import com.evelyn.algafood.domain.model.FotoProduto;
import com.evelyn.algafood.domain.repository.ProdutoRepository;
import com.evelyn.algafood.domain.service.FotoStorageService.NovaFoto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoFotoProdutoService {

	private ProdutoRepository produtoRepository;
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		
		Long restauranteId = foto.getProduto().getRestaurante().getId();
		Long produtoId= foto.getProduto().getId();
		String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.excluir(fotoExistente.get());
		}
		
		foto.setNomeArquivo(novoNomeArquivo); // evita 
		//salvar a imagem antes de armazenar, pois se der problema no banco é feito holback
		foto = produtoRepository.save(foto);
		produtoRepository.flush(); //descarrega o insert
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(dadosArquivo)
				.build();
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}
	
	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(()-> new FotoNaoEncontradaException(restauranteId, produtoId));
	}
}
