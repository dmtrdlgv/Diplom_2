package ordertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.*;
import site.nomoreparties.stellarburgers.steps.OrderSteps;
import site.nomoreparties.stellarburgers.steps.UserSteps;

import java.util.ArrayList;

public class OrderTest {

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
        RestAssured.filters( new ResponseLoggingFilter(LogDetail.BODY));
    }

    @Test
    @DisplayName("Base positive test of creating new Order with ingredients but without authorization")
    public void createNewOrder_WithIngredientsAndWithoutAuth_ExpectedOk() {
        //записываем нужное колич ингредиентов для будущего заказа
        for (int i = 0; i < 3; i++) ingredients.addIngredient(ingredientsResponse.getData().get(i).get_id());
        response = orderSteps.createNewOrder(null, ingredients);
        orderSteps.checkResponseOfSuccessCreatingNewOrderWithoutAuth(response);
    }

    @Test
    @DisplayName("Base positive test of creating new Order with ingredients and authorization")
    public void createNewOrder_WithIngredientsAndAuth_ExpectedOk() {
        //записываем нужное колич ингредиентов для будущего заказа
        for (int i = 0; i < 5; i++) ingredients.addIngredient(ingredientsResponse.getData().get(i).get_id());
        response = orderSteps.createNewOrder(user.getAccessToken(), ingredients);
        orderSteps.checkResponseOfSuccessCreatingNewOrderWithAuth(response, user);
    }



    @After
    public void tearDown() {
        if (user.getAccessToken() != null) {
            userSteps.deleteUser(user.getAccessToken());
        }
    }
}
