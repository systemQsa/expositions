package com.myproject.expo.expositions.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Exposition implements Serializable, Comparable<Exposition> {
    private static final long serialUID = 4555697977776111109L;
    private long idExpo;
    private String name;
    private LocalDate expoDate;
    private LocalTime expoTime;
    private BigDecimal price;
    private long sold;
    private Theme theme;
    private long tickets;
    private int statusId;
    private List<Hall> hallList;

    public static class ExpositionBuilder {
        private final Exposition expo;

        public ExpositionBuilder() {
            expo = new Exposition();
        }

        public ExpositionBuilder setExpositionID(long idExpo) {
            expo.idExpo = idExpo;
            return this;
        }

        public ExpositionBuilder setExpoName(String name) {
            expo.name = name;
            return this;
        }

        public ExpositionBuilder setExpoDate(LocalDate expoDate) {
            expo.expoDate = expoDate;
            return this;
        }

        public ExpositionBuilder setExpoTime(LocalTime expoTime) {
            expo.expoTime = expoTime;
            return this;
        }

        public ExpositionBuilder setExpoPrice(BigDecimal price) {
            expo.price = price;
            return this;
        }

        public ExpositionBuilder setTheme(Theme theme) {
            expo.theme = theme;
            return this;
        }

        public ExpositionBuilder setExpoSoldTickets(long sold) {
            expo.sold = sold;
            return this;
        }

        public Exposition getExpo() {
            return expo;
        }

        public ExpositionBuilder setTickets(long tickets) {
            expo.tickets = tickets;
            return this;
        }

        public ExpositionBuilder setStatusId(int statusId) {
            expo.statusId = statusId;
            return this;
        }

        public ExpositionBuilder setHallList(List<Hall> hallList) {
            expo.hallList = hallList;
            return this;
        }

        public Exposition build() {
            return expo;
        }
    }

    public static ExpositionBuilder builder() {
        return new ExpositionBuilder();
    }

    public void setIdExpo(long idExpo) {
        this.idExpo = idExpo;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    public long getIdExpo() {
        return idExpo;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return expoDate;
    }

    public LocalTime getTime() {
        return expoTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getSold() {
        return sold;
    }

    public Theme getTheme() {
        return theme;
    }

    public long getTickets() {
        return tickets;
    }

    public List<Hall> getHallList() {
        return hallList;
    }

    public int getStatusId() {
        return statusId;
    }

    @Override
    public int compareTo(Exposition o) {
        return Comparator.comparing((Exposition e) -> e.getDate().compareTo(o.getDate()))
                .thenComparing(elem -> elem.getTime().compareTo(o.getTime()))
                .thenComparing(elem -> elem.getHallList().stream()
                        .noneMatch(item -> o.getHallList().contains(item))).
                compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exposition that = (Exposition) o;
        return getIdExpo() == that.getIdExpo() && getSold() == that.getSold()
                && getTickets() == that.getTickets() && getStatusId() == that.getStatusId()
                && Objects.equals(getName(), that.getName()) && Objects.equals(expoDate, that.expoDate)
                && Objects.equals(expoTime, that.expoTime) && Objects.equals(getPrice(), that.getPrice())
                && Objects.equals(getTheme(), that.getTheme()) && Objects.equals(getHallList(), that.getHallList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdExpo(), getName(), expoDate, expoTime,
                getPrice(), getSold(), getTheme(), getTickets(), getStatusId(), getHallList());
    }

    @Override
    public String toString() {
        return "Exposition{" +
                "idExpo=" + idExpo +
                ", name='" + name + '\'' +
                ", expoDate=" + expoDate +
                ", expoTime=" + expoTime +
                ", price=" + price +
                ", sold=" + sold +
                ", theme=" + theme +
                ", tickets=" + tickets +
                ", statusId=" + statusId +
                ", hallList=" + hallList +
                '}';
    }
}
