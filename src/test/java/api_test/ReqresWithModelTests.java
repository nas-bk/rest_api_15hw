package api_test;

import io.restassured.RestAssured;
import models.create.CreateUserBodyModel;
import models.create.CreateUserResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.RegistrationResponseModel;
import models.single.SingleResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ReqresSpec.*;

@Tag("api")
public class ReqresWithModelTests {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void successfulCreateUserTest() {
        CreateUserBodyModel data = new CreateUserBodyModel();
        data.setName("morpheus");
        data.setJob("leader");

        CreateUserResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(data)

                        .when()
                        .post("/users")

                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Check response", () ->
                assertEquals("morpheus", response.getName()));
    }

    @Test
    void successfulRegistration() {
        RegistrationBodyModel regData = new RegistrationBodyModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(regData)

                        .when()
                        .post("/register")

                        .then()
                        .spec(successfulResponseSpec)
                        .extract().as(RegistrationResponseModel.class));

        step("Check response", () -> {
            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @Test
    void successfulFoundSingleUserWithId2Test() {
        SingleResponseModel response = step("Make request", () ->
                given(requestSpec)

                        .when()
                        .get("/unknown/2")

                        .then()
                        .spec(successfulResponseSpec)
                        .extract().as(SingleResponseModel.class));

        step("Check response", () -> {
            assertEquals("2", response.getData().getId());
            assertEquals("https://reqres.in/#support-heading", response.getSupport().getUrl());
        });
    }
}
