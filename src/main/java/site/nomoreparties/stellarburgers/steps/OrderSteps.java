package site.nomoreparties.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import site.nomoreparties.stellarburgers.model.Ingredients;
import site.nomoreparties.stellarburgers.model.Order;
import site.nomoreparties.stellarburgers.model.Orders;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.restapiclient.OrderApiClient;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;

public class OrderSteps {

    private final OrderApiClient orderApiClient = new OrderApiClient();
    private final SoftAssertions softly = new SoftAssertions();
    private Orders orders;

    @Step("Getting ingredient list GET api/ingredients")
    public Response getIngredientList() {
        return orderApiClient.getIngredientList();
    }

    @Step("Getting user order list GET api/orders")
    public Response getUserOrderList(String accessToken) {
        return orderApiClient.getUserOrderList(accessToken);
    }

    @Step("Getting all orders list GET api/orders/all")
    public Response getAllOrders() {
        return orderApiClient.getAllOrdersList();
    }

    @Step("Creating new order POST api/orders")
    public Response createNewOrder(String accessToken, Ingredients ingredients) {
        return orderApiClient.createNewOrder(accessToken, ingredients);
    }

    @Step("Checking status code and response of success creating new order without auth")
    public void checkResponseOfSuccessCreatingNewOrderWithoutAuth(Response response) {
        response
                .then()
                .statusCode(200)
                .and()
                .body(
                        "success", equalTo(true),
                        "$", hasKey("name"),
                        "order.number", notNullValue()
                );
    }

    @Step("Checking status code and response of success creating new order with auth")
    public void checkResponseOfSuccessCreatingNewOrderWithAuth(Response response, User creatingUser) {
        response
                .then()
                .statusCode(200)
                .and()
                .body(
                        "success", equalTo(true),
                        "order", notNullValue(),
                        "order", hasKey("ingredients"),
                        "order.owner.name", equalTo(creatingUser.getName()),
                        "order.owner.email", equalTo(creatingUser.getEmail()),
                        "order._id", notNullValue(),
                        "order.status", notNullValue(),
                        "order.number", notNullValue(),
                        "order.price", notNullValue(),
                        "order.createdAt", notNullValue(),
                        "order.updatedAt", notNullValue()
                );
    }

    @Step("Checking response and orders of user order list")
    public void checkResponseOfUserOrderList(Response response, ArrayList userOrdersId) {
        orders = new Orders();
        orders = response.body().as(Orders.class);
        softly.assertThat(orders.isSuccess()).as("Success is not match").isTrue();
        //проверка соответствия созданных пользователем заказов и списка заказов
        for (Order order : orders.getOrders()) {
            softly.assertThat(userOrdersId.contains(order.getId())).as("Order id is not match").isTrue();
        }
        softly.assertThat(orders.getTotalToday()).as("No match getTotalToday").isEqualTo(userOrdersId.size());
        softly.assertThat(orders.getTotal()).as("No match getTotal").isEqualTo(userOrdersId.size());
        softly.assertAll();
    }

    //Универсальный шаг проверки статус кода и сообщения об ошибке
    @Step("Check status code and body of error response")
    public void checkStatusCodeAndBodyOfErrorResponse(Response response, int expectedCode, String expectedMessage) {
        response
                .then()
                .statusCode(expectedCode)
                .body("success", equalTo(false), "message", equalTo(expectedMessage));
    }

    //Универсальный шаг проверки отдельно статус кода
    @Step("Check status code")
    public void checkStatusCode(Response response, int expectedCode) {
        response
                .then()
                .statusCode(expectedCode);

    }
}
