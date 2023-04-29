package org.acme;

import static io.restassured.RestAssured.given;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import unitins.topicos.dto.TelefoneDTO;
import unitins.topicos.dto.TelefoneResponseDTO;
import unitins.topicos.service.TelefoneService;


/**
 * Ocorreram erros no endpoint de procurar por número, pois antes estava programado para filtrar a coluna "nome"
 * coluna essa que não existe em Telefone, portanto foi necessário atualizar o repository, o service e o resource
 * adaptando essa situação
 */
@QuarkusTest
class TelefoneResourceTest {
	@Inject
	TelefoneService telefoneService;

	@Test
	public void getAllTest() {

		given().when().get("/telefone").then().statusCode(200);
	}

	@Test
	public void testInsert() {
		TelefoneDTO telefone = new TelefoneDTO("43","984312345");

		given().contentType(ContentType.JSON).body(telefone).when().post("/telefone").then().statusCode(201)
				.body("codigoArea", is("43"), "numero", is("984312345"));
	}

	@Test
	public void updateTest() {

		TelefoneDTO telefone = new TelefoneDTO("43","984312345");
		Long id =telefoneService.create(telefone).id();

		TelefoneDTO telefoneUpdate = new TelefoneDTO("63", "985173723");

		given().contentType(ContentType.JSON).body(telefoneUpdate).when().put("/telefone/" + id).then()
				.statusCode(200);

		TelefoneResponseDTO telefoneResponse = telefoneService.findById(id);

		assertThat(telefoneResponse.codigoArea(), is("63"));
		assertThat(telefoneResponse.numero(), is("985173723"));

	}
	
	  @Test
	    public void deleteTest() {

		  TelefoneDTO telefone = new TelefoneDTO("43","984312345");
			Long id =telefoneService.create(telefone).id();
	        given()
	          .when().delete("/telefone/" + id)
	          .then()
	             .statusCode(204);

	      TelefoneResponseDTO telefoneResponse = null;

	        try {
	            
	            telefoneResponse =  telefoneService.findById(id);
	        } catch (Exception e) {

	        }
	        finally {
	            assertNull(telefoneResponse);   
	        }
	    }
	 
	  @Test
	    public void countTest() {

	        given()
	            .when().get("/telefone/count")
	            .then()
	                .statusCode(200);
	    }
	  
	  @Test
	    public void getByIdTest() {
		  TelefoneDTO telefone = new TelefoneDTO("43","984312345");
			Long id =telefoneService.create(telefone).id();
	        given()
	            .when().get("/telefone/" + id)
	            .then()
	                .statusCode(200);
	    }
	  
	  @Test
	    public void getByNumeroTest() {

		  TelefoneDTO telefone = new TelefoneDTO("43","984312345");
		
			

	        String numero = telefoneService.create(telefone).numero();

	        given()
	            .when().get("/telefone/search/" + numero)
	            .then()
	                .statusCode(200);
	    }

}