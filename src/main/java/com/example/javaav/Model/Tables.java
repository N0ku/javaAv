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
        if(this.customers != null) {
            StringJoiner joiner = new StringJoiner(" ");
            this.customers.forEach(c -> {
                if (!c.getOrders().isEmpty()) {
                    joiner.add(c.getName());
                    ArrayList<Orders> customerOrders = c.getOrders();
                    joiner.add("  | Commande: ");
                    customerOrders.forEach(o -> {
                        o.getMealList().forEach(m -> {
                            joiner.add(m.getName() + ", ");
                        });
                    });
                    joiner.add("\n");
                } else {
                    joiner.add(c.getName() + "\n"); // add a line break after the customer's name
                }
            });
            return joiner.toString();
        }
        else{
            return "";
        }
    }


    public void setCustomers(ArrayList<Customers> customers) {
        this.customers = customers;
    }

}
