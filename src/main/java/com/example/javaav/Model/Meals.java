package com.example.javaav.Model;

import java.util.ArrayList;

public class Meals {
    String name;
    String imgUrl;
    float price;
    int nbOrder;
    String desc;
    boolean isCancel;
    float marge;
    int quantity;
    ArrayList<Ingredients> ingredients;

    public Meals(String name, String imgUrl, float price, int nbOrder, String desc, float marge,
            ArrayList<Ingredients> ingredients) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.nbOrder = nbOrder;
        this.desc = desc;
        this.isCancel = false;
        this.marge = marge;
        this.ingredients = ingredients;
        this.quantity=0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNbOrder() {
        return nbOrder;
    }

    public void setNbOrder(int nbOrder) {
        this.nbOrder = nbOrder;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    public float getMarge() {
        return marge;
    }

    public void setMarge(float marge) {
        this.marge = marge;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
