package com.example.javaav.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Orders {
    private final UUID id;
    ArrayList<Meals> mealList;
    double totalPrice;
    Date hour;
    String status ;

    public Orders(ArrayList<Meals> mealList,double totalPrice) {
        this.id = UUID.randomUUID();
        this.mealList = mealList;
        this.totalPrice = totalPrice;
        this.hour = new Date();
        this.status= "pending";
    }

    public UUID getId() {
        return id;
    }
    public ArrayList<Meals> getMealList() {
        return mealList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getHour() {
        return hour;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", mealList=" + mealList +
                ", totalPrice=" + totalPrice +
                ", hour=" + hour +
                ", status='" + status + '\'' +
                '}';
    }
}
