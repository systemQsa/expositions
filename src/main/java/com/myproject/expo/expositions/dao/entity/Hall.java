package com.myproject.expo.expositions.dao.entity;


import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Hall implements Serializable, Comparable<Hall> {
    private static final long serialUID = 345678902345671567L;
    private long idHall;
    private String name;
    private int expoId;

    public static class HallBuilder {
        private final Hall hall;

        public HallBuilder() {
            hall = new Hall();
        }

        public HallBuilder setIDHall(long idHall) {
            hall.idHall = idHall;
            return this;
        }

        public HallBuilder setHallName(String name) {
            hall.name = name;
            return this;
        }

        public HallBuilder setExpositionId(int expoId) {
            hall.expoId = expoId;
            return this;
        }

        public Hall build() {
            return hall;
        }
    }

    public static HallBuilder builder() {
        return new HallBuilder();
    }

    public long getIdHall() {
        return idHall;
    }

    public void setIdHall(long idHall) {
        this.idHall = idHall;
    }

    public String getName() {
        return name;
    }

    public int getExpoId() {
        return expoId;
    }

    @Override
    public int compareTo(Hall o) {
        return Comparator.comparing((Hall h) -> h.idHall)
                .compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return idHall == hall.idHall && expoId == hall.expoId && Objects.equals(name, hall.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHall, name, expoId);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "idHall=" + idHall +
                ", name='" + name + '\'' +
                ", expoId=" + expoId +
                '}';
    }
}
