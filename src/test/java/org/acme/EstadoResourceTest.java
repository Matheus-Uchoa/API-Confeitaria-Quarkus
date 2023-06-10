package org.acme;

import static io.restassured.RestAssured.given;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;



import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import unitins.topicos.dto.EstadoDTO;
import unitins.topicos.dto.EstadoResponseDTO;

import unitins.topicos.service.EstadoService;

@QuarkusTest
class EstadoResourceTest {
	@Inject
	EstadoService estadoService;
String tokenadm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6IkltbWFudWVsS2FudCIsImdyb3VwcyI6WyJVc2VyIiwiQWRtaW4iXSwiZXhwIjoxNjg2NDQ2NDI5LCJpYXQiOjE2ODYzNjAwMjksImp0aSI6IjVjZDUzOTcxLWZlOGMtNDNkYy04MDliLTI5MjkzZTI1MjI4NyJ9.RqnoLn83zr2iRAuFh6RbJqdF6cpwYP3d3xpW1vKegCUl3fYo09-armdAcW_GcqVvgAj6a-Wu7oaRduGPvKnR0aHJZFyNheKK5SqzmDiYgxyaAJUFiTchmTY4dAHxN1RTNnFpZV2wW-wvRZg2pqHKKcOP7FZccs8oXjpFxY-MBMbNq8_9bNt8EEYdKss5etOpCkWn6HrKTJOTajQ_yMzhn2mVMzr4dw_Dya7ScPaGU3G1slBg-ignyOvRdbVozNvRq0pct9gbb-_K0JFbMS2FxS9wHkmuCGv3UInZm968DOQKDeqsMayBrDCt1j1n36ZGnMOdcdRPuYC_fGaGFhog_Q";
	@Test
	public void getall() {

		given()
				.header("Authorization", "Bearer " + tokenadm)
				.when()
				.get("/estados")
				.then()
				.statusCode(200);
	}

 	@Test
	public void testInsert() {
		EstadoDTO estado = new EstadoDTO("Tocantins", "TO");

		given().header("Authorization", "Bearer " + tokenadm)
				.contentType(ContentType.JSON).body(estado).when().post("/estados").then().statusCode(201)
				.body("nome", is("Tocantins"), "sigla", is("TO"));
	}

	@Test
	public void updateTest() {

		EstadoDTO estado = new EstadoDTO("Tocantins", "TO");
		Long id =estadoService.create(estado).id();

		EstadoDTO estadoUpdate = new EstadoDTO("Goiás", "GO");

		given().header("Authorization", "Bearer " + tokenadm)
				.contentType(ContentType.JSON).body(estadoUpdate).when().put("/estados/" + id).then()
				.statusCode(204);

		EstadoResponseDTO estadoResponse = estadoService.findById(id);

		assertThat(estadoResponse.nome(), is("Goiás"));
		assertThat(estadoResponse.sigla(), is("GO"));

	}
	
	  @Test
	    public void deleteTest() {

		  EstadoDTO estado = new EstadoDTO("Tocantins", "TO");
			Long id =estadoService.create(estado).id();
	       given().header("Authorization", "Bearer " + tokenadm)
				.when().delete("/estados/" + id).then().statusCode(204);

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
	            .header("Authorization", "Bearer " + tokenadm)
				.when().get("/estados/count")
	            .then()
	                .statusCode(200);
	    }
	  
	  @Test
	    public void getByIdTest() {

		  EstadoDTO estado = new EstadoDTO("Tocantins", "TO");
			Long id =estadoService.create(estado).id();

	        given()
	            .header("Authorization", "Bearer " + tokenadm)
				.when().get("/estados/" + id)
	            .then()
	                .statusCode(200);
	    }
	  
	  @Test
	    public void getByNomeTest() {

		  EstadoDTO estado = new EstadoDTO("Tocantins", "TO");
			

	        String nome = estadoService.create(estado).nome();

	        given()
	            .header("Authorization", "Bearer " + tokenadm)
				.when().get("/estados/search/" + nome)
	            .then()
	                .statusCode(200);
	    } 

}