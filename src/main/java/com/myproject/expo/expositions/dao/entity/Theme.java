package com.myproject.expo.expositions.dao.entity;


import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Theme implements Serializable, Comparable<Theme> {
    private static final long serialUID = 1244454565656577822L;
    private long idTheme;
    private String name;
    private long allRecords;

    public static class ThemeBuilder {
        private final Theme theme;

        public ThemeBuilder() {
            theme = new Theme();
        }

        public ThemeBuilder setIDTheme(long idTheme) {
            theme.idTheme = idTheme;
            return this;
        }

        public ThemeBuilder setThemeName(String name) {
            theme.name = name;
            return this;
        }

        public ThemeBuilder setAllRecords(long allRecords) {
            theme.allRecords = allRecords;
            return this;
        }

        public Theme build() {
            return theme;
        }
    }

    public static ThemeBuilder builder() {
        return new ThemeBuilder();
    }

    public void setIdTheme(long idTheme) {
        this.idTheme = idTheme;
    }

    public long getIdTheme() {
        return idTheme;
    }

    public String getName() {
        return name;
    }

    public long getAllRecords() {
        return allRecords;
    }

    @Override
    public int compareTo(Theme o) {
        return Comparator.comparing((Theme theme) -> theme.idTheme)
                .compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return idTheme == theme.idTheme && Objects.equals(name, theme.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTheme, name);
    }

    @Override
    public String toString() {
        return "Theme{" +
                "idTheme=" + idTheme +
                ", name='" + name + '\'' +
                '}';
    }
}
