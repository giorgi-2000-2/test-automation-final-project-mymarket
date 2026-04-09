package org.example.utils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static io.restassured.RestAssured.given;
import static org.example.utils.ExtentReportManager.getTest;

public class APIManager  {
    public APIManager() {
        RestAssured.baseURI = ConfigReader.get("BASE_URL");
    }

    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        return headers;
    }

    public String uniqueTitle(String base) {
        return base + "-" + UUID.randomUUID().toString();
    }



    public Response get(String endpoint, Map<String, String> headers, int postid) {
        return given()
                .pathParam("id", postid)
                .accept(ContentType.JSON)
                .headers(headers)
                .log().all()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response post(String endpoint, Map<String, String> headers, Object body) {
        return given()
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(body)
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response getParams(String endpoint, Map<String, String> headers,
                                         Map<String, Object> params) {
        return given()
                .headers(headers)
                .queryParams(params)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response put(String endpoint, Map<String, String> headers, int id, Object body) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .headers(headers)
                .body(body)
                .log().all()
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response delete(String endpoint, Map<String, String> headers, int id) {
        return given()
                .pathParam("id", id)
                .headers(headers)
                .log().all()
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }



    public void assertWithLog(int actual, int expected, String description) {
        if (actual == expected) {
            getTest().pass(description + " — წარმატებულია: " + actual);
            Assert.assertEquals(actual, expected);
        } else {
            getTest().fail(description + " — მოსალოდნელი: " + expected + ", მიღებული: " + actual);
            Assert.assertEquals(actual, expected);
        }
    }

    public void assertWithLog(String actual, String expected, String description) {
        if (actual.equals(expected)) {
            getTest().pass(description + " — წარმატებულია: " + actual);
            Assert.assertEquals(actual, expected);
        } else {
            getTest().fail(description + " — მოსალოდნელი: " + expected + ", მიღებული: " + actual);
            Assert.assertEquals(actual, expected);
        }

    }



}