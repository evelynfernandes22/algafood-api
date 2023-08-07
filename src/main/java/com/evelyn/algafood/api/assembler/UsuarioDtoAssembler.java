package com.evelyn.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.evelyn.algafood.api.DTO.UsuarioDTO;
import com.evelyn.algafood.domain.model.Usuario;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UsuarioDtoAssembler {

	private ModelMapper modelMapper;
	
	public UsuarioDTO toModel (Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectionModel(Collection<Usuario> usuarios){
		return usuarios.stream()
				.map(usuario -> toModel(usuario))
				.collect(Collectors.toList());
	}
	
}
