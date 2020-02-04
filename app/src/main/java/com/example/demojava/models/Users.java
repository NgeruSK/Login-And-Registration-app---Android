package com.example.demojava.models;

public class Users {
    public static final String TABLE_USERS="users";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "CITY";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";

    int id;
    String name;
    String city;

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
