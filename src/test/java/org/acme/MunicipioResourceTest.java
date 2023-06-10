package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import unitins.topicos.dto.MunicipioDTO;
import unitins.topicos.dto.MunicipioResponseDTO;
import unitins.topicos.service.MunicipioService;

@QuarkusTest
class MunicipioResourceTest {
	@Inject
	MunicipioService municipioService;
	String tokenadm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6IkltbWFudWVsS2FudCIsImdyb3VwcyI6WyJVc2VyIiwiQWRtaW4iXSwiZXhwIjoxNjg2NDQ2NDI5LCJpYXQiOjE2ODYzNjAwMjksImp0aSI6IjVjZDUzOTcxLWZlOGMtNDNkYy04MDliLTI5MjkzZTI1MjI4NyJ9.RqnoLn83zr2iRAuFh6RbJqdF6cpwYP3d3xpW1vKegCUl3fYo09-armdAcW_GcqVvgAj6a-Wu7oaRduGPvKnR0aHJZFyNheKK5SqzmDiYgxyaAJUFiTchmTY4dAHxN1RTNnFpZV2wW-wvRZg2pqHKKcOP7FZccs8oXjpFxY-MBMbNq8_9bNt8EEYdKss5etOpCkWn6HrKTJOTajQ_yMzhn2mVMzr4dw_Dya7ScPaGU3G1slBg-ignyOvRdbVozNvRq0pct9gbb-_K0JFbMS2FxS9wHkmuCGv3UInZm968DOQKDeqsMayBrDCt1j1n36ZGnMOdcdRPuYC_fGaGFhog_Q";

	@Test
	public void getAllTest() {

		given().header("Authorization", "Bearer " + tokenadm).when().get("/municipios").then().statusCode(200);
	}

	@Test
	public void insertTest() {

		MunicipioDTO municipio = new MunicipioDTO(
				"Miracema do Tocantins",
				1l);

		given().header("Authorization", "Bearer " + tokenadm)
				.contentType(ContentType.JSON)
				.body(municipio)
				.when().post("/municipios")
				.then()
				.statusCode(201)
				.body("id", notNullValue(), "nome", is("Miracema do Tocantins"),
						"estado.get(\"nome\")", is("Tocantins"), "estado.sigla", is("TO"));
	}

	@Test
	public void updateTest() {

		MunicipioDTO municipioDto = new MunicipioDTO(
				"Miracema do Tocantins",
				1l);

		Long id = municipioService.create(municipioDto).id();

		MunicipioDTO municipioUpdate = new MunicipioDTO(
				"Rio Verde",
				2l);

		given().header("Authorization", "Bearer " + tokenadm)
				.contentType(ContentType.JSON)
				.body(municipioUpdate)
				.when().put("/municipios/" + id)
				.then()
				.statusCode(200);

		MunicipioResponseDTO municipioResponse = municipioService.findById(id);

		assertThat(municipioResponse.nome(), is("Rio Verde"));
		assertThat(municipioResponse.estado().get("nome"), is("Goiás"));
		assertThat(municipioResponse.estado().get("sigla"), is("GO"));
	}

	@Test
	public void deleteTest() {

		MunicipioDTO municipio = new MunicipioDTO(
				"Miracema do Tocantins",
				1l);

		Long id = municipioService.create(municipio).id();

		given().header("Authorization", "Bearer " + tokenadm)
				.when().delete("/municipios/" + id)
				.then()
				.statusCode(204);

		MunicipioResponseDTO municipioResponse = null;

		try {

			municipioResponse = municipioService.findById(id);
		} catch (Exception e) {

		} finally {
			assertNull(municipioResponse);
		}
	}

	@Test
	public void countTest() {

		given().header("Authorization", "Bearer " + tokenadm)
				.when().get("/municipios/count")
				.then()
				.statusCode(200);
	}

	@Test
	public void getByIdTest() {

		MunicipioDTO municipio = new MunicipioDTO(
				"Porongatu",
				2l);

		Long id = municipioService.create(municipio).id();

		given().header("Authorization", "Bearer " + tokenadm)
				.when().get("/municipios/" + id)
				.then()
				.statusCode(200);
	}

	@Test
	public void getByNomeTest() {

		MunicipioDTO municipio = new MunicipioDTO(
				"Porongatu",
				2l);

		String nome = municipioService.create(municipio).nome();

		given().header("Authorization", "Bearer " + tokenadm)
				.when().get("/municipios/search/" + nome)
				.then()
				.statusCode(200);
	}

}
