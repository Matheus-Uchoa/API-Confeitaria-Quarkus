package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import unitins.topicos.dto.AlergenicoDTO;
import unitins.topicos.dto.AlergenicoResponseDTO;
import unitins.topicos.service.AlergenicoService;


@QuarkusTest
class AlergenicosResourceTest {
	@Inject
	AlergenicoService alergenicoService;

	@Test
	public void getAllTest() {

		given().when().get("/alergenico").then().statusCode(200);
	}

	@Test
	public void testInsert() {
		AlergenicoDTO ingrediente = new AlergenicoDTO("Pimenta");

		given().contentType(ContentType.JSON).body(ingrediente).when().post("/alergenico").then().statusCode(201)
				.body("descricao", is("Pimenta"));
	}

	@Test
	public void updateTest() {

		AlergenicoDTO alergenico = new AlergenicoDTO("Pimenta");
		Long id = alergenicoService.create(alergenico).id();

		AlergenicoDTO alergenicoUpdate = new AlergenicoDTO("Soja");

		given().contentType(ContentType.JSON).body(alergenicoUpdate).when().put("/alergenico/" + id).then()
				.statusCode(200);

		AlergenicoResponseDTO alergenicoResponse = alergenicoService.findById(id);

		assertThat(alergenicoResponse.descricao(), is("Soja"));

	}

	@Test
	public void deleteTest() {

		AlergenicoDTO alergenico = new AlergenicoDTO("Pimenta");
		Long id = alergenicoService.create(alergenico).id();
		given().when().delete("/alergenico/" + id).then().statusCode(204);

		AlergenicoResponseDTO alergenicoResponse = null;

		try {

			alergenicoResponse = alergenicoService.findById(id);
		} catch (Exception e) {

		} finally {
			assertNull(alergenicoResponse);
		}
	}

	@Test
	public void countTest() {

		given().when().get("/alergenico/count").then().statusCode(200);
	}

	@Test
	public void getByIdTest() {
		AlergenicoDTO alergenico = new AlergenicoDTO("Pimenta");
		Long id = alergenicoService.create(alergenico).id();
		given().when().get("/alergenico/" + id).then().statusCode(200);
	}

	@Test
	public void getByNomeTest() {

		AlergenicoDTO alergenico = new AlergenicoDTO("Pimenta");
		

		String nome = alergenicoService.create(alergenico).descricao();

		given().when().get("/ingredientes/search/" + nome).then().statusCode(200);
	}

}