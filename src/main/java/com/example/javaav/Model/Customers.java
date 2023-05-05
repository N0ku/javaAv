package com.example.javaav.Model;

import java.util.ArrayList;

public class Customers extends Person{

    ArrayList<Orders> orders;

    int groupId;

    public Customers(String name, String mail, String tel, int age, String adress,int groupId) {
        super(name, mail, tel, age, adress);
        this.orders =new ArrayList<>() ;
        this.groupId = groupId;
    }

    public int getGroupId(){ return  groupId;}

    public ArrayList<Orders> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Orders> orders){
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customers{"+"id="+getId()+", name="+ getName()+", tel="+getTel()+", age="+getAge()+ ", adress="+getAdress()+
                ", orders=" + orders +
                ", groupId=" + groupId +
                '}';
    }
}
