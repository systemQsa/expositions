package com.myproject.expo.expositions.command;

import com.myproject.expo.expositions.dao.entity.Exposition;
import com.myproject.expo.expositions.dao.entity.Hall;
import com.myproject.expo.expositions.dao.entity.Theme;
import com.myproject.expo.expositions.dao.entity.User;
import com.myproject.expo.expositions.exception.ValidationException;
import com.myproject.expo.expositions.util.Constant;
import com.myproject.expo.expositions.validation.Validate;
import com.myproject.expo.expositions.validation.ValidateInput;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
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
        req.setAttribute("infoMsg", translateInfoMessageToRequiredLang(message,
                (ResourceBundle) req.getSession().getAttribute("language")));
        return true;
    }

    default String translateInfoMessageToRequiredLang(String message, ResourceBundle bundle) {
        return bundle.getString(message);
    }

    default long parseStrToLong(String number) {
        if (number != null) {
            return Long.parseLong(number);
        }
        return 0;
    }

    default LocalDate parseStrToLocalDate(String date) {
        final DateTimeFormatter datePatternEng = DateTimeFormatter.ofPattern("M/dd/yy");
        final DateTimeFormatter datePatternUkr = DateTimeFormatter.ofPattern("dd.MM.yy");
        return LocalDate.parse(date, Pattern.compile("\\d{1,2}/\\d{2}/\\d{2}").matcher(date).matches() ? datePatternEng : datePatternUkr);
    }

    default LocalTime parseStrToLocalTime(String time) {
        final DateTimeFormatter timePatternEng = DateTimeFormatter.ofPattern("h:mm a");
        final DateTimeFormatter timePatternUkr = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, Pattern.compile("\\d{1,2}:\\d{2}\\W[A-Z]{2}").matcher(time).matches() ? timePatternEng : timePatternUkr);
    }

    default BigDecimal parseToBigDecimal(String price) {
        return new BigDecimal(price);
    }
}
