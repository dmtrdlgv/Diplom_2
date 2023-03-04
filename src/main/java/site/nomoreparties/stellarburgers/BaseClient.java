package site.nomoreparties.stellarburgers;

import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BaseClient {

    private final String JSON = "application/json";
    private static final String BASE_URL = "stellarburgers.nomoreparties.site";

    //config основного клиента
    private final RestAssuredConfig config = RestAssuredConfig.newConfig()
            .sslConfig(new SSLConfig().relaxedHTTPSValidation())
            .redirect(new RedirectConfig().followRedirects(true));

    //универсальный метод Get без параметров и хедеров
    protected Response doGetRequest(String endPoint) {
        return given()
                .log().all()
                .config(config)
                .header("Content-Type", JSON)
                .get(BASE_URL + endPoint);
    }

    //универсальный метод Get с Хедерами но без парамтров
    protected Response doGetRequestWithHeaders(String endPoint, HashMap headers) {
        return given()
                .log().all()
                .config(config)
                .headers(headers)
                .get(BASE_URL + endPoint);
    }

    //универсальный метод Get с параметрами
    protected Response doGetRequestWithParams(String endPoint, HashMap params) {
        return given()
                .log().all()
                .config(config)
                .header("Content-Type", JSON)
                .queryParams(params)
                .get(BASE_URL + endPoint);
    }

    //универсальный метод Post с Сериализацией (body as POJO)
    protected Response doPostRequest(String endPoint, Object body) {
        return given()
                .log().uri()
                .and()
                .log().body()
                .config(config)
                .header("Content-Type", JSON)
                .body(body)
                .post(BASE_URL + endPoint);
    }

    //Универсальный метод Patch
    protected Response doPatchRequest(String endPoint, Object body) {
        return given()
                .log().uri()
                .and()
                .log().body()
                .config(config)
                .header("Content-Type", JSON)
                .body(body)
                .patch(BASE_URL + endPoint);
    }

    //Универсальный метод Delete с хедерами
    protected Response doDeleteRequestWithHeaders(String endPoint, HashMap headers) {
        return given()
                .log().uri()
                .and()
                .log().body()
                .config(config)
                .headers(headers)
                .delete(BASE_URL + endPoint);
    }
}
