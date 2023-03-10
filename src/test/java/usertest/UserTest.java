package usertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.steps.UserSteps;

public class UserTest {

    private User baseUser;
    private User secondUser;
    private Response response;
    private final UserSteps userSteps = new UserSteps();

    @Before
    public void init() {

        //Основной объект пользователя со всеми данными
        baseUser = new User();
        baseUser.fillRandomUserData();

        //Копия основного пользователя для сериализации логина и доп. действий
        secondUser = new User();
        secondUser.setEmail(baseUser.getEmail());
        secondUser.setPassword(baseUser.getPassword());
    }

    @Test
    @DisplayName("Base test success register user")
    public void registerUser_WithCorrectData_ExpectedOK(){
        response = userSteps.registerUser(baseUser);
        userSteps.setTokensFromResponseToUser(response, baseUser);
        userSteps.checkStatusCodeAndResponseBodyOfRegisterUser(response, 200, baseUser);
    }

    @Test
    @DisplayName("Base test success user login")
    public void loginUser_WithCorrectData_ExpectedOK() {
        userSteps.setTokensFromResponseToUser(userSteps.registerUser(baseUser), baseUser);
        response = userSteps.loginUser(secondUser);
        userSteps.checkStatusCodeAndResponseBodyOfUserLogin(response, 200, baseUser);
    }

    @Test
    @DisplayName("Negative test register duplicate user")
    public void registerUser_DuplicateUser_ExpectedError() {
        userSteps.setTokensFromResponseToUser(userSteps.registerUser(baseUser), baseUser);
        //Используем копию основного юзера чтобы в сериализации не участвовали токены
        secondUser.setName(baseUser.getName());
        response = userSteps.registerUser(secondUser);
        userSteps.checkStatusCodeAndBodyOfErrorResponse(response, 403, "User already exists");
    }

    @Test
    @DisplayName("Negative test edit user email to an already exist one")
    public void editUser_ToAlreadyExistEmail_ExpectedError() {
        String secondUserToken;
        userSteps.setTokensFromResponseToUser(userSteps.registerUser(baseUser), baseUser);
        secondUser.fillRandomUserData();
        response = userSteps.registerUser(secondUser);
        secondUserToken = response.path("accessToken").toString();
        secondUser.setPassword(null);
        secondUser.setName(null);
        response = userSteps.updateUserInfo(baseUser.getAccessToken(), secondUser);
        userSteps.checkStatusCodeAndBodyOfErrorResponse(response, 403, "User with such email already exists");
        userSteps.deleteUser(secondUserToken);
    }

    @After
    public void tearDown(){
        if (baseUser.getAccessToken() != null) {
            userSteps.deleteUserAndCheckResponse(baseUser.getAccessToken());
        }
    }
}
