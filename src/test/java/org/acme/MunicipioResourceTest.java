package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import unitins.topicos.dto.MunicipioDTO;
import unitins.topicos.dto.MunicipioResponseDTO;
import unitins.topicos.service.MunicipioService;

@QuarkusTest
class MunicipioResourceTest {
	@Inject
	MunicipioService municipioService;

	@Test
	public void getAllTest() {

		given().when().get("/municipios").then().statusCode(200);
	}

	@Test
	public void testInsert() {
		MunicipioDTO municipio = new MunicipioDTO("Porongatu", 2l);

		given().contentType(ContentType.JSON).body(municipio).when().post("/municipios").then().statusCode(201)
				.body("nome", is("Porongatu"), "idEstado", is(2));
	}

	@Test
	public void updateTest() {

		MunicipioDTO municipioDto = new MunicipioDTO("Porongatu", 2l);

		Long id = municipioService.create(municipioDto).id();

		MunicipioDTO municipioUpdate = new MunicipioDTO("Araguaina", 1l);

		given().contentType(ContentType.JSON).body(municipioUpdate).when().put("/municipios/" + id).then()
				.statusCode(200);

		MunicipioResponseDTO municipioResponse = municipioService.findById(id);

		assertThat(municipioResponse.nome(), is("Araguaina"));
		assertThat(municipioResponse.idEstado(), is(1l));

	}
	
	  @Test
	    public void deleteTest() {

	        MunicipioDTO municipio = new MunicipioDTO(
	            "Porongatu",
	            2l
	        );

	        Long id = municipioService.create(municipio).id();

	        given()
	          .when().delete("/municipios/" + id)
	          .then()
	             .statusCode(204);

	        MunicipioResponseDTO municipioResponse = null;

	        try {
	            
	            municipioResponse =  municipioService.findById(id);
	        } catch (Exception e) {

	        }
	        finally {
	            assertNull(municipioResponse);   
	        }
	    }
	 
	  @Test
	    public void countTest() {

	        given()
	            .when().get("/municipios/count")
	            .then()
	                .statusCode(200);
	    }
	  
	  @Test
	    public void getByIdTest() {

	        MunicipioDTO municipio = new MunicipioDTO(
	            "Porongatu",
	            2l
	        );

	        Long id = municipioService.create(municipio).id();

	        given()
	            .when().get("/municipios/" + id)
	            .then()
	                .statusCode(200);
	    }
	  
	  @Test
	    public void getByNomeTest() {

	        MunicipioDTO municipio = new MunicipioDTO(
	            "Porongatu",
	            2l
	        );

	        String nome = municipioService.create(municipio).nome();

	        given()
	            .when().get("/municipios/search/" + nome)
	            .then()
	                .statusCode(200);
	    }

}
