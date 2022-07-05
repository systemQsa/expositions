package com.myproject.expo.expositions.dao.sql;

public final class Query {
    public final static class UserSQL {
        public static final String REGISTER_USER = "INSERT INTO users(name,surname,email,password,phone)" +
                "VALUES(?,?,?,?,?)";
        public static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";
        public static final String UPDATE_USER_BALANCE = "UPDATE users SET balance=? WHERE id_user=?";
        public static final String INSERT_REFS_EXPO_USER = "INSERT INTO exposition_users(expo_id, user_id) VALUES (?,?)";
        public static final String WITHDRAW_MONEY_AND_UPDATE_EXPOS_SOLD_TICKETS = "UPDATE users,exposition SET balance=?,sold=?,tickets=? WHERE id_expo=? AND id_user=?";
        public static final String UPDATE_USER_EMAIL = "UPDATE users SET email=? WHERE email=?";
        public static final String UPDATE_USER_PASS = "UPDATE users SET password=? WHERE email=?";
        public static final String GET_ALL_USERS = "SELECT * FROM users";
        public static final String UPDATE_USER_STATUS = "UPDATE users SET status_id=? WHERE id_user=?";
    }

    public final static class ExpoSQL {
        public static final String ALL_EXPOS = "select e.id_expo,e.name,expo_date,expo_time,price,sold,id_theme_ref,t.name,tickets,status_id,\n" +
                " GROUP_CONCAT(distinct hall.name separator ', ') as halls,\n" +
                " GROUP_CONCAT(distinct h.id_hall separator ', ') as ids\n" +
                "from exposition as e\n" +
                "join expo_hall as h on e.id_expo = h.id_expo\n" +
                "join theme as t on e.id_theme_ref = t.id_theme\n" +
                "join hall on h.id_hall= hall.id_hall\n" +
                "group by e.id_expo";
        public static final String DESC_LIMIT = "DESC LIMIT ?,?";
        public static final String ASC_LIMIT = "LIMIT ?,?";
        public static final String SEARCH_BY_THEME = ALL_EXPOS + ",t.name HAVING t.name=? ORDER BY e.id_expo DESC";
        public static final String SEARCH_BY_DATE = ALL_EXPOS + ",expo_date HAVING expo_date=? ORDER BY e.id_expo DESC";
        public static final String SORT_BY_ID = ALL_EXPOS + " ORDER BY  id_expo " + DESC_LIMIT;
        public static final String SORT_BY_DATE = ALL_EXPOS + " ORDER BY expo_date " + ASC_LIMIT;
        public static final String SORT_BY_THEME = ALL_EXPOS + " ORDER BY t.name " + ASC_LIMIT;
        public static final String SORT_BY_PRICE = ALL_EXPOS + " ORDER BY price " + ASC_LIMIT;
        public static final String STATISTICS = ALL_EXPOS + ",sold ORDER BY sold " + DESC_LIMIT;
        public static final String GET_CANCELED_EXPOS_FOR_USER = "SELECT e.id_expo,e.name,expo_date,expo_time,price,sold,id_theme_ref,t.name,tickets,status_id,\n" +
                "  GROUP_CONCAT(h.name separator ', ') as halls,\n" +
                "  GROUP_CONCAT(distinct h.id_hall separator ', ') as ids\n" +
                "  FROM exposition AS e\n" +
                "  JOIN expo_hall as eh on e.id_expo = eh.id_expo\n" +
                "  JOIN hall AS h ON eh.id_hall = h.id_hall\n" +
                "  JOIN theme AS t ON e.id_theme_ref = t.id_theme\n" +
                "  JOIN exposition_users AS exp ON e.id_expo = exp.expo_id\n" +
                "  WHERE user_id=? AND status_id=?\n" +
                "  GROUP BY e.id_expo\n" +
                "  ORDER BY id_expo DESC";
        public static final String UPDATE_EXPO = "UPDATE exposition SET name=?,expo_date=?,expo_time=?,price=?,sold=?,id_theme_ref=?,tickets=? WHERE id_expo=?";
        public static final String DELETE_EXPO_FROM_EXPO_HALL_TABLE = "DELETE FROM expo_hall WHERE id_expo=?";
        public static final String INSERT_REFS_TO_EXPO_HALL_TABLE = "INSERT INTO expo_hall(id_expo, id_hall) VALUES (?,?)";
        public static final String ADD_NEW_EXPO = "INSERT INTO exposition(name, expo_date, expo_time, price, sold, id_theme_ref, tickets) VALUES (?,?,?,?,?,?,?)";
        public static final String UPDATE_EXPO_STATUS = "UPDATE exposition SET status_id=? WHERE id_expo=?";
        public static final String DELETE_EXPO = "DELETE FROM exposition WHERE id_expo=?";
        public static final String GET_AMOUNT_BOUGHT_TICKETS_PER_PERSON = "SELECT e.id_expo,user_id,\n" +
                " (select count(ha.expo_id) from exposition_users\n" +
                " as ha where ha.expo_id = e.id_expo AND ha.user_id=exp.user_id) as expos\n" +
                "  FROM exposition AS e\n" +
                " JOIN expo_hall as eh on e.id_expo = eh.id_expo\n" +
                " JOIN hall AS h ON eh.id_hall = h.id_hall\n" +
                " JOIN theme AS t ON e.id_theme_ref = t.id_theme\n" +
                " JOIN exposition_users AS exp ON e.id_expo = exp.expo_id\n" +
                " WHERE user_id=? AND status_id=?\n" +
                " GROUP BY e.id_expo\n" +
                "ORDER BY id_expo DESC";

    }

    public final static class ThemeSQL {
        public static final String GET_ALL_THEMES_PAGINATE = "SELECT * FROM theme ORDER BY id_theme DESC LIMIT ?,?";
        public static final String ADD_NEW_THEME = "INSERT INTO theme(name) VALUES (?)";
        public static final String UPDATE_THEME = "UPDATE theme SET name=? WHERE id_theme=?";
        public static final String DELETE_THEME = "DELETE FROM theme WHERE id_theme=?";
        public static final String GET_ALL_THEMES = "SELECT * FROM theme";
        public static final String GET_THEMES_BY_NAME = "SELECT * FROM theme ORDER BY name DESC LIMIT ?,?";
        public static final String GET_ALL_THEMES_DEFAULT = "SELECT * FROM theme ORDER BY id_theme DESC LIMIT ?,?";
        public static final String CHECK_THEME_PRESENT_IN_EXPO = "SELECT id_expo FROM exposition WHERE id_theme_ref=?";
    }

    public final static class HallSQL {
        public static final String ADD_NEW_HALL = "INSERT INTO hall(name) VALUES (?)";
        public static final String GET_ALL_HALLS_PAGINATE = "SELECT * FROM hall ORDER BY id_hall DESC LIMIT ?,?";
        public static final String UPDATE_HALL = "UPDATE hall SET name=? WHERE id_hall=?";
        public static final String DELETE_HALL = "DELETE FROM hall WHERE id_hall=?";
        public static final String GET_ALL_HALLS = "SELECT * FROM hall";
        public static final String CHECK_HALL_PRESENT_IN_EXPO = "SELECT id_expo FROM expo_hall WHERE id_hall=?";
    }
}
