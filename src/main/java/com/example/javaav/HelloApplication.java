package com.example.javaav;

import com.example.javaav.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;


public class HelloApplication extends Application {
    public static Restaurant restaurant;

    @Override
    public void start(Stage stage) throws IOException, ParseException {

        InitRestaurant();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RestaurantStatusView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 500);
        stage.setTitle("Restaurant");
        stage.setScene(scene);
        stage.show();
    }
    static void InitRestaurant() throws ParseException {
        Employees employe1 = new Employees(0, "Emma", "emma@mail.com", "06 72 34 56 78", 20, "Rue des Lilas", "Cuisinier", 20, 1245.67f);
        Employees employe2 = new Employees(1, "Oscar", "oscar@mail.com", "06 72 34 56 78", 23, "Rue des Lilas", "Chef", 30, 1405.11f);
        Employees employe3 = new Employees(2, "Sophie", "sophie@mail.com", "06 72 34 56 78", 40, "Rue des Lilas", "Serveur", 22, 1100.02f);
        Employees employe4 = new Employees(3, "Leo", "leo@mail.com", "06 72 34 56 78", 19, "Rue des Lilas", "Serveur", 23, 1381.93f);
        Employees employe5 = new Employees(4, "Hugo", "hugo@mail.com", "06 72 34 56 78", 30, "Rue des Lilas", "Commis", 10, 400.23f);

        ArrayList<Employees> employees = new ArrayList<>(Arrays.asList(employe1, employe2, employe3, employe4, employe5));

        Customers customers1 = new Customers(0, "Alice", "alice@example.com", "06 12 34 56 78", 28, "1 rue de la Liberté", null, null);
        Customers customers2 = new Customers(1, "Bob", "bob@example.com", "06 98 76 54 32", 35, "10 avenue des Fleurs", null, null);
        Customers customers3 = new Customers(2, "Charlie", "charlie@example.com", "06 44 88 12 16", 42, "22 rue du Château", null, null);
        Customers customers4 = new Customers(3, "David", "david@example.com", "06 55 33 77 99", 20, "5 boulevard du Parc", null, null);
        Customers customers5 = new Customers(4, "Emma", "emma@example.com", "06 66 99 11 22", 50, "3 rue des Écoles", null, null);

        ArrayList<Customers> customersFree = new ArrayList<>(Arrays.asList(customers1, customers2, customers3, customers4, customers5));

        ArrayList<Customers> customers = new ArrayList<>();

        Tables table1 = new Tables(0, 5, "Terrasse", true, customers);
        Tables table2 = new Tables(1, 8, "Terrasse", true, customers);
        Tables table3 = new Tables(2, 2, "Terrasse", true, customers);
        Tables table4 = new Tables(3, 1, "Terrasse", true, customers);
        Tables table5 = new Tables(4, 6, "Terrasse", true, customers);

        ArrayList<Tables> tables = new ArrayList<>(Arrays.asList(table1, table2, table3, table4, table5));

        DateFormat format = new SimpleDateFormat("HH:mm");
        Date serviceStart = format.parse("12:00");
        Date serviceEnd = format.parse("14:00");

        Service service = new Service(serviceStart, serviceEnd, true);

        ArrayList<String> allergies = new ArrayList<>();

// Object 1
        ArrayList<Ingredients> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredients(0, 10, "Tomatoes", allergies));
        ingredients1.add(new Ingredients(1, 20, "Cheese", allergies));
        Meals meal1 = new Meals("Margherita", "https://imgurl1.com", 10.5f, 50, "Classic tomato and cheese pizza", false, 2.5f, ingredients1);

// Object 2
        ArrayList<Ingredients> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredients(2, 20, "Pepperoni", allergies));
        ingredients2.add(new Ingredients(3, 5, "Onions", allergies));
        Meals meal2 = new Meals("Pepperoni", "https://imgurl2.com", 12.5f, 35, "Pizza with pepperoni and onions", false, 3.0f, ingredients2);

// Object 3
        ArrayList<Ingredients> ingredients3 = new ArrayList<>();
        ingredients3.add(new Ingredients(4, 15, "Chicken", allergies));
        ingredients3.add(new Ingredients(5, 10, "Mushrooms", allergies));
        Meals meal3 = new Meals("Chicken Mushroom", "https://imgurl3.com", 14.5f, 20, "Pizza with chicken and mushrooms", false, 3.5f, ingredients3);

// Object 4
        ArrayList<Ingredients> ingredients4 = new ArrayList<>();
        ingredients4.add(new Ingredients(6, 10, "Ham", allergies));
        ingredients4.add(new Ingredients(7, 15, "Pineapple", allergies));
        Meals meal4 = new Meals("Hawaiian", "https://imgurl4.com", 13.5f, 25, "Pizza with ham and pineapple", false, 3.0f, ingredients4);

// Object 5
        ArrayList<Ingredients> ingredients5 = new ArrayList<>();
        ingredients5.add(new Ingredients(8, 20, "Bacon", allergies));
        ingredients5.add(new Ingredients(9, 30, "Beef", allergies));
        Meals meal5 = new Meals("Meat Lovers", "https://imgurl5.com", 16.5f, 40, "Pizza with bacon and beef", false, 4.0f, ingredients5);
        ArrayList<Meals> meals = new ArrayList<>(Arrays.asList(meal1, meal2, meal3, meal4, meal5));

        restaurant = new Restaurant("El Resto", 0, "12 rue des Pigeons, 75002 Paris", employees, customersFree, meals, tables, 1000, service);
    }

    public static void main(String[] args) {
        launch();
    }
}