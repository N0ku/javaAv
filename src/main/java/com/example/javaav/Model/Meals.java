package com.example.javaav.Model;

import java.util.ArrayList;

public class Meals {
    String name;
    String imgUrl;
    float price;
    int nbOrder;
    String desc;
    float marge;
    int quantity;
    ArrayList<Ingredients> ingredients;

    public Meals(String name, String imgUrl, float price, int nbOrder, String desc,float marge,
                 ArrayList<Ingredients> ingredients) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.nbOrder = nbOrder;
        this.desc = desc;
        this.marge = marge;
        this.ingredients = ingredients;
        this.quantity=0;
    }
    
    /** 
     * @return String
     */
    public String toString() {
        return "Meal{name='" + name + "', price=" + price + "ingredients'"+ingredients;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getImgUrl() {
        return imgUrl;
    }

    
    /** 
     * @param imgUrl
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    
    /** 
     * @return float
     */
    public float getPrice() {
        return price;
    }

    
    /** 
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    
    /** 
     * @return int
     */
    public int getNbOrder() {
        return nbOrder;
    }

    
    /** 
     * @param nbOrder
     */
    public void setNbOrder(int nbOrder) {
        this.nbOrder = nbOrder;
    }

    
    /** 
     * @return String
     */
    public String getDesc() {
        return desc;
    }

    
    /** 
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }


    
    /** 
     * @return float
     */
    public float getMarge() {
        return marge;
    }

    
    /** 
     * @param marge
     */
    public void setMarge(float marge) {
        this.marge = marge;
    }

    
    /** 
     * @return ArrayList<Ingredients>
     */
    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    
    /** 
     * @param ingredients
     */
    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    
    /** 
     * @return int
     */
    public int getQuantity() {
        return quantity;
    }

    
    /** 
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
