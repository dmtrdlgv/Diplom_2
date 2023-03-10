package site.nomoreparties.stellarburgers.model;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class RandomGenerator {

    private static Faker faker = new Faker(Locale.forLanguageTag("ru"));

    public static String randomName() {
        return faker.name().fullName();
    }

    //применяется lowerCase т.к. почтовые адреса нерегистрозависимы
    public static String randomEmail() {
        return String.format("%s@%s.ru", RandomStringUtils.randomAlphanumeric(5,20),RandomStringUtils.randomAlphanumeric(5,10)).toLowerCase();
    }

    public static String randomPassword() {
        return RandomStringUtils.randomAlphanumeric(5,20) + "!";
    }

}
