package org.acme;

import static io.restassured.RestAssured.given;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import unitins.topicos.dto.UsuarioDTO;
import unitins.topicos.dto.UsuarioResponseDTO;
import unitins.topicos.service.UsuarioService;


/*Ocorreram erros ao tentar fazer inserts novos e updates por conta do cpf que não estava sendo setado na classe 
  UsuarioService
*/
@QuarkusTest
class UsuarioResourceTest {
	@Inject
	UsuarioService usuarioService;

	@Test
	public void getAllTest() {

		given().when().get("/usuario").then().statusCode(200);
	}

	@Test
	public void testInsert() {
		UsuarioDTO usuario = new UsuarioDTO("Aderbaldo", "aderbaldo@gmail.com", "123", "098.231.322-45", 1l, 1l);

		given().contentType(ContentType.JSON).body(usuario).when().post("/usuario").then().statusCode(201).body("nome",
				is("Aderbaldo"), "email", is("aderbaldo@gmail.com"), "senha", is("123"), "cpf", is("098.231.322-45"),
				"idEndereco", is(1), "idTelefone", is(1));
	}

	@Test
	public void updateTest() {

		UsuarioDTO usuario = new UsuarioDTO("Aderbaldo", "aderbaldo@gmail.com", "123", "098.231.322-45", 1l, 1l);

		Long id = usuarioService.create(usuario).id();

		UsuarioDTO usuarioUpdate = new UsuarioDTO("Jonas", "jonas@gmail.com", "654", "023.342.312-45", 2l, 2l);

		given().contentType(ContentType.JSON).body(usuarioUpdate).when().put("/usuario/" + id).then().statusCode(200);

		UsuarioResponseDTO usuarioResponse = usuarioService.findById(id);

		assertThat(usuarioResponse.nome(), is("Jonas"));
		assertThat(usuarioResponse.email(), is("jonas@gmail.com"));
		assertThat(usuarioResponse.senha(), is("654"));
		assertThat(usuarioResponse.cpf(), is("023.342.312-45"));
		assertThat(usuarioResponse.idEndereco(), is(2l));
		assertThat(usuarioResponse.idTelefone(), is(2l));

	}

	@Test
	public void deleteTest() {

		UsuarioDTO usuario = new UsuarioDTO("Aderbaldo", "aderbaldo@gmail.com", "123", "098.231.322-45", 1l, 1l);

		Long id = usuarioService.create(usuario).id();

		given().when().delete("/usuario/" + id).then().statusCode(204);

		UsuarioResponseDTO usuarioResponse = null;

		try {

			usuarioResponse = usuarioService.findById(id);
		} catch (Exception e) {

		} finally {
			assertNull(usuarioResponse);
		}
	}

	@Test
	public void countTest() {

		given().when().get("/usuario/count").then().statusCode(200);
	}

	@Test
	public void getByIdTest() {

		UsuarioDTO usuario = new UsuarioDTO("Aderbaldo", "aderbaldo@gmail.com", "123", "098.231.322-45", 1l, 1l);

		Long id = usuarioService.create(usuario).id();


		given().when().get("/usuario/" + id).then().statusCode(200);
	}

	@Test
	public void getByNomeTest() {

		UsuarioDTO usuario = new UsuarioDTO("Aderbaldo", "aderbaldo@gmail.com", "123", "098.231.322-45", 1l, 1l);

		


		String nome = usuarioService.create(usuario).nome();

		given().when().get("/usuario/search/" + nome).then().statusCode(200);
	}

}