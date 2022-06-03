package com.myproject.expo.expositions.command;

import com.myproject.expo.expositions.dao.entity.Status;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.util.Constant;
import com.myproject.expo.expositions.validation.Validate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public interface UtilMethods {

    default User getUserFromSession(HttpServletRequest req) {
        return (User) req.getSession().getAttribute(Constant.USER_DATA);
    }

    default int getNoOfRecordsFromSession(HttpServletRequest req) {
        return (int) req.getSession().getAttribute(Constant.NO_OF_RECORDS);
    }

    default boolean setInformMessageToUser(int infoNum, HttpServletRequest req, String message) {
        req.setAttribute("infoNum", infoNum);
        req.setAttribute("infoMsg", translateInfoMessageToRequiredLang(message, req));
        return true;
    }

    default void setInfMSGToSession(String message, HttpServletRequest req) {
        req.getSession().setAttribute("infMsg", translateInfoMessageToRequiredLang(message, req));
    }

    default String translateInfoMessageToRequiredLang(String message, HttpServletRequest req) {
        ResourceBundle bundle = ((ResourceBundle) req.getSession().getAttribute("language"));
        return bundle.getString(message);
    }

    default long parseStrToLong(String number) {
        if (number != null) {
            return Long.parseLong(number);
        }
        return 0;
    }

    default LocalDate parseStrToLocalDate(String date) {
        final DateTimeFormatter datePatternEng = DateTimeFormatter.ofPattern("M/d/yy");
        final DateTimeFormatter datePatternUkr = DateTimeFormatter.ofPattern("dd.MM.yy");
        return LocalDate.parse(date, Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{2}").matcher(date).matches()
                ? datePatternEng : datePatternUkr);
    }

    default LocalTime parseStrToLocalTime(String time) {
        final DateTimeFormatter timePatternEng = DateTimeFormatter.ofPattern("h:mm a");
        final DateTimeFormatter timePatternUkr = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, Pattern.compile("\\d{1,2}:\\d{2}\\W[A-Z]{2}").matcher(time).matches()
                ? timePatternEng : timePatternUkr);
    }

    default BigDecimal parseToBigDecimal(String price) {
        return new BigDecimal(price);
    }

    default int getStatusId(String status) {
        return Arrays.stream(Status.values())
                .filter(item -> item.getName().equals(status))
                .mapToInt(Status::getStatusId)
                .findFirst()
                .orElse(0);
    }

    default void validateExpoInput(HttpServletRequest req, Validate validate) throws ValidationException {
        validate.notEmptyStr(req,Constant.Param.EXPO_NAME);
        validate.dateValidate(req.getParameter(Constant.Param.EXPO_DATE));
        validate.timeValidate(req.getParameter(Constant.Param.EXPO_TIME));
        validate.priceValidate(req.getParameter(Constant.Param.EXPO_PRICE));
        validate.onlyDigitsValidate(req.getParameter(Constant.Param.EXPO_SOLD));
        validate.onlyDigitsValidate(req.getParameter(Constant.Param.EXPO_TICKETS));
    }
}
