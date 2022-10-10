package com.example.listview;

public class Data {
    private String name;
    private String id;
    private String token;


    public Data(String name, String id,String token) {
        this.name = name;
        this.id = id;
        this.token=token;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }
}