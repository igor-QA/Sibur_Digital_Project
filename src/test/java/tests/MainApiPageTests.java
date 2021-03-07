package tests;

import annotations.Layer;
import api.spec.Request;
import io.qameta.allure.AllureId;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@Layer("api")
@Owner("Igor Pavlov")
public class MainApiPageTests {

    @Test
    @AllureId("#1821")
    @DisplayName("API запрос на проверку событий")
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
    @AllureId("#1819")
    @DisplayName("API запрос на проверку категорий")
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
    @AllureId("#1820")
    @DisplayName("API запрос на проверку страницы новостей")
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