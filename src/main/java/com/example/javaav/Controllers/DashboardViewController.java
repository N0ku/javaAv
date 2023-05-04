package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {

    @FXML
    private ListView<Employees> EmployeesList;

    @FXML
    private ListView<Meals> MealList;

    @FXML
    private Button backButton;

    @FXML
    private ListView<Orders> lastOrdersList;

    @FXML
    private Label lowPrice;

    @FXML
    private Label chronoLabel;

    @FXML
    private ListView<Orders> ordersWaitingList;

    @FXML
    private ListView<?> priceCustomersLeave;

    @FXML
    private ListView<?> priceCustomersPresent;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label topPrice;

    @FXML
    private Label totalPrice;

    @FXML
    private Button gestionButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Restaurant restaurant = HelloApplication.restaurant;

        backButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/HomeView.fxml"))));
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

        ChronoThread chrono = new ChronoThread(chronoLabel,restaurant);
        chrono.start();

        // Let's have some fun with streams

       //TODO restaurant.getCustomersList().stream().filter(customer -> customer.getOrders().getStatus() = "delivred"); // add for each after

      //TODO ArrayList<Customers> customersWaiting =  restaurant.getCustomersList().stream().filter(customer -> customer.getOrders().getStatus() = "pending");

        
    }
}
