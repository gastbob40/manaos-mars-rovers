package com.gastbob40;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.val;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RoverResourceTest {
    private ValidatableResponse getRequest(String body) {
        var req = given()
                .when()
                .contentType(ContentType.TEXT)
                .accept(ContentType.TEXT);

        if (body != null) {
            req = req.body(body);
        }

        return req.post("/rovers/simulate")
                  .then();
    }

    @Test
    public void testNullBody() {
        getRequest(null)
                .statusCode(400);
    }

    @Test
    public void testEmptyBody() {
        getRequest("")
                .statusCode(400);
    }

    @Test
    public void testSubjectExample() {
        val request = """
                5 5
                1 2 N
                LMLMLMLMM
                3 3 E
                MMRMMRMRRM
                """;

        val expected = """
                1 3 N
                5 1 E
                """;

        getRequest(request)
                .statusCode(200)
                .body(is(expected));
    }

}