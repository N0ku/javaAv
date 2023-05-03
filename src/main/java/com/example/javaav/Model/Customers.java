package com.example.javaav.Model;

public class Customers extends Person{

    Tables table;
    Orders orders;

    int groupId;

    public Customers(int id,String name, String mail, String tel, int age, String adress, Tables table, Orders orders,int groupId) {
        super(id,name, mail, tel, age, adress);
        this.table = table;
        this.orders = orders;
        this.groupId = groupId;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customers [" + "name=" + getName() + "table=" + table + ", orders=" + orders + "]";
    }
    
    
}