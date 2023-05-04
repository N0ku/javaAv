package com.example.javaav.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Orders {
    private final UUID id;
    ArrayList<Meals> mealList;
    boolean delivered;
    int totalPrice;
    Date hour;
    boolean isFavorite;
    String status ;

    public Orders(ArrayList<Meals> mealList,int totalPrice) {
        this.id = UUID.randomUUID();
        this.mealList = mealList;
        this.delivered = false;
        this.totalPrice = totalPrice;
        this.hour = new Date();
        this.isFavorite=false;
        this.status= "";
    }

    public UUID getId() {
        return id;
    }
    public ArrayList<Meals> getMealList() {
        return mealList;
    }

    public void setMealList(ArrayList<Meals> mealList) {
        this.mealList = mealList;
    }

    public boolean isDelivred() {
        return delivered;
    }

    public void setDelivred(boolean delivred) {
        this.delivered = delivred;
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
        return "Orders [mealList=" + mealList + ", delivred=" + delivered + ", totalPrice=" + totalPrice + ", hour="
                + hour + "]";
    }


}
