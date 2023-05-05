package com.example.javaav.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Restaurant {
    String name;
    int recipe;
    String address;
    ArrayList<Employees> employeesList;
    ArrayList<Customers> customersList;
    ArrayList<Meals> mealsList;
    ArrayList<Tables> tablesList;
    int capital;
    Service service;
    
    public Restaurant(String name, int recipe, String address, ArrayList<Employees> employeesList,
            ArrayList<Customers> customersList, ArrayList<Meals> mealsList, ArrayList<Tables> tablesList, int capital,
            Service service) {
        this.name = name;
        this.recipe = recipe;
        this.address = address;
        this.employeesList = employeesList;
        this.customersList = customersList;
        this.mealsList = mealsList;
        this.tablesList = tablesList;
        this.capital = capital;
        this.service = service;
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
     * @return int
     */
    public int getRecipe() {
        return recipe;
    }

    
    /** 
     * @param recipe
     */
    public void setRecipe(int recipe) {
        this.recipe = recipe;
    }

    
    /** 
     * @return String
     */
    public String getAddress() {
        return address;
    }

    
    /** 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    
    /** 
     * @return ArrayList<Employees>
     */
    public ArrayList<Employees> getEmployeesList() {
        return employeesList;
    }

    
    /** 
     * @param employeesList
     */
    public void setEmployeesList(ArrayList<Employees> employeesList) {
        this.employeesList = employeesList;
    }

    
    /** 
     * @return ArrayList<Customers>
     */
    public ArrayList<Customers> getCustomersList() {
        return customersList;
    }

    
    /** 
     * @param customersList
     */
    public void setCustomersList(ArrayList<Customers> customersList) {
        this.customersList = customersList;
    }

    
    /** 
     * @return ArrayList<Meals>
     */
    public ArrayList<Meals> getMealsList() {
        return mealsList;
    }

    
    /** 
     * @param mealsList
     */
    public void setMealsList(ArrayList<Meals> mealsList) {
        this.mealsList = mealsList;
    }

    
    /** 
     * @return ArrayList<Tables>
     */
    public ArrayList<Tables> getTablesList() {
        return tablesList;
    }

    
    /** 
     * @param tablesList
     */
    public void setTablesList(ArrayList<Tables> tablesList) {
        this.tablesList = tablesList;
    }

    
    /** 
     * @return int
     */
    public int getCapital() {
        return capital;
    }

    
    /** 
     * @param capital
     */
    public void setCapital(int capital) {
        this.capital = capital;
    }

    
    /** 
     * @return Service
     */
    public Service getService() {
        return service;
    }

    
    /** 
     * @param service
     */
    public void setService(Service service) {
        this.service = service;
    }



}
