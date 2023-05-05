package com.example.javaav.Model;

public class Ingredients {
    int id;
    int price;
    String name;

    public Ingredients(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String toString() {
        return "intgredients{id='" + id + "', price=" + price + " ingredients '"+name+"'}";
    }
    
    /** 
     * @return int
     */
    public int getPrice() {
        return price;
    }

    
    /** 
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    
    /** 
     * @return String
     */
    public  String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }



    
    /** 
     * @return int
     */
    public  int getId() {
        return id;
    }


    
    /** 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


}
