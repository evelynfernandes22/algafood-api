package com.evelyn.algafood.api.DTO.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.evelyn.algafood.core.validation.FileContentType;
import com.evelyn.algafood.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {
	
	
	@NotNull
<<<<<<< HEAD
	@FileSize(max = "20KB")
=======
	@FileSize(max = "500KB")
>>>>>>> 49a66d4d58cb414ef02cca82192b6441f0de3d6b
	@FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
}
