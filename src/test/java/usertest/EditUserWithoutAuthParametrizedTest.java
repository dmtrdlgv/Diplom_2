package usertest;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import site.nomoreparties.stellarburgers.model.RandomGenerator;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.steps.UserSteps;

@RunWith(Parameterized.class)
public class EditUserWithoutAuthParametrizedTest {

    private final String email;
    private final String password;
    private final String name;
    private static final UserSteps userSteps = new UserSteps();
    private User baseUser;
    private User editUser;
    private static Response response;

    public EditUserWithoutAuthParametrizedTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "Test data for user edit: email: {0}, password: {1}, name: {2}")
    public static Object[][] getData() {
        return new Object[][] {
                {RandomGenerator.randomEmail(), null, null},
                {null, null, RandomGenerator.randomName()},
                {null, RandomGenerator.randomPassword(), null},
        };
    }

    @Test
    public void editUserInfo_WithoutAuthToken_ExpectedError(){

        baseUser = new User();
        editUser = new User(email, password, name);
        baseUser.fillRandomUserData();
        userSteps.setTokensFromResponseToUser(userSteps.registerUser(baseUser), baseUser);
        response = userSteps.updateUserInfo(null, editUser);
        userSteps.checkStatusCodeAndBodyOfErrorResponse(response, 401, "You should be authorised");
    }
}

