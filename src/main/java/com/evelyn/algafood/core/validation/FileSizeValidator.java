package com.evelyn.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile>{

	private DataSize maxSize;
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		this.maxSize = DataSize.parse(constraintAnnotation.max()); // convertendo a String em DataSize
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		//Comparar bytes com bytes
		return value == null || value.getSize() <= this.maxSize.toBytes();
	}
}
