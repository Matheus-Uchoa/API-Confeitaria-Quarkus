package org.acme;

import static io.restassured.RestAssured.given;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import unitins.topicos.dto.IngredienteDTO;
import unitins.topicos.dto.IngredienteResponseDTO;
import unitins.topicos.service.IngredienteService;

/**
 * Ocorreram erros no endpoint de procurar por nome, o caminho para o endpoint estava incorreto
 * foi resolvido na classe IngredienteResource passando o Path corretamente
 */
@QuarkusTest
class IngredienteResourceTest {
	@Inject
	IngredienteService ingredienteService;

	@Test
	public void getAllTest() {

		given().when().get("/ingredientes").then().statusCode(200);
	}

	@Test
	public void testInsert() {
		IngredienteDTO ingrediente = new IngredienteDTO("Banana");

		given().contentType(ContentType.JSON).body(ingrediente).when().post("/ingredientes").then().statusCode(201)
				.body("nome", is("Banana"));
	}

	@Test
	public void updateTest() {

		IngredienteDTO ingrediente = new IngredienteDTO("Banana");
		Long id = ingredienteService.create(ingrediente).id();

		IngredienteDTO telefoneUpdate = new IngredienteDTO("Cereja");

		given().contentType(ContentType.JSON).body(telefoneUpdate).when().put("/ingredientes/" + id).then()
				.statusCode(200);

		IngredienteResponseDTO ingredienteResponse = ingredienteService.findById(id);

		assertThat(ingredienteResponse.nome(), is("Cereja"));

	}

	@Test
	public void deleteTest() {

		IngredienteDTO ingrediente = new IngredienteDTO("Banana");
		Long id = ingredienteService.create(ingrediente).id();
		given().when().delete("/ingredientes/" + id).then().statusCode(204);

		IngredienteResponseDTO ingredienteResponse = null;

		try {

			ingredienteResponse = ingredienteService.findById(id);
		} catch (Exception e) {

		} finally {
			assertNull(ingredienteResponse);
		}
	}

	@Test
	public void countTest() {

		given().when().get("/ingredientes/count").then().statusCode(200);
	}

	@Test
	public void getByIdTest() {
		IngredienteDTO ingrediente = new IngredienteDTO("Banana");
		Long id = ingredienteService.create(ingrediente).id();
		given().when().get("/ingredientes/" + id).then().statusCode(200);
	}

	@Test
	public void getByNomeTest() {

		IngredienteDTO ingrediente = new IngredienteDTO("Banana");

		String nome = ingredienteService.create(ingrediente).nome();

		given().when().get("/ingredientes/search/" + nome).then().statusCode(200);
	}

}
