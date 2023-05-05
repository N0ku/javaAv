package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.Customers;
import com.example.javaav.Model.Meals;
import com.example.javaav.Model.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrderDisplayViewController implements Initializable {

    @FXML
    private ListView<Orders> orderListView;

    @FXML
    private Button buttonReturn;

    private ObservableList<Orders> orderList;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderList = FXCollections.observableArrayList();

        // Fill the list with pending orders
        refreshOrderList();

        // Customize the display of each element in the list
        orderListView.setCellFactory(lv -> new ListCell<>() {
            private final Button cancelBtn = new Button("Annulé");
            private final Button deliverBtn = new Button("Livré");

            @Override
            public void updateItem(Orders order, boolean empty) {
                super.updateItem(order, empty);

                if (empty || order == null) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText("Commande" + order.getId());
                    HBox buttonsBox = new HBox(cancelBtn, deliverBtn);
                    setGraphic(buttonsBox);

                    // Gérer le clic sur le bouton "Canceled"
                    cancelBtn.setOnAction(event -> {
                        order.setStatus("canceled");
                        refreshOrderList();
                    });

                    // Gérer le clic sur le bouton "Delivered"
                    deliverBtn.setOnAction(event -> {
                        order.setStatus("delivered");
                        refreshOrderList();
                    });
                }
            }
        });

        orderListView.setItems(orderList);
        //Add the return
        buttonReturn.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/RestaurantStatusView.fxml"))));
                Scene currentScene = buttonReturn.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Refresh the list of pending orders from the list of customers stored in HelloApplication.
     */
    private void refreshOrderList() {
        List<Customers> customers = HelloApplication.restaurant.getCustomersList();
        List<Orders> pendingOrders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getStatus().equals("pending")).toList();
        orderList.setAll(pendingOrders);
    }
}
