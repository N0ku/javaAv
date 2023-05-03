package com.example.javaav.Model;

import java.util.ArrayList;

public class Ingredients {
    int id;
    int price;
    String name;
    ArrayList<String> allergies;

    public Ingredients(int id, int price, String name, ArrayList<String> allergies) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.allergies = allergies;
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

    public ArrayList<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(ArrayList<String> allergies) {
        this.allergies = allergies;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

}
