package usertest;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import site.nomoreparties.stellarburgers.utils.RandomGenerator;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.steps.UserSteps;

@RunWith(Parameterized.class)
public class EditUserPositiveParametrizedTest {

    private final String email;
    private final String password;
    private final String name;
    private static final UserSteps userSteps = new UserSteps();
    private User baseUser;
    private User loginUser;
    private User editUser;
    private static Response response;

    public EditUserPositiveParametrizedTest(String email, String password, String name) {
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
                {RandomGenerator.randomEmail(), RandomGenerator.randomPassword(), RandomGenerator.randomName()},
                {null, null, null}
        };
    }

    @Test
    public void editUserInfoExpectedOk(){

        baseUser = new User();
        editUser = new User(email, password, name);
        loginUser = new User();
        baseUser.fillRandomUserData();
        userSteps.setTokensFromResponseToUser(userSteps.registerUser(baseUser), baseUser);
        response = userSteps.updateUserInfo(baseUser.getAccessToken(), editUser);

        //присвоение юзеру данных из параметров, чтобы потом сверить успешные изменения
        if (email != null) baseUser.setEmail(email);
        if (password != null) baseUser.setPassword(password);
        if (name != null) baseUser.setName(name);

        loginUser.setEmail(baseUser.getEmail());
        loginUser.setPassword(baseUser.getPassword());

        //проверка почты и пароля в теле ответа запроса на изменение юзера
        userSteps.checkResponseOfUpdateUser(response, baseUser);
        response = userSteps.loginUser(loginUser);

        //проверка изменения пароля через успешный вход с новыми данными
        userSteps.checkStatusCodeAndResponseBodyOfUserLogin(response, 200, baseUser);
    }

    @Parameterized.AfterParam
    public static void tearDown() {
        userSteps.deleteUser(response.path("accessToken").toString());
    }
}
