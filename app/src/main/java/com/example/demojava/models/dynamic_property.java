package com.example.demojava.models;

public class dynamic_property {
    public  String name,code,id;
    public int property_type = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProperty_type() {
        return property_type;
    }

    public void setProperty_type(int property_type) {
        this.property_type = property_type;
    }
}
