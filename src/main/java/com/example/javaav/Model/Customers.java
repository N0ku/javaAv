package com.example.javaav.Model;

import java.util.ArrayList;

public class Customers extends Person{

    Tables table;
    ArrayList<Orders> orders;

    int groupId;

    public Customers(String name, String mail, String tel, int age, String adress, Tables table,int groupId) {
        super(name, mail, tel, age, adress);
        this.table = table;
        this.orders =new ArrayList<>() ;
        this.groupId = groupId;
    }

    public int getGroupId(){ return  groupId;}

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public ArrayList<Orders> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Orders> orders){
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customers [" + "name=" + getName() + "table=" + table + ", orders=" + orders + "]";
    }
    
    
}
