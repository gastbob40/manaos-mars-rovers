package com.gastbob40;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class RoverResourceTest {
    private ValidatableResponse getRequest() {
        return given()
                .when()
                .contentType(ContentType.TEXT)
                .accept(ContentType.TEXT)
                .post("/rovers/simulate")
                .then();
    }

    @Test
    public void testNullBody() {
        getRequest()
                .statusCode(400);
    }

}