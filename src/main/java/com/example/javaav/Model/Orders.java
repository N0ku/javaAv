package com.example.javaav.Model;

import java.util.ArrayList;
import java.util.Date;

public class Orders {
    int id;
    ArrayList<Meals> mealList;
    boolean delivred;
    int totalPrice;
    Date hour;

    public Orders(int id, ArrayList<Meals> mealList, boolean delivred, int totalPrice, Date hour) {
        this.id = id;
        this.mealList = mealList;
        this.delivred = delivred;
        this.totalPrice = totalPrice;
        this.hour = hour;
    }

    public ArrayList<Meals> getMealList() {
        return mealList;
    }

    public void setMealList(ArrayList<Meals> mealList) {
        this.mealList = mealList;
    }

    public boolean isDelivred() {
        return delivred;
    }

    public void setDelivred(boolean delivred) {
        this.delivred = delivred;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Orders [mealList=" + mealList + ", delivred=" + delivred + ", totalPrice=" + totalPrice + ", hour="
                + hour + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}
