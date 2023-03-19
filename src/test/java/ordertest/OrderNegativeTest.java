package ordertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.*;
import site.nomoreparties.stellarburgers.steps.OrderSteps;
import site.nomoreparties.stellarburgers.steps.UserSteps;

import java.util.ArrayList;

public class OrderNegativeTest {

    private final OrderSteps orderSteps = new OrderSteps();
    private final UserSteps userSteps = new UserSteps();
    private Orders orders;
    private Ingredients ingredients;
    private Ingredient ingredient;
    private IngredientsResponse ingredientsResponse;
    private User user;
    private Response response;

    @Before
    public void init() {
        user = new User();
        user.fillRandomUserData();
        userSteps.setTokensFromResponseToUser(userSteps.registerUser(user), user);
        orders = new Orders();
        ingredient = new Ingredient();
        ingredients = new Ingredients(new ArrayList<>());
        ingredientsResponse = new IngredientsResponse();
        response = orderSteps.getIngredientList();
        ingredientsResponse = response.body().as(IngredientsResponse.class);
    }

    @Test
    @DisplayName("Negative test of creating new order without ingredients and auth")
    public void createNewOrder_WithoutIngredientsAndAuth_ExpectedError() {
        response = orderSteps.createNewOrder(null, ingredients);
        orderSteps.checkStatusCodeAndBodyOfErrorResponse(response, 400, "Ingredient ids must be provided"
        );
    }

    @Test
    @DisplayName("Negative test of creating new order without ingredients and with auth")
    public void createNewOrder_WithoutIngredientsAndWithAuth_ExpectedError() {
        response = orderSteps.createNewOrder(user.getAccessToken(), ingredients);
        orderSteps.checkStatusCodeAndBodyOfErrorResponse(response, 400, "Ingredient ids must be provided"
        );
    }

    @Test
    @DisplayName("Negative test of creating new order with invalid ingredients hash")
    public void createNewOrder_WithInvalidIngredients_ExpectedError() {
        ingredients.addIngredient("invalid_hash");
        response = orderSteps.createNewOrder(null, ingredients);
        orderSteps.checkStatusCode(response, 500);
    }

    @Test
    @DisplayName("Negative test of getting user order list without auth")
    public void getUserOrderList_WithoutAuth_ExpectedError() {
        response = orderSteps.getUserOrderList(null);
        orderSteps.checkStatusCodeAndBodyOfErrorResponse(response, 401, "You should be authorised");
    }

    @After
    public void tearDown() {
        if (user.getAccessToken() != null) {
            userSteps.deleteUser(user.getAccessToken());
        }
    }
}
