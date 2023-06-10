package org.acme;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import unitins.topicos.repository.CompraRepository;
import unitins.topicos.service.TokenJwtService;
import unitins.topicos.service.UsuarioService;

import static io.restassured.RestAssured.given;

import jakarta.inject.Inject;


@QuarkusTest
public class CompraResourceTest{
    
    @Inject
    TokenJwtService jwt;

    @Inject
    UsuarioService usuarioService;

    @Inject
    CompraRepository compraRepository;

    @Test
    void testGetAll() {
        given()
        .when().get("/compras")
        .then()
        .statusCode(200);
    }

    

  
}
