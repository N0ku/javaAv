package com.example.javaav.Model;

import java.util.ArrayList;

public class Ingredients {
    int id;
    int price;
    String name;

    public Ingredients(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

}
