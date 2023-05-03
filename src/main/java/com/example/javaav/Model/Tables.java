package com.example.javaav.Model;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Tables {
    int tableNumber;
    int size;
    String place;
    boolean isFree;
    ArrayList<Customers> customers;

    public Tables(int tableNumber, int size, String place, boolean isFree, ArrayList<Customers> customers) {
        this.tableNumber = tableNumber;
        this.size = size;
        this.place = place;
        this.isFree = isFree;
        this.customers = customers;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public ArrayList<Customers> getCustomers() {
        return customers;
    }

    public String getCustomersToString(){
        StringJoiner joiner = new StringJoiner(", ");
        this.customers.forEach(c -> joiner.add(c.getName()));
        return joiner.toString();
    }

    public void setCustomers(ArrayList<Customers> customers) {
        this.customers = customers;
    }

}
