package org.acme;

import static io.restassured.RestAssured.given;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import unitins.topicos.dto.EstadoDTO;
import unitins.topicos.dto.EstadoResponseDTO;

import unitins.topicos.service.EstadoService;

@QuarkusTest
class EstadoResourceTest {
	@Inject
	EstadoService estadoService;

	@Test
	public void getAllTest() {

		given().when().get("/estados").then().statusCode(200);
	}

	@Test
	public void testInsert() {
		EstadoDTO estado = new EstadoDTO("Tocantins", "TO");

		given().contentType(ContentType.JSON).body(estado).when().post("/estados").then().statusCode(201)
				.body("nome", is("Tocantins"), "sigla", is("TO"));
	}

	@Test
	public void updateTest() {

		EstadoDTO estado = new EstadoDTO("Tocantins", "TO");
		Long id =estadoService.create(estado).id();

		EstadoDTO estadoUpdate = new EstadoDTO("Goiás", "GO");

		given().contentType(ContentType.JSON).body(estadoUpdate).when().put("/estados/" + id).then()
				.statusCode(200);

		EstadoResponseDTO estadoResponse = estadoService.findById(id);

		assertThat(estadoResponse.nome(), is("Goiás"));
		assertThat(estadoResponse.sigla(), is("GO"));

	}
	
	  @Test
	    public void deleteTest() {

		  EstadoDTO estado = new EstadoDTO("Tocantins", "TO");
			Long id =estadoService.create(estado).id();
	        given()
	          .when().delete("/estados/" + id)
	          .then()
	             .statusCode(204);

	       EstadoResponseDTO estadoResponse = null;

	        try {
	            
	            estadoResponse =  estadoService.findById(id);
	        } catch (Exception e) {

	        }
	        finally {
	            assertNull(estadoResponse);   
	        }
	    }
	 
	  @Test
	    public void countTest() {

	        given()
	            .when().get("/estados/count")
	            .then()
	                .statusCode(200);
	    }
	  
	  @Test
	    public void getByIdTest() {

		  EstadoDTO estado = new EstadoDTO("Tocantins", "TO");
			Long id =estadoService.create(estado).id();

	        given()
	            .when().get("/estados/" + id)
	            .then()
	                .statusCode(200);
	    }
	  
	  @Test
	    public void getByNomeTest() {

		  EstadoDTO estado = new EstadoDTO("Tocantins", "TO");
			

	        String nome = estadoService.create(estado).nome();

	        given()
	            .when().get("/estados/search/" + nome)
	            .then()
	                .statusCode(200);
	    }

}