package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class ReqresSpec {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(JSON);


    public static ResponseSpecification createUserResponseSpec201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();


    public static ResponseSpecification successfulResponseSpec200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();
}
