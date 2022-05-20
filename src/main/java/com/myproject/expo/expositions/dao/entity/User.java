package com.myproject.expo.expositions.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class User implements Serializable, Comparable<User> {
    private static final long serialUID = 234561567867833455L;
    private long idUser;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private char[] password;
    private BigDecimal balance;
    private UserRole userRole;

    public static class UserBuilder {
        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder setIdUser(long idUser) {
            user.idUser = idUser;
            return this;
        }

        public UserBuilder setName(String name) {
            user.name = name;
            return this;
        }

        public UserBuilder setSurname(String surname) {
            user.surname = surname;
            return this;
        }

        public UserBuilder setEmail(String email) {
            user.email = email;
            return this;
        }

        public UserBuilder setPassword(char[] password) {
            user.password = password;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            user.phone = phone;
            return this;
        }

        public UserBuilder setBalance(BigDecimal balance) {
            user.balance = balance;
            return this;
        }

        public UserBuilder setUserRole(UserRole userRole) {
            user.userRole = userRole;
            return this;
        }

        public User build() {
            return user;
        }

    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public char[] getPassword() {
        return password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public int compareTo(User o) {
        return Comparator.comparing((User user) -> user.name)
                .thenComparing(user -> user.email)
                .thenComparing(user -> user.phone)
                .compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idUser == user.idUser && Objects.equals(name, user.name)
                && Objects.equals(surname, user.surname) && Objects.equals(email, user.email)
                && Objects.equals(phone, user.phone) && Arrays.equals(password, user.password)
                && Objects.equals(balance, user.balance);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(idUser, name, surname, email, phone, balance);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password=" + Arrays.toString(password) +
                ", balance=" + balance +
                '}';
    }
}
