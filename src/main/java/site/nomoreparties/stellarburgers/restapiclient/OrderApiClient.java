package site.nomoreparties.stellarburgers.restapiclient;

import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.model.Ingredients;

import java.util.HashMap;

public class OrderApiClient extends BaseApiClient {

    private final String GET_INGREDIENTS_ENDPOINT = "/api/ingredients";
    private final String ORDERS_ENDPOINT = "/api/orders";
    private final String GET_ALL_ORDERS = "/api/orders/all";
    private HashMap<String, String> headers;

    public Response getIngredientList() {
        return doGetRequest(GET_INGREDIENTS_ENDPOINT);
    }

    public Response getUserOrderList(String accessToken) {
        headers = new HashMap<>();
        headers.put("Authorization", accessToken);
        return doGetRequestWithHeaders(ORDERS_ENDPOINT, headers);
    }

    public Response getAllOrdersList() {
        return doGetRequest(GET_ALL_ORDERS);
    }

    public Response createNewOrder(String accessToken, Ingredients ingredients) {
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", accessToken);
        return doPostRequestWithHeaders(ORDERS_ENDPOINT, headers, ingredients);
    }
}
