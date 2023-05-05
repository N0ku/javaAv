package com.example.javaav.Model;

import java.util.ArrayList;

public class Customers extends Person{

    ArrayList<Orders> orders;

    int groupId;
    int numberTable;

    public Customers(String name, String mail, String tel, int age, String adress,int groupId) {
        super(name, mail, tel, age, adress);
        this.orders =new ArrayList<>() ;
        this.groupId = groupId;
        this.numberTable = 0;
    }

    
    /** 
     * @return int
     */
    public int getNumberTable() {
        return numberTable;
    }

    
    /** 
     * @param numberTable
     */
    public void setNumberTable(int numberTable) {
        this.numberTable = numberTable;
    }

    public int getGroupId(){ return  groupId;}

    
    /** 
     * @return ArrayList<Orders>
     */
    public ArrayList<Orders> getOrders() {
        return orders;
    }

    
    /** 
     * @param orders
     */
    public void setOrders(ArrayList<Orders> orders){
        this.orders = orders;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "Customers{"+"id="+getId()+", name="+ getName()+", tel="+getTel()+", age="+getAge()+ ", adress="+getAdress()+
                ", orders=" + orders +
                ", groupId=" + groupId +
                '}';
    }
}
