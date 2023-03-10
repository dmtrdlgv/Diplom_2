package usertest;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import site.nomoreparties.stellarburgers.utils.RandomGenerator;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.steps.UserSteps;

@RunWith(Parameterized.class)
public class RegisterUserNegativeParametrizedTest {

    private final String email;
    private final String password;
    private final String name;
    private final UserSteps userSteps = new UserSteps();
    private final String expectedMessage = "Email, password and name are required fields";
    private final int expectedStatusCode = 403;
    private User user;
    private Response response;

    public RegisterUserNegativeParametrizedTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1}, {2}")
    public static Object[][] getData() {
        return new Object[][] {
                {RandomGenerator.randomEmail(), RandomGenerator.randomPassword(), null,},
                {RandomGenerator.randomEmail(), null, RandomGenerator.randomName()},
                {null, RandomGenerator.randomPassword(), RandomGenerator.randomName()},
                {null, null, null}
        };
    }

    @Test
    public void registerUser_withoutRequiredField_Expected403Error() {
        user = new User(email, password, name);
        response = userSteps.registerUser(user);
        userSteps.checkStatusCodeAndBodyOfErrorResponse(response, expectedStatusCode, expectedMessage);
    }
}
