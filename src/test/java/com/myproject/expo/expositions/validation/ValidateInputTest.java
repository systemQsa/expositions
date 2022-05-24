package com.myproject.expo.expositions.validation;

import com.myproject.expo.expositions.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateInputTest {
    private static Validate validate;

    @BeforeClass
    public static void init() {
        validate = new ValidateInput();
    }

    @Test
    public void isEmailValidPositive() {
        List<String> emails = Arrays.asList("user@gmail.com", "Alex@gmail.com", "користувач@gmail.com",
                "user123@gmail.com", "ABC@gmail.com");
        for (String email : emails) {
            Assertions.assertThatCode(() -> validate.isEmailValid(email)).doesNotThrowAnyException();
        }
    }

    @Test(expected = ValidationException.class)
    public void isEmailValidNegative() throws ValidationException {
        List<String> badEmails = Arrays.asList("user#gmail.com",
                "usergmail", "peter$gmail.com", "користувач1@gmail");
        for (String email : badEmails) {
            validate.isEmailValid(email);
        }
    }

    @Test
    public void isNameValidPositive() {
        List<String> names = Arrays.asList("sasha", "Peter", "SuperMan", "Артем");
        for (String name : names) {
            Assertions.assertThatCode(() -> validate.isNameValid(name)).doesNotThrowAnyException();
        }
    }

    @Test
    public void isPhoneValidPositive() {
        List<String> phones = Arrays.asList("+380983456123", "+1234568", "+4567892345", "+390894567234", "+12345678902345");
        for (String phone : phones) {
            Assertions.assertThatCode(() -> validate.isPhoneValid(phone)).doesNotThrowAnyException();
        }
    }

    @Test(expected = ValidationException.class)
    public void isPhoneValidNegative() throws ValidationException {
        List<String> badPhones = Arrays.asList("45678", "+123456", "+123456789012345", "12", "1", "@", "^&*)");
        for (String phone : badPhones) {
            validate.isPhoneValid(phone);
        }
    }

    @Test
    public void isPasswordValidPositive() {
        List<String> passwords = Arrays.asList("1a3", "156778", "45Q7yU805555");
        for (String password : passwords) {
            Assertions.assertThatCode(() -> validate.isPasswordValid(password))
                    .doesNotThrowAnyException();
        }
    }

    @Test(expected = ValidationException.class)
    public void isPasswordValidNegative() throws ValidationException {
        List<String> badPass = Arrays.asList("1", "@", "$%^");
        for (String pass : badPass) {
            validate.isPasswordValid(pass);
        }
    }

    @Test(expected = ValidationException.class)
    public void dateValidate() throws ValidationException {
        validate.dateValidate("345/45/12");
    }

    @Test(expected = ValidationException.class)
    public void timeValidate() throws ValidationException {
        validate.timeValidate("345:56");
    }

    @Test
    public void priceValidate() {
        assertDoesNotThrow(() -> validate.priceValidate("1234.40"));
    }

    @Test
    public void onlyDigitsValidate() {
        assertDoesNotThrow(() -> validate.onlyDigitsValidate("123"));
    }

    @Test(expected = ValidationException.class)
    public void validateProperDatesAndTime() throws ValidationException {
        validate.validateProperDateAndTime(LocalDate.of(2022, 2, 19), LocalTime.of(17, 30));
    }

    @Test(expected = ValidationException.class)
    public void isUserBlocked() throws ValidationException {
        validate.isUserBlocked("blocked");
    }
}
