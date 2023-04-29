package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import unitins.topicos.dto.EnderecoDTO;
import unitins.topicos.dto.EnderecoResponseDTO;
import unitins.topicos.service.EnderecoService;


@QuarkusTest
class EnderecoResourceTest {
	@Inject
	EnderecoService enderecoService;

	@Test
	public void getAllTest() {

		given().when().get("/endereco").then().statusCode(200);
	}

	@Test
	public void testInsert() {
		EnderecoDTO endereco = new EnderecoDTO(false, "logradouro", "bairro", "12", "complemento", "77020-432", 1l);

		given().contentType(ContentType.JSON).body(endereco).when().post("/endereco").then().statusCode(201).body(
				"principal", is(false), "logradouro", is("logradouro"), "bairro", is("bairro"), "numero", is("12"),
				"complemento", is("complemento"), "cep", is("77020-432"), "id_municipio", is(1));
	}

	@Test
	public void updateTest() {

		EnderecoDTO endereco = new EnderecoDTO(false, "logradouro", "bairro", "12", "complemento", "77020-432", 1l);

		Long id = enderecoService.create(endereco).id();

		EnderecoDTO enderecoUpdate = new EnderecoDTO(false, "logradouro2", "bairro2", "122", "complemento2", "77020-321", 2l);

		given().contentType(ContentType.JSON).body(enderecoUpdate).when().put("/endereco/" + id).then().statusCode(200);

		EnderecoResponseDTO enderecoResponse = enderecoService.findById(id);

		assertThat(enderecoResponse.principal(), is(false));
		assertThat(enderecoResponse.logradouro(), is("logradouro2"));
		assertThat(enderecoResponse.bairro(), is("bairro2"));
		assertThat(enderecoResponse.numero(), is("122"));
		assertThat(enderecoResponse.complemento(), is("complemento2"));
		assertThat(enderecoResponse.cep(), is("77020-321"));
		assertThat(enderecoResponse.id_municipio(), is(2l));

	}

	@Test
	public void deleteTest() {

		EnderecoDTO endereco = new EnderecoDTO(false, "logradouro", "bairro", "12", "complemento", "77020-432", 1l);

		Long id = enderecoService.create(endereco).id();

		given().when().delete("/endereco/" + id).then().statusCode(204);

		EnderecoResponseDTO enderecoResponse = null;

		try {

			enderecoResponse = enderecoService.findById(id);
		} catch (Exception e) {

		} finally {
			assertNull(enderecoResponse);
		}
	}

	@Test
	public void countTest() {

		given().when().get("/endereco/count").then().statusCode(200);
	}

	@Test
	public void getByIdTest() {

		EnderecoDTO endereco = new EnderecoDTO(false, "logradouro", "bairro", "12", "complemento", "77020-432", 1l);

		Long id = enderecoService.create(endereco).id();

		given().when().get("/endereco/" + id).then().statusCode(200);
	}

	@Test
	public void getByNomeTest() {

		EnderecoDTO endereco = new EnderecoDTO(false, "logradouro", "bairro", "12", "complemento", "77020-432", 1l);

		

		String nome = enderecoService.create(endereco).cep();

		given().when().get("/endereco/search/" + nome).then().statusCode(200);
	}

}