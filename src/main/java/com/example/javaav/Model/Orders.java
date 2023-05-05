package com.example.javaav.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Orders {
    private  UUID id;
    ArrayList<Meals> mealList;
    double totalPrice;
    Date hour;
    String status ;

    public Orders(ArrayList<Meals> mealList,double totalPrice ,Date hour) {
        this.id = UUID.randomUUID();
        this.mealList = mealList;
        this.totalPrice = totalPrice;
        this.hour = hour;
        this.status= "pending";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus() {
        return  this.status;
    }
}
