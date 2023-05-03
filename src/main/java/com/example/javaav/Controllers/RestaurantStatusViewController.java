package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.Customers;
import com.example.javaav.Model.Restaurant;
import com.example.javaav.Model.Tables;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class RestaurantStatusViewController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> customersList;

    @FXML
    private ListView<String> tablesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        backButton.setOnMouseClicked(event ->{
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/DashboardView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Restaurant restaurant = HelloApplication.restaurant;

        ArrayList<Customers> customers = restaurant.getCustomersList();
        ArrayList<Tables> tables = restaurant.getTablesList();

        customers.forEach(customer -> {
            customersList.getItems().add(customer.getName());
        });

        tables.forEach(
                table ->{
                    tablesList.getItems().add("Table: " +  table.getTableNumber());
                }
        );



    }
}
