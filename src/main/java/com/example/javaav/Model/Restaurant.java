package com.example.javaav.Model;

import java.util.ArrayList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecipe() {
        return recipe;
    }

    public void setRecipe(int recipe) {
        this.recipe = recipe;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Employees> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(ArrayList<Employees> employeesList) {
        this.employeesList = employeesList;
    }

    public ArrayList<Customers> getCustomersList() {
        return customersList;
    }

    public void setCustomersList(ArrayList<Customers> customersList) {
        this.customersList = customersList;
    }

    public ArrayList<Meals> getMealsList() {
        return mealsList;
    }

    public void setMealsList(ArrayList<Meals> mealsList) {
        this.mealsList = mealsList;
    }

    public ArrayList<Tables> getTablesList() {
        return tablesList;
    }

    public void setTablesList(ArrayList<Tables> tablesList) {
        this.tablesList = tablesList;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    
}
