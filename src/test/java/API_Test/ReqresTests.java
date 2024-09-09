package API_Test;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";
    }

    @Test
    void successfulGetListUsers() {
        given()
                .log().uri()

                .when()
                .get("?page=2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12))
                .body("data[1].first_name", is("Lindsay"))
                .body("data[1].last_name", is("Ferguson"))
                .body("data[1].email", is("lindsay.ferguson@reqres.in"))
                .body("data[1].id", is(8));

    }

    @Test
    void successfulCreateUserTest() {
        String authData = "{\"name\": \"morpheus\",\"job\": \"leader\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post()

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    @Test
    void successfulFoundSingleUserWithId2Test() {
        given()
                .log().uri()

                .when()
                .get("/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }

    @Test
    void unsuccessfulFoundSingleUserWithId23Test() {
        given()
                .log().uri()

                .when()
                .get("/23")

                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }


    @Test()
    void successfulDeleteUserTest() {
        given()
                .log().uri()

                .when()
                .delete("/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }
}
