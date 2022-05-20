package com.myproject.expo.expositions.validation;

import com.myproject.expo.expositions.exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Validate {
    boolean isEmailValid(String email) throws ValidationException;

    boolean isNameValid(String name) throws ValidationException;

    boolean isSurnameValid(String surname) throws ValidationException;

    boolean isPhoneValid(String phone) throws ValidationException;

    boolean isPasswordValid(String password) throws ValidationException;

    String dateValidate(String date) throws ValidationException;

    String timeValidate(String time) throws ValidationException;

    String priceValidate(String price) throws ValidationException;

    String onlyDigitsValidate(String str) throws ValidationException;

    boolean validateProperDatesAndTime(LocalDate date, LocalTime time) throws ValidationException;
}
