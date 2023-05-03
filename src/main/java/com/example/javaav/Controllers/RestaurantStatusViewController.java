package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RestaurantStatusViewController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Customers> customersList;

    @FXML
    private ListView<Tables> tablesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        backButton.setOnMouseClicked(event -> {
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
            customersList.getItems().add(customer);
        });

        customersList.setCellFactory(customer -> new CellCustomers());

        tables.forEach(
                table -> {
                    tablesList.getItems().add(table);
                }
        );

        tablesList.setCellFactory(table -> new CellTables());

        customersList.setOnMouseClicked(event -> {
            Customers customer = customersList.getSelectionModel().getSelectedItem();

            ArrayList<Customers> customersToAdd = customers.stream()
                    .filter(c -> c.getGroupId() == customer.getGroupId())
                    .collect(Collectors.toCollection(ArrayList::new));

            // find the first free table and where all the customers can sit
            Tables table = tables.stream().filter(t -> !t.isFree() && t.getSize() > customersToAdd.size()).findFirst().orElse(null);


        });


    }
}
