package com.example.javaav.Utils;

import com.example.javaav.MainApplication;
import com.example.javaav.Model.Restaurant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestaurantToJsonConverter {

    public static JSONObject toJson() {
Restaurant restaurant = MainApplication.restaurant;
        JSONObject jsonRestaurant = new JSONObject();

        jsonRestaurant.put("name", restaurant.getName());
        jsonRestaurant.put("recipe", restaurant.getRecipe());
        jsonRestaurant.put("address", restaurant.getAddress());
        jsonRestaurant.put("capital", restaurant.getCapital());

        // Ajouter la liste des employés
        JSONArray jsonEmployees = new JSONArray();
        restaurant.getEmployeesList().stream().forEach(employee -> {
            JSONObject jsonEmployee = new JSONObject();
            jsonEmployee.put("jobName", employee.getJobName());
            jsonEmployee.put("mail", employee.getMail());
            jsonEmployee.put("workHours", employee.getWorkHours());
            jsonEmployee.put("name", employee.getName());
            jsonEmployee.put("tel", employee.getTel());
            jsonEmployee.put("adress", employee.getAdress());
            jsonEmployee.put("id",employee.getId());
            jsonEmployee.put("salary", employee.getSalary());
            jsonEmployee.put("age", employee.getAge());

            jsonEmployees.put(jsonEmployee);
        });
        jsonRestaurant.put("employees", jsonEmployees);

        // Ajouter la liste des clients
        JSONArray jsonCustomers = new JSONArray();
        restaurant.getCustomersList().stream().forEach(customer -> {
            JSONObject jsonCustomer = new JSONObject();
            jsonCustomer.put("name", customer.getName());
            jsonCustomer.put("mail", customer.getMail());
            jsonCustomer.put("tel", customer.getTel());
            jsonCustomer.put("age", customer.getAge());
            jsonCustomer.put("adress", customer.getAdress());
            //Order pour la liste de client
            JSONArray jsonOrders = new JSONArray();
            customer.getOrders().forEach(order -> {
                JSONObject jsonOrder = new JSONObject();
                jsonOrder.put("id", order.getId());
                JSONArray jsonMeals = new JSONArray();
                order.getMealList().stream().forEach(meal -> {
                    JSONObject jsonMeal = new JSONObject();
                    jsonMeal.put("name", meal.getName());
                    jsonMeal.put("imgUrl", meal.getImgUrl());
                    jsonMeal.put("price", meal.getPrice());
                    jsonMeal.put("nbOrder", meal.getNbOrder());
                    jsonMeal.put("desc", meal.getDesc());
                    jsonMeal.put("marge", meal.getMarge());
                    jsonMeal.put("quantity", meal.getQuantity());
                    JSONArray jsonIngredientsMeals = new JSONArray();
                    meal.getIngredients().stream().forEach(ingredient -> {
                        JSONObject jsonIngredient = new JSONObject();
                        jsonIngredient.put("id", ingredient.getId());
                        jsonIngredient.put("price", ingredient.getPrice());
                        jsonIngredient.put("name", ingredient.getName());
                        jsonIngredientsMeals.put(jsonIngredient);
                    });
                    jsonMeal.put("ingregients",jsonIngredientsMeals);
                    jsonMeals.put(jsonMeal);
                });
                jsonOrder.put("mealList", jsonMeals);
                jsonOrder.put("totalPrice", order.getTotalPrice());
                jsonOrder.put("hour", order.getHour());
                jsonOrder.put("status", order.getStatus());
                jsonOrders.put(jsonOrder);
            });
            jsonCustomer.put("orders", jsonOrders);
            jsonCustomer.put("groupId", customer.getGroupId());
            jsonCustomer.put("numberTable", customer.getNumberTable());
            jsonCustomers.put(jsonCustomer);
        });
        jsonRestaurant.put("customersList", jsonCustomers);


        // Ajouter la liste des plats
        JSONArray jsonMeals = new JSONArray();
        restaurant.getMealsList().stream().forEach(meal -> {
            JSONObject jsonMeal = new JSONObject();
            jsonMeal.put("name", meal.getName());
            jsonMeal.put("imgUrl", meal.getImgUrl());
            jsonMeal.put("price", meal.getPrice());
            jsonMeal.put("nbOrder", meal.getNbOrder());
            jsonMeal.put("desc", meal.getDesc());
            jsonMeal.put("marge", meal.getMarge());
            JSONArray jsonIngredients = new JSONArray();
            meal.getIngredients().stream().forEach(ingredient -> {
                JSONObject jsonIngredient = new JSONObject();
                jsonIngredient.put("id", ingredient.getId());
                jsonIngredient.put("price", ingredient.getPrice());
                jsonIngredient.put("name", ingredient.getName());
                jsonIngredients.put(jsonIngredient);
            });
            jsonMeal.put("ingredients",jsonIngredients);
            jsonMeals.put(jsonMeal);
        });
        jsonRestaurant.put("mealsList", jsonMeals);

        // Ajouter la liste des tables
        JSONArray jsonTables = new JSONArray();
        ArrayList<String> list = new ArrayList<>();

        restaurant.getTablesList().stream().forEach(table -> {
            JSONObject jsonTable = new JSONObject();
            jsonTable.put("tableNumber", table.getTableNumber());
            jsonTable.put("size", table.getSize());
            jsonTable.put("place", table.getPlace());
            jsonTable.put("isFree", true);
            jsonTable.put("customer",list);
            jsonTables.put(jsonTable);
        });
        jsonRestaurant.put("tablesList", jsonTables);

        // Ajouter le service
        JSONObject jsonService = new JSONObject();
        jsonService.put("isRunning", restaurant.getService().isRunning());
        jsonService.put("startTime", restaurant.getService().getServiceStart());
        jsonService.put("endTime", restaurant.getService().getServiceEnd());
        jsonService.put("seconds",restaurant.getService().getSeconds());
        jsonRestaurant.put("service", jsonService);

        return jsonRestaurant;
    }

}
