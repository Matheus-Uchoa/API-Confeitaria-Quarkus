package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import unitins.topicos.dto.AlergenicoDTO;
import unitins.topicos.dto.AlergenicoResponseDTO;
import unitins.topicos.service.AlergenicoService;

@QuarkusTest
class AlergenicosResourceTest {
	@Inject
	AlergenicoService alergenicoService;

	String tokenadm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6IkltbWFudWVsS2FudCIsImdyb3VwcyI6WyJVc2VyIiwiQWRtaW4iXSwiZXhwIjoxNjg2NDQ2NDI5LCJpYXQiOjE2ODYzNjAwMjksImp0aSI6IjVjZDUzOTcxLWZlOGMtNDNkYy04MDliLTI5MjkzZTI1MjI4NyJ9.RqnoLn83zr2iRAuFh6RbJqdF6cpwYP3d3xpW1vKegCUl3fYo09-armdAcW_GcqVvgAj6a-Wu7oaRduGPvKnR0aHJZFyNheKK5SqzmDiYgxyaAJUFiTchmTY4dAHxN1RTNnFpZV2wW-wvRZg2pqHKKcOP7FZccs8oXjpFxY-MBMbNq8_9bNt8EEYdKss5etOpCkWn6HrKTJOTajQ_yMzhn2mVMzr4dw_Dya7ScPaGU3G1slBg-ignyOvRdbVozNvRq0pct9gbb-_K0JFbMS2FxS9wHkmuCGv3UInZm968DOQKDeqsMayBrDCt1j1n36ZGnMOdcdRPuYC_fGaGFhog_Q";

	@Test
	public void getall() {

		given()
				.header("Authorization", "Bearer " + tokenadm)
				.when()
				.get("/alergenicos")
				.then()
				.statusCode(200);
	}

	@Test
	public void testInsert() {
		AlergenicoDTO ingrediente = new AlergenicoDTO("Pimenta");

		given().header("Authorization", "Bearer " + tokenadm)
				.contentType(ContentType.JSON).body(ingrediente).when().post("/alergenicos").then().statusCode(201)
				.body("descricao", is("Pimenta"));
	}

	@Test
	public void updateTest() {

		AlergenicoDTO alergenico = new AlergenicoDTO("Pimenta");
		Long id = alergenicoService.create(alergenico).id();

		AlergenicoDTO alergenicoUpdate = new AlergenicoDTO("Soja");

		given().header("Authorization", "Bearer " + tokenadm)
				.contentType(ContentType.JSON).body(alergenicoUpdate).when().put("/alergenicos/" + id).then()
				.statusCode(200);

		AlergenicoResponseDTO alergenicoResponse = alergenicoService.findById(id);

		assertThat(alergenicoResponse.descricao(), is("Soja"));

	}

	@Test
	public void deleteTest() {

		AlergenicoDTO alergenico = new AlergenicoDTO("Pimenta");
		Long id = alergenicoService.create(alergenico).id();
		given().header("Authorization", "Bearer " + tokenadm)
				.when().delete("/alergenicos/" + id).then().statusCode(204);

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

		given().header("Authorization", "Bearer " + tokenadm)
				.when().get("/alergenicos/count").then().statusCode(200);
	}

	@Test
	public void getByIdTest() {
		AlergenicoDTO alergenico = new AlergenicoDTO("Pimenta");
		Long id = alergenicoService.create(alergenico).id();
		given().header("Authorization", "Bearer " + tokenadm)
				.when().get("/alergenicos/" + id).then().statusCode(200);
	}

	@Test
	public void getByNomeTest() {

		AlergenicoDTO alergenico = new AlergenicoDTO("Pimenta");

		String nome = alergenicoService.create(alergenico).descricao();

		given().header("Authorization", "Bearer " + tokenadm)
				.when().get("/alergenicos/search/" + nome).then().statusCode(200);
	}

}