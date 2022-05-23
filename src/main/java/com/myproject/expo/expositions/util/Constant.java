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
    public static final String USER_EXPOS = "userExpos";
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
    public static final String USERS_LIST = "usersList";
    public static final String STATUS = "status";
    public static final String ID = "id";
    public static final String ACTIVE = "active";
    public static final String CANCELED = "canceled";
    public static final String LIST_HEADER = "listHeader";
    public static final String STATISTIC = "statistic";
    public static final String EXPOSITIONS = "expositions";
    public static final String STATISTICS = "Statistics";


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
        public static final String CANT_GET_EXPOS = "err.cant_get_expos";
        public static final String GET_ALL_USER_EXPOS = "err.get_all_canceled_expos_for_user";
        public static final String UPDATE_EXPO = "err.update_expo";
        public static final String ADD_NEW_EXPO = "err.add_new_expo";
        public static final String DELETE_EXPO = "err.delete_expo";
        public static final String CHANGE_EXPO_STATUS = "err.change_expo_status";
        public static final String NOTHING_WAS_FOUND = "err.nothing_found";
        public static final String ADD_HALL = "err.add_hall";
        public static final String GET_ALL_HALLS = "err.cant_get_all_halls";
        public static final String UPDATE_HALL = "err.cant_update_hall";
        public static final String DELETE_HALL = "err.cant_delete_hall";
        public static final String GET_ALL_THEMES = "err.all_themes";
        public static final String ADD_NEW_THEME = "err.add_new_theme";
        public static final String UPDATE_THEME = "err.cannot_update_theme";
        public static final String DELETE_THEME = "err.delete_theme";
        public static final String TOP_UP_BALANCE = "err.top_up_balance";
        public static final String NO_MORE_TICKETS = "err.no_more_tickets";
        public static final String POOR_BALANCE = "err.balance";
        public static final String SET_BALANCE = "err.set_balance_statement";
        public static final String SET_REFS_EXPO_USER = "err.set_refs";
        public static final String CHANGE_EMAIL = "err.change_email";
        public static final String CHANGE_PASSWORD = "err.change_pass";
        public static final String GET_ALL_USERS = "err.find_all_users";
        public static final String BLOCK_UNBLOCK_USER = "err.cant_block_unblock_user";
        public static final String NO_SUCH_STATUS = "err.no_such_status";
        public static final String TIME = "err.time";
        public static final String DATE = "err.date";
        public static final String ONLY_DIGITS = "err.only_digits";
        public static final String PRICE = "err.price";
        public static final String BLOCKED_USER = "err.you_blocked";
        public static final String INCORRECT_PASSWORD = "err.incorrect_password";
        public static final String CANCELED_EXPO = "err.expo_canceled";
        public static final String HALL_BUSY = "err.hall_busy";
        public static final String INCORRECT_SEARCH = "err.incorrect_search";
        public static final String CANCELED_EXPO_CANT_BUY = "err.canceled_expo";
    }

    public static class InfoMsg {
        public static final String CONTACT_MANAGER = "contact_manager";
        public static final String MY_ACTIVE_EXPOS = "my_active_expos";
        public static final String MY_CANCELED_EXPOS = "my_canceled_expos";
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
        public static final String CANT_GET_ALL_EXPOS = "Cant get all expos in ExpositionDaoImpl class";
        public static final String CANT_GET_USER_EXPOS = "Cannot get all expos user with id = ";
        public static final String ADD_NEW_EXPO = "Problem cannot add a new exposition to the table";
        public static final String UPDATE_EXPO_STATUS = "Updating the exposition status has failed in ExpositionDaoImpl class. Expo with id = ";
        public static final String SEARCH_BY_NAME = "Searching by name has failed";
        public static final String SEARCH_BY_DATE = "Searching by date has failed";
        public static final String ADD_HALL = "Cant add to the  DB hall name = ";
        public static final String GET_ALL_HALLS = "Impossible to get All halls from DB";
        public static final String UPDATE_HALL = "Cannot Update Hall  in HallDaoImpl class hallId = ";
        public static final String DELETE_HALL = "Cannot delete Hall with id = ";
        public static final String GET_ALL_THEMES = "Impossible to get All Themes from db";
        public static final String ADD_NEW_THEME = "Cannot add new theme to the db";
        public static final String TOP_UP_BALANCE = "User cannot top up balance! User id = ";
        public static final String CHANGE_EMAIL = "User can`t update the email.The user email = ";
        public static final String CHANGE_PASSWORD = "User with can`t update the pass. User email is ";
        public static final String GET_ALL_USERS = "Cannot get all registered users from DB";
        public static final String BUY_EXPO = "BuyExpo class failed. User cannot buy the exposition";
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
        public static final String SEE_ONE_EXPO_FULL_PATH = "/WEB-INF/views/admin/seeOneExpo.jsp";
    }

    public static class Column {
        public static final String NAME = "name";
        public static final String EXPO_DATE = "expo_date";
        public static final String EXPO_TIME = "expo_time";
        public static final String PRICE = "price";
        public static final String SOLD = "sold";
        public static final String TICKETS = "tickets";
        public static final String STATUS_ID = "status_id";
        public static final String ID_THEME_REF = "id_theme_ref";
        public static final String T_NAME = "t.name";
        public static final String IDS = "ids";
        public static final String HALLS = "halls";
        public static final String SURNAME = "surname";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String PHONE = "phone";
        public static final String BALANCE = "balance";
        public static final String ROLE_ID = "role_id";

    }

    public static class Param {
        public static final String EXPO_ID = "expoId";
        public static final String EXPO_NAME = "expoName";
        public static final String EXPO_DATE = "expoDate";
        public static final String EXPO_TIME = "expoTime";
        public static final String EXPO_PRICE = "expoPrice";
        public static final String EXPO_SOLD = "expoSold";
        public static final String EXPO_TICKETS = "expoTickets";
        public static final String HALLS = "halls";
        public static final String ID_THEME = "idTheme";
        public static final String HALL_NAME = "hallName";
        public static final String USER_ID = "userId";
        public static final String EXPO_HALL_ID = "expoHallId";
    }

}
