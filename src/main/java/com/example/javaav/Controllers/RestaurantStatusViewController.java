package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RestaurantStatusViewController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Label chronoLabel;

    @FXML
    private Button createEmployeeButton;

    @FXML
    private Button createMenuButton;

    @FXML
    private Button createOrderButton;

    @FXML
    private ListView<Customers> customersList;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button employeeListButton;

    @FXML
    private Button menuListButton;

    @FXML
    private Button ordersListButton;

    @FXML
    private AnchorPane root;

    @FXML
    private ListView<Tables> tablesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        backButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/HomeView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        createOrderButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/CreateOrderView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Restaurant restaurant = HelloApplication.restaurant;

        ChronoThread chrono = new ChronoThread(chronoLabel, restaurant);
        chrono.start();

        final ArrayList<Customers>[] customers = new ArrayList[]{restaurant.getCustomersList().stream().filter(c -> c.getTable() == null).collect(Collectors.toCollection(ArrayList::new))};
        final ArrayList<Tables>[] tables = new ArrayList[]{restaurant.getTablesList()};

        customersList.getItems().addAll(customers[0]);

        customersList.setCellFactory(customer -> new CellCustomers());

        tables[0].forEach(
                table -> {
                    tablesList.getItems().add(table);
                }
        );

        tablesList.setCellFactory(table -> new CellTables());

        customersList.setOnMouseClicked(event -> {
            if (!restaurant.getService().isRunning()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erreur !");
                alert.setHeaderText("Le restaurant va bientôt fermer !");
                alert.setContentText("Désolé mais le restaurant ne prend plus les clients lorsqu'il reste moins de 15 minutes de service");
                alert.show();
                return;
            }
            Customers customer = customersList.getSelectionModel().getSelectedItem();

            ArrayList<Customers> customersToAdd = customers[0].stream()
                    .filter(c -> c.getGroupId() == customer.getGroupId())
                    .collect(Collectors.toCollection(ArrayList::new));

            // find the first free table and where all the customers can sit
            Tables table = tables[0].stream().filter(t -> t.isFree() && t.getSize() >= customersToAdd.size()).findFirst().orElse(null);

            if (table != null) {
                table.setCustomers(customersToAdd);

                customersToAdd.forEach(c -> {
                    customersList.getItems().remove(c);
                    c.setTable(table);
                    int indexCustomer = restaurant.getCustomersList().indexOf(c);
                    ArrayList<Customers> customersToUpdate = restaurant.getCustomersList();
                    customersToUpdate.set(indexCustomer, c);
                    restaurant.setCustomersList(customersToUpdate);
                    customers[0] = restaurant.getCustomersList();
                });

                int indexToUpdate = tablesList.getItems().indexOf(table);
                table.setCustomers(customersToAdd);
                table.setFree(false);
                tablesList.getItems().set(indexToUpdate, table);
                tables[0] = restaurant.getTablesList();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Complet !");
                alert.setHeaderText("Aucune table n'est disponible actuellement");
                alert.setContentText("Il n'y a pas asser de place sur toutes les tables et/ou toutes les tables sont déjà prises");
                alert.show();
            }
        });

        tablesList.setOnMouseClicked(event -> {
            Tables table = tablesList.getSelectionModel().getSelectedItem();

            ArrayList<Customers> customersTable = new ArrayList<>(table.getCustomers());

            if (customersTable.size() >= 1) {
                customersTable.forEach(c -> {
                    c.setTable(null);
                    //ArrayList<Orders> o = new ArrayList<>();
                   // c.setOrders(o);
                    int indexCustomer = restaurant.getCustomersList().indexOf(c);
                    ArrayList<Customers> customersToUpdate = restaurant.getCustomersList();
                    customersToUpdate.set(indexCustomer, c);
                    restaurant.setCustomersList(customersToUpdate);
                    customersList.getItems().add(c);
                });
                customersTable.clear();
                customers[0] = restaurant.getCustomersList();
                int indexToUpdate = tablesList.getItems().indexOf(table);
                table.setCustomers(customersTable);
                table.setFree(true);
                tablesList.getItems().set(indexToUpdate, table);
                tables[0] = restaurant.getTablesList();
            }
        });

        dashboardButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/DashboardView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ordersListButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/OrderDisplayView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        employeeListButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/DisplayEmployeeView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        createEmployeeButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/CreationEmployeeView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        createMenuButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/MenuCreationView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menuListButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/MenuDisplayView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        // clear listview selection
        root.setOnMouseClicked(event -> {
            if (!customersList.getBoundsInParent().contains(event.getX(), event.getY())) {
                customersList.getSelectionModel().clearSelection();
            }
            if (!tablesList.getBoundsInParent().contains(event.getX(), event.getY())) {
                tablesList.getSelectionModel().clearSelection();
            }
        });

    }
}
