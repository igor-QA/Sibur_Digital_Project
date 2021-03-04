package tests;

import annotations.Layer;
import api.spec.Request;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@Layer("api")
@Owner("Igor Pavlov")
public class MainApiPageTests {

    @Test
    public void checkEvents() {
        given()
                .spec(Request.spec())
                .get("events")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void checkCategories() {
        given()
                .spec(Request.spec())
                .get("categories")
                .then()
                .log().body()
                .statusCode(200)
                .body("name", notNullValue());
    }

    @Test
    public void checkPages() {
        given()
                .spec(Request.spec())
                .get("pages")
                .then()
                .log().body()
                .statusCode(200)
                .body("media", notNullValue());
    }
}