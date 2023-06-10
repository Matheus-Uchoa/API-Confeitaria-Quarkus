package org.acme;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import unitins.topicos.dto.ConfeitariaDTO;

import unitins.topicos.service.ConfeitariaService;

@QuarkusTest
class ConfeitariaResourceTest {
  @Inject
  ConfeitariaService confeitariaService;
  String tokenadm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ1bml0aW5zLWp3dCIsInN1YiI6IkltbWFudWVsS2FudCIsImdyb3VwcyI6WyJVc2VyIiwiQWRtaW4iXSwiZXhwIjoxNjg2NDQ2NDI5LCJpYXQiOjE2ODYzNjAwMjksImp0aSI6IjVjZDUzOTcxLWZlOGMtNDNkYy04MDliLTI5MjkzZTI1MjI4NyJ9.RqnoLn83zr2iRAuFh6RbJqdF6cpwYP3d3xpW1vKegCUl3fYo09-armdAcW_GcqVvgAj6a-Wu7oaRduGPvKnR0aHJZFyNheKK5SqzmDiYgxyaAJUFiTchmTY4dAHxN1RTNnFpZV2wW-wvRZg2pqHKKcOP7FZccs8oXjpFxY-MBMbNq8_9bNt8EEYdKss5etOpCkWn6HrKTJOTajQ_yMzhn2mVMzr4dw_Dya7ScPaGU3G1slBg-ignyOvRdbVozNvRq0pct9gbb-_K0JFbMS2FxS9wHkmuCGv3UInZm968DOQKDeqsMayBrDCt1j1n36ZGnMOdcdRPuYC_fGaGFhog_Q";

  @Test
  public void getAllTest() {

    given().header("Authorization", "Bearer " + tokenadm).when().get("/confeitaria").then().statusCode(200);
  }

  @Test
  public void countTest() {

    given().header("Authorization", "Bearer " + tokenadm)
        .when().get("/confeitaria/count")
        .then()
        .statusCode(200);
  }

  @Test
  public void getByIdTest() {

    ConfeitariaDTO produto = new ConfeitariaDTO("nometeste", "descricaoteste", 13.2d, 10, 1l, 12.3d, 1);

    Long id = confeitariaService.create(produto).id();

    given().header("Authorization", "Bearer " + tokenadm)
        .when().get("/confeitaria/" + id)
        .then()
        .statusCode(200);
  }

  @Test
  public void getByNomeTest() {

    ConfeitariaDTO produto = new ConfeitariaDTO("nometeste", "descricaoteste", 13.2d, 10, 1l, 12.3d, 1);

    String nome = confeitariaService.create(produto).nome();

    given().header("Authorization", "Bearer " + tokenadm)
        .when().get("/confeitaria/search/" + nome)
        .then()
        .statusCode(200);
  }

}