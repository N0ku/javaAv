package com.example.javaav.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringJoiner;
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

    
    /** 
     * @return UUID
     */
    public UUID getId() {
        return id;
    }

    
    /** 
     * @param id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    
    /** 
     * @return ArrayList<Meals>
     */
    public ArrayList<Meals> getMealList() {
        return mealList;
    }

    
    /** 
     * @return double
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    
    /** 
     * @return Date
     */
    public Date getHour() {
        return hour;
    }

    
    /** 
     * @return String
     */
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

    
    /** 
     * @param status
     */
    public void setStatus(String status){
        this.status = status;
    }
    
    /** 
     * @return String
     */
    public String getStatus() {
        return  this.status;
    }

    
    /** 
     * @return String
     */
    public String getMealListToString() {
        StringJoiner joiner = new StringJoiner(" ");
        this.mealList.forEach(m -> {
                joiner.add(m.getName());
        });
        return joiner.toString();
    }
}
