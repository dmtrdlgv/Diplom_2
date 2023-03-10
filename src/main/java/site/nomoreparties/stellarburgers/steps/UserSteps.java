package site.nomoreparties.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.restapiclient.UserApiClient;

import static org.hamcrest.Matchers.*;

public class UserSteps {

    private final UserApiClient userApiClient = new UserApiClient();

    @Step("Register new user. api/auth/register")
    public Response registerUser(User user) {
        return userApiClient.registerUser(user);
    }

    @Step("Check status code and response body of success user register")
    public void checkStatusCodeAndResponseBodyOfRegisterUser(Response response, int expectedCode, User user) {
        response
                .then()
                .statusCode(expectedCode)
                .and()
                .body(
                        "success", equalTo(true),
                        "user.email",equalTo(user.getEmail()),
                        "user.name", equalTo(user.getName()),
                        "$", hasKey("accessToken"),
                        "$", hasKey("refreshToken")
                );
    }

    @Step("Delete user by token")
    public Response deleteUser(String accessToken) {
        return userApiClient.deleteUser(accessToken);
    }

    @Step("Check response body of success user deleting")
    public void checkResponseBodyOfUserDeleting(Response response) {
        response
                .then()
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"));
    }

    @Step("Step of deleting user and checking success response")
    public void deleteUserAndCheckResponse(String accessToken) {
        checkResponseBodyOfUserDeleting(deleteUser(accessToken));
    }

    @Step("Login user")
    public Response loginUser(User user) {
        return userApiClient.logInUser(user);
    }

    @Step("Check status code and response body of success user login")
    public void checkStatusCodeAndResponseBodyOfUserLogin(Response response, int expectedCode, User user) {
        response
                .then()
                .statusCode(expectedCode)
                .and()
                .body(
                        "success", equalTo(true),
                        "user.email", equalTo(user.getEmail()),
                        "user.name", equalTo(user.getName()),
                        "$", hasKey("accessToken"),
                        "$", hasKey("refreshToken")
                );
    }

    @Step("Logout user ")
    public Response logoutUser(String refreshToken) {
        return userApiClient.logOutUser(refreshToken);
    }

    @Step("Check response of success user logout")
    public void checkSuccessLogoutResponse(Response response) {
        response
                .then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true),
                        "message", equalTo("Successful logout"));
    }

    @Step("Update user info")
    public Response updateUserInfo(String accessToken, User user) {
        return userApiClient.updateUserInfo(accessToken, user);
    }

    @Step("Check response of success update user info")
    public void checkResponseOfUpdateUser(Response response, User user) {
        response
                .then()
                .statusCode(200)
                .body(
                        "success", equalTo(true),
                        "user.email",equalTo(user.getEmail()),
                        "user.name", equalTo(user.getName())
                );
    }

    @Step("Set tokens from response to user")
    public void setTokensFromResponseToUser(Response response, User user) {
        user.setAccessToken(response.body().jsonPath().getString("accessToken"));
        user.setRefreshToken(response.body().jsonPath().getString("refreshToken"));
    }

    //Универсальный шаг проверки статус кода и сообщения об ошибке
    @Step("Check status code and body of error response")
    public void checkStatusCodeAndBodyOfErrorResponse(Response response, int expectedCode, String expectedMessage) {
        response
                .then()
                .statusCode(expectedCode)
                .and()
                .body("success", equalTo(false), "message", equalTo(expectedMessage));
    }
}
