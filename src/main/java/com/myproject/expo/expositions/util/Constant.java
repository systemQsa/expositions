package com.myproject.expo.expositions.util;

public final class Constant {
    public static final String REDIRECT = "redirect:";
    public static final String ACTION = "action";
    public static final String CONTROLLER = "controller";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USER_DATA = "userData";
    public static final String USER_EMAIL = "userEmail";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PHONE = "phone";
    public static final String LANG = "lang";
    public static final String ENG = "en";
    public static final String COUNTRY_US = "US";
    public static final String RESOURCES = "resources";
    public static final String COUNTRY = "country";
    public static final String LANGUAGE = "language";
    public static final String ROLE = "role";
    public static final String CONTENT_TYPE = "text/html";
    public static final String ENCODING = "UTF-8";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String NO_STORE_MUST_REVALIDATE = "no-store,must-revalidate";
    public static final String PRAGMA = "Pragma";
    public static final String NO_CACHE = "no-cache";
    public static final String EXPIRES = "Expires";
    public static final String JDBC_DATA = "jdbc/Data";
    public static final String LOOK_UP_ENV = "java:comp/env";
    public static final String PAGE = "page";
    public static final String NO_OF_RECORDS = "noOfRecords";
    public static final String THEME_LIST = "themeList";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String THEME_NAME = "themeName";
    public static final String ID_THEME = "idTheme";
    public static final String THEME_NEW_NAME = "themeNewName";
    public static final String ID_HALL = "idHall";
    public static final String HALL_LIST = "hallList";
    public static final String HALL_NEW_NAME = "hallNewName";
    public static final String EXPOS_LIST = "exposList";
    public static final String SORT_BY = "sortBy";
    public static final String DATE_FORMAT = "dateFormat";
    public static final String TIME_FORMAT = "timeFormat";
    public static final String EXPO_ID = "expoId";
    public static final String ONE_EXPO_DATA = "oneExpoData";
    public static final String REGEX_FOR_PIECE_OF_PATH_TO_CHANGE = "[a-z]+.jsp";
    public static final String CANCELED_EXPOS = "canceledExpos";
    public static final String SEARCHED_LIST = "searchedList";
    public static final String GUEST = "guest";
    public static final String SEARCHED_ITEM = "searchedItem";
    public static final String INLINE_RADIO_BTN = "inlineRadioOptions";
    public static final String NEW_PASS = "newPass";
    public static final String OLD_EMAIL = "oldEmail";
    public static final String NEW_EMAIL = "newEmail";
    public static final String PRICE = "price";
    public static final String COMMAND = "command";
    public static final String KEY = "123";

    public static class ErrMsg {
        public static final String REGISTER = "err.register";
        public static final String CANNOT_FIND_SUCH_USER = "err.cant_find_user";
        public static final String USER_IS_LOGGED = "err.user_is_logged";
        public static final String INCORRECT_EMAIL_INPUT = "err.email";
        public static final String INCORRECT_SURNAME_INPUT = "err.surname";
        public static final String INCORRECT_NAME_INPUT = "err.name";
        public static final String INCORRECT_PHONE_INPUT = "err.phone";
        public static final String INCORRECT_PASS_INPUT = "err.password";
        public static final String NO_MORE_RECORDS = "err.no_more_records";
    }

    public static class InfoMsg {

    }

    public static class LogMsg {
        public static final String PROBLEM_REGISTER_USER_IN_DAO = "Impossible to register a new user in UserDaoImpl class";
        public static final String PROBLEM_REGISTER_USER_IN_SERVICE = "Problem occur during registration the new user in UserService class";
        public static final String USER_ENTERED_INCORRECT_INPUT = "User entered incorrect input data for registration";
        public static final String USER_CANNOT_LOGIN = " cannot login in LoginCommand class";
        public static final String CONTROLLER_FAILED = "Controller is failed";
        public static final String LANG_FILTER_WORK = "LangFilter working";
        public static final String CLEAN_CACHE_FILTER_WORKING = "CleanCache filter working";
        public static final String ENCODING_FILTER_WORKING = "Encoding filter working";
        public static final String CANNOT_CLOSE_CONNECTION = "Cannot close connection";
        public static final String CONNECTION_TO_DB_FAILED = "Connection to the database is failed in ConnectionPool class!";
        public static final String CANNOT_ROLLBACK_COMMIT = "Cannot rollback the changes in database";
    }

    public static class URL {
        public static final String CONTROLLER = "/controller";
        public static final String REGISTER = "/register.jsp";
        public static final String LOGIN = "/login.jsp";
        public static final String FULL_PATH_LOGIN = "/expo/login.jsp";
        public static final String ROOT = "/expo";
        public static final String FULL_ADMIN_PAGE = "/WEB-INF/views/admin/admin.jsp";
        public static final String SEE_ONE_EXPO_REPLACEMENT = "seeOneExpo.jsp";
        public static final String FULL_USER_PAGE = "/WEB-INF/views/user/user.jsp";
        public static final String USER_REDIRECT = "redirect:/views/user/user.jsp";
        public static final String ADMIN_REDIRECT = "redirect:/views/admin/admin.jsp";
        public static final String ROOT_FULL_PATH = "/expo/login.js";
        public static final String ADD_NEW_EXPO_FULL_PATH = "/WEB-INF/views/admin/addNewExpo.jsp";
        public static final String ADD_EXPO_REDIRECT = "redirect:/views/admin/addNewExpo.jsp";
    }

}
