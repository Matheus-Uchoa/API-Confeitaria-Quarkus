package org.acme;

import unitins.topicos.dto.usuario.AuthUsuarioDTO;
import unitins.topicos.service.HashService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@QuarkusTest
public class AuthResourceTest {
    
    @Inject
    HashService hashService;

    @Test
    public void logintest(){
        AuthUsuarioDTO login = new AuthUsuarioDTO("Josue", "123");

        given().contentType(ContentType.JSON)
.body(login)
.when().post("/auth")
.then()
.statusCode(204);

    }

    @Test
    public void cadastrotest(){
        AuthUsuarioDTO login = new AuthUsuarioDTO("Josue", "123");

        given().contentType(ContentType.JSON)
.body(login)
.when().post("/auth/cadastro")
.then()
.statusCode(201);

    }
}
