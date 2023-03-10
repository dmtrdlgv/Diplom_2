package usertest;

import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import site.nomoreparties.stellarburgers.model.RandomGenerator;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.steps.UserSteps;

@RunWith(Parameterized.class)
public class LoginUserNegativeParametrizedTest {

    private final String email;
    private final String password;
    private static final UserSteps userSteps = new UserSteps();
    private final String expectedMessage = "email or password are incorrect";
    private final int expectedStatusCode = 401;
    private static User baseUser;
    private User loginUser;
    private Response response;

    public LoginUserNegativeParametrizedTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @BeforeClass
    public static void init() {
        //Основной объект пользователя со всеми данными
        baseUser = new User();
        baseUser.fillRandomUserData();
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1}")
    public static Object[][] getData() {
        return new Object[][] {
                {RandomGenerator.randomEmail(), null},
                {null, RandomGenerator.randomPassword()},
                {null, null}
        };
    }

    @Test
    public void loginUser_WithoutOneOfField_ExpectedError() {
        if (email != null) baseUser.setEmail(email);
        if (password != null) baseUser.setPassword(password);
        userSteps.setTokensFromResponseToUser(userSteps.registerUser(baseUser), baseUser);
        loginUser = new User();
        loginUser.setEmail(email);
        loginUser.setPassword(password);
        response = userSteps.loginUser(loginUser);
        userSteps.checkStatusCodeAndBodyOfErrorResponse(response, expectedStatusCode, expectedMessage);
    }

    @Parameterized.AfterParam
    public static void tearDown(){
        if (baseUser.getAccessToken() != null) {
            userSteps.deleteUserAndCheckResponse(baseUser.getAccessToken());
            baseUser.setRefreshToken(null);
            baseUser.setAccessToken(null);
        }
    }
}
