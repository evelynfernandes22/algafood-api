package com.evelyn.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.evelyn.algafood.domain.model.Cozinha;
import com.evelyn.algafood.domain.repository.CozinhaRepository;
import com.evelyn.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //para subir a aplicação
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaTestApi {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

//	PREPARAÇÃO PARA OS TESTES
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); //mostra onde falha
		RestAssured.port = port;	//porta que é carregada no spring
		RestAssured.basePath = "/cozinhas"; 	//o que está mapeado
		
		databaseCleaner.clearTables(); //limpar as tabelas do BD
		prepararDados();	//inserir a massa de dados
	}

//	TESTES
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()	
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConter2Cozinhas_QuandoConsultarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()	
			.get()
		.then()
			.body("", hasSize(2));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
			.body("{\"nome\": \"Americana\"}")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	

	private void prepararDados() {
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Italiana");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha1.setNome("Brasileira");
		cozinhaRepository.save(cozinha2);
	}
}
