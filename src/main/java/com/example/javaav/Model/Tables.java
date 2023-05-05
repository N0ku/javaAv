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

    
    /** 
     * @return int
     */
    public int getTableNumber() {
        return tableNumber;
    }

    
    /** 
     * @param tableNumber
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    
    /** 
     * @return int
     */
    public int getSize() {
        return size;
    }

    
    /** 
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    
    /** 
     * @return String
     */
    public String getPlace() {
        return place;
    }

    
    /** 
     * @param place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    
    /** 
     * @return boolean
     */
    public boolean isFree() {
        return isFree;
    }

    
    /** 
     * @param isFree
     */
    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    
    /** 
     * @return ArrayList<Customers>
     */
    public ArrayList<Customers> getCustomers() {
        return customers;
    }

    
    /** 
     * @return String
     */
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


    
    /** 
     * @param customers
     */
    public void setCustomers(ArrayList<Customers> customers) {
        this.customers = customers;
    }

}
