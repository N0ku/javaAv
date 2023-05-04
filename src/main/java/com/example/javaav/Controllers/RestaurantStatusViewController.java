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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    private ListView<Customers> customersList;

    @FXML
    private ListView<Tables> tablesList;

    @FXML
    private Label chronoLabel;

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

        Date serviceStartHour = restaurant.getService().getServiceStart();
        Date serviceEndHour = restaurant.getService().getServiceEnd();

        long diffMillis = Math.abs(serviceEndHour.getTime() - serviceStartHour.getTime());
        Duration duration = Duration.ofMillis(diffMillis);
        final int[] seconds = {(int) duration.toSeconds()};
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (seconds[0] > 0) {
                    String value = computeChronoValue();
                    Platform.runLater(() -> chronoLabel.setText(value));
                    Thread.sleep(1000);
                }
                return null;
            }

            private String computeChronoValue() {
                int remainingSeconds = seconds[0];
                seconds[0] = remainingSeconds - 1;

                // 15mins into seconds
                if (seconds[0] < 900){
                    restaurant.getService().setRunning(false);
                }
                int minutes = remainingSeconds / 60;
                int seconds = remainingSeconds % 60;
                String value = String.format("%02d:%02d", minutes, seconds);
                if (remainingSeconds <= 0) {
                    value = "00:00";
                    cancel();
                }
                return value;
            }
        };


        new Thread(task).start();

        customersList.setOnMouseClicked(event -> {
            if(!restaurant.getService().isRunning()){return;}
            Customers customer = customersList.getSelectionModel().getSelectedItem();

            ArrayList<Customers> customersToAdd = customers.stream()
                    .filter(c -> c.getGroupId() == customer.getGroupId())
                    .collect(Collectors.toCollection(ArrayList::new));

            // find the first free table and where all the customers can sit
            Tables table = tables.stream().filter(t -> t.isFree() && t.getSize() > customersToAdd.size()).findFirst().orElse(null);

            if (table != null) {
                table.setCustomers(customersToAdd);

                customersToAdd.forEach(c -> {
                    customersList.getItems().remove(c);
                });

                int indexToUpdate = tablesList.getItems().indexOf(table);

                table.setCustomers(customersToAdd);
                table.setFree(false);
                tablesList.getItems().set(indexToUpdate, table);
            } else {
                System.out.println("Aucune table de dispo !!");
            }
        });

        tablesList.setOnMouseClicked(event -> {
            Tables table = tablesList.getSelectionModel().getSelectedItem();

            ArrayList<Customers> customersTable = new ArrayList<>(table.getCustomers());

            System.out.println(customersTable.size());

            if (customersTable.size() >= 1) {
                customersTable.forEach(c -> {
                    customersList.getItems().add(c);
                });
                customersTable.clear();
                int indexToUpdate = tablesList.getItems().indexOf(table);
                table.setCustomers(customersTable);
                table.setFree(true);
                tablesList.getItems().set(indexToUpdate, table);
            }
        });

    }
}
