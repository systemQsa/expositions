package com.myproject.expo.expositions.validation;

import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.myproject.expo.expositions.util.Constant.*;

public class ValidateInput implements Validate {
    private static final Logger logger = LogManager.getLogger(ValidateInput.class);
    private static final String PASSWORD_REGEX = "^[\\w+]{3,20}$";
    private static final String EMAIL_REGEX = "^(([\\w-]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    private static final String EMAIL_CYRILLIC = "^(([\\p{IsCyrillic}]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    private static final String REGEX_ONLY_WORDS = "^(\\p{L}+){3,17}$";
    private static final String REGEX_PHONE = "^[+]?[\\d]{7,14}$";
    private static final String DATE_ENG_REGEX = "\\d{1,2}/\\d{1,2}/\\d{2}";
    private static final String DATE_REGEX = "\\d{2}.\\d{2}.\\d{2}";
    private static final String TIME_ENG_REGEX = "\\d{1,2}:\\d{2}\\W[A-Z]{2}";
    private static final String TIME_UKR_REGEX = "\\d{2}:\\d{2}";
    private static final String PRICE_REGEX = "\\d+\\.\\d{2}";
    private static final String ONLY_DIGITS = "^\\d+$";

    @Override
    public boolean isEmailValid(String email) throws ValidationException {
        return Optional.ofNullable(email)
                .map(mail -> (Pattern.compile(EMAIL_REGEX).matcher(mail).matches()
                        || Pattern.compile(EMAIL_CYRILLIC).matcher(mail).matches()))
                .filter(res -> res)
                .orElseThrow(() -> new ValidationException(ErrMsg.INCORRECT_EMAIL_INPUT));

    }

    @Override
    public boolean isSurnameValid(String surname) throws ValidationException {
        return Optional.ofNullable(surname)
                .map(userSurname -> Pattern.compile(REGEX_ONLY_WORDS)
                        .matcher(userSurname)
                        .matches())
                .filter(res -> res)
                .orElseThrow(() -> new ValidationException(ErrMsg.INCORRECT_SURNAME_INPUT));
    }

    @Override
    public boolean isNameValid(String name) throws ValidationException {
        return Optional.ofNullable(name)
                .map(userName -> Pattern.compile(REGEX_ONLY_WORDS)
                        .matcher(userName).matches())
                .filter(res -> res)
                .orElseThrow(() -> new ValidationException(ErrMsg.INCORRECT_NAME_INPUT));
    }

    @Override
    public boolean isPhoneValid(String phone) throws ValidationException {
        return Optional.ofNullable(phone)
                .map(userPhone -> Pattern.compile(REGEX_PHONE).matcher(userPhone).matches())
                .filter(res -> res)
                .orElseThrow(() -> new ValidationException(ErrMsg.INCORRECT_PHONE_INPUT));
    }

    @Override
    public boolean isPasswordValid(String password) throws ValidationException {
        return Optional.ofNullable(password)
                .map(pass -> Pattern.compile(PASSWORD_REGEX)
                        .matcher(pass)
                        .matches())
                .filter(res -> res)
                .orElseThrow(() -> new ValidationException(ErrMsg.INCORRECT_PASS_INPUT));
    }

    @Override
    public String dateValidate(String date) throws ValidationException {
        return validateDate(date) ? date : null;
    }

    @Override
    public String timeValidate(String time) throws ValidationException {
        return validateTime(time) ? time : null;
    }

    @Override
    public String priceValidate(String price) throws ValidationException {
        return validatePrice(price) ? price : null;
    }

    @Override
    public String onlyDigitsValidate(String str) throws ValidationException {
        return containsOnlyDigits(str) ? str : null;
    }

    @Override
    public boolean validateProperDateAndTime(LocalDate date, LocalTime time) throws ValidationException {
        return validateProperDate(date) && validateProperTime(time);
    }

    @Override
    public boolean isUserBlocked(String status) throws ValidationException {
        if (status.equals(BLOCKED)) {
            throw new ValidationException(ErrMsg.BLOCKED_USER);
        }
        return false;
    }

    @Override
    public String notEmptyStr(HttpServletRequest req,String param) throws ValidationException {
        return   Optional.ofNullable(req.getParameter(param))
                .filter(Objects::nonNull)
                .filter(str -> !str.isEmpty())
                .orElseThrow(() -> new ValidationException(ErrMsg.EMPTY_STR_INPUT));
    }

    private boolean validateDate(String date) throws ValidationException {
        return Optional.ofNullable(date)
                .map(str -> (Pattern.compile(DATE_REGEX).matcher(str).matches())
                        || Pattern.compile(DATE_ENG_REGEX).matcher(date).matches())
                .filter(str -> str)
                .orElseThrow(() -> new ValidationException(ErrMsg.DATE));
    }

    private boolean validateTime(String time) throws ValidationException {
        return Optional.ofNullable(time)
                .map(str -> (Pattern.compile("\\d{1,2}:\\d{2}\\W[A-Z]{2}").matcher(time).matches())
                        || Pattern.compile(TIME_UKR_REGEX).matcher(str).matches())
                .filter(str -> str)
                .orElseThrow(() -> new ValidationException(ErrMsg.TIME));
    }

    private boolean validatePrice(String price) throws ValidationException {
        return Optional.ofNullable(price)
                .map(str -> Pattern.compile(PRICE_REGEX).matcher(str).matches())
                .filter(str -> str)
                .orElseThrow(() -> new ValidationException(ErrMsg.PRICE));
    }

    private boolean containsOnlyDigits(String str) throws ValidationException {
        return Optional.ofNullable(str)
                .map(value -> Pattern.compile(ONLY_DIGITS).matcher(value).matches())
                .filter(value -> value)
                .orElseThrow(() -> new ValidationException(ErrMsg.ONLY_DIGITS));
    }

    private boolean validateProperDate(LocalDate date) throws ValidationException {
        if (date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now())) {
            return true;
        }
        throw new ValidationException(ErrMsg.DATE);
    }

    private boolean validateProperTime(LocalTime time) throws ValidationException {
        LocalTime fromTime = LocalTime.of(9, 0);
        LocalTime toTime = LocalTime.of(21, 30);
        if (time.isAfter(fromTime) || time.isBefore(toTime)) {
            return true;
        }
        throw new ValidationException(ErrMsg.TIME);
    }



}
