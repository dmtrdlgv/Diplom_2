package site.nomoreparties.stellarburgers.restapiclient;

import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.model.User;

import java.util.HashMap;

public class UserApiClient extends BaseApiClient{

    private final String USER_REGISTER_ENDPOINT = "/api/auth/register";
    private final String USER_LOGIN_ENDPOINT = "/api/auth/login";
    private final String USER_LOGOUT_ENDPOINT = "/api/auth/logout";
    private final String USER_TOKEN_UPDATE_ENDPOINT = "/api/auth/token";
    private final String USER_INFO_ENDPOINT = "/api/auth/user";

    private String body;
    private HashMap<String, String> headers;

    public Response registerUser(User user) {
        return doPostRequest(USER_REGISTER_ENDPOINT, user);
    }

    public Response logInUser(User user) {
        return doPostRequest(USER_LOGIN_ENDPOINT, user);
    }

    public Response logOutUser(String token) {
        body = String.format("{\"token\": \"%s\"}", token);
        return doPostRequest(USER_LOGOUT_ENDPOINT, body);
    }

    public Response updateUserToken(String token) {
        body = String.format("{\"token\": \"%s\"}", token);
        return doPostRequest(USER_TOKEN_UPDATE_ENDPOINT, body);
    }

    public Response getUserInfo(String token) {
        headers = new HashMap<>();
        headers.put("Authorization", token);
        return doGetRequestWithHeaders(USER_TOKEN_UPDATE_ENDPOINT, headers);
    }
}
