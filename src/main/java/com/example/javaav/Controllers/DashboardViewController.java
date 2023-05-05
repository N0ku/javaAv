package com.example.javaav.Controllers;

import com.example.javaav.MainApplication;
import com.example.javaav.Model.*;
import com.example.javaav.Model.Cells.CellEmployees;
import com.example.javaav.Model.Cells.CellMeals;
import com.example.javaav.Model.Cells.CellOrders;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardViewController implements Initializable {


    @FXML
    private ListView<Employees> EmployeesListl30;

    @FXML
    private ListView<Employees> EmployeesListl45;

    @FXML
    private ListView<Employees> EmployeesListm30;

    @FXML
    private ListView<Employees> EmployeesListm45;

    @FXML
    private ListView<Meals> MealList;

    @FXML
    private Button backButton;

    @FXML
    private Label chronoLabel;

    @FXML
    private Label factl;

    @FXML
    private Label factp;

    @FXML
    private Button gestionButton;

    @FXML
    private Label lowPrice;

    @FXML
    private ListView<Orders> ordersWaitingList;

    @FXML
    private ListView<Orders> lastOrdersList;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label topPrice;

    @FXML
    private Label totalPrice;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Restaurant restaurant = MainApplication.restaurant;

        backButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/RestaurantStatusView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        gestionButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/RestaurantStatusView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        gestionButton.getStyleClass().add("button-style nav");
        backButton.getStyleClass().add("button-style nav");
        searchButton.getStyleClass().add("search-style");

        searchButton.setOnMouseClicked(event -> {
            String searchContent = searchField.getText();

            if (searchContent.isBlank() || searchContent.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Recherche vide");
                alert.setHeaderText("Error");
                alert.setContentText("Le champs de recherche ne peut pas être vide");
                alert.show();
            } else {
                ArrayList<Meals> mealsFilter = restaurant.getMealsList().stream()
                        .filter(meal -> meal.getIngredients().stream()
                                .anyMatch(ingredient -> ingredient.getName().equals(searchContent))).
                        collect(Collectors.toCollection(ArrayList::new));
                MealList.getItems().removeAll(restaurant.getMealsList());
                MealList.getItems().addAll(mealsFilter);
            }
        });

        MealList.getItems().addAll(restaurant.getMealsList());
        MealList.setCellFactory(customer -> new CellMeals());

        ChronoThread chrono = new ChronoThread(chronoLabel, restaurant);
        chrono.start();

        // Let's have some fun with streams

        ArrayList<Orders> ordersWaiting = restaurant.getCustomersList().stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getStatus().equals("pending"))
                .sorted(Comparator.comparing(Orders::getHour).reversed())
                .collect(Collectors.toCollection(ArrayList::new));


        ArrayList<Orders> ordersDelivred = restaurant.getCustomersList().stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getStatus().equals("delivered")).limit(5)
                .sorted(Comparator.comparing(Orders::getHour).reversed())
                .collect(Collectors.toCollection(ArrayList::new));


        ArrayList<Employees> more30 = restaurant.getEmployeesList().stream().filter(e -> e.getAge() >= 30).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Employees> less30 = restaurant.getEmployeesList().stream().filter(e -> e.getAge() < 30).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Employees> more45 = restaurant.getEmployeesList().stream().filter(e -> e.getAge() >= 45).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Employees> less45 = restaurant.getEmployeesList().stream().filter(e -> e.getAge() < 45).collect(Collectors.toCollection(ArrayList::new));

        EmployeesListl30.getItems().addAll(less30);
        EmployeesListl45.getItems().addAll(less45);
        EmployeesListm30.getItems().addAll(more30);
        EmployeesListm45.getItems().addAll(more45);

        EmployeesListl30.setCellFactory(e -> new CellEmployees());
        EmployeesListm30.setCellFactory(e -> new CellEmployees());
        EmployeesListl45.setCellFactory(e -> new CellEmployees());
        EmployeesListm45.setCellFactory(e -> new CellEmployees());


       ordersWaitingList.getItems().addAll(ordersWaiting);
        ordersWaitingList.setCellFactory(o -> new CellOrders());

        lastOrdersList.getItems().addAll(ordersDelivred);
        lastOrdersList.setCellFactory(o -> new CellOrders());
        System.out.println(ordersDelivred);

        int totalMoneyCP = restaurant.getCustomersList().stream()
                .filter(c -> c.getGroupId() != 0)
                .flatMap(customer -> customer.getOrders().stream())
                .reduce(0, (result, order) -> (int) (result + order.getTotalPrice()), Integer::sum);
        factp.setText(totalMoneyCP + " €");

        int totalMoneyCL = restaurant.getCustomersList().stream()
                .filter(c -> c.getGroupId() == 0)
                .flatMap(customer -> customer.getOrders().stream())
                .reduce(0, (result, order) -> (int) (result + order.getTotalPrice()), Integer::sum);
        factl.setText(totalMoneyCL + " €");


        int totalMealsPrice = restaurant.getMealsList().stream().reduce(0,(result,meal) -> (int) (result + meal.getPrice()),Integer::sum);
        totalPrice.setText(totalMealsPrice + " €");

        Meals mealWithHighestPrice = restaurant.getMealsList().stream()
                .max(Comparator.comparing(Meals::getPrice))
                .orElse(null);

        assert mealWithHighestPrice != null;
        topPrice.setText("Name: " + mealWithHighestPrice.getName() + " Price: " +  mealWithHighestPrice.getPrice() +  " €");

        Meals mealWithLowestPrice = restaurant.getMealsList().stream()
                .min(Comparator.comparing(Meals::getPrice))
                .orElse(null);

        assert mealWithLowestPrice != null;
        lowPrice.setText("Name: " + mealWithLowestPrice.getName() + " Price: " + mealWithLowestPrice.getPrice() + " €");

    }
}
