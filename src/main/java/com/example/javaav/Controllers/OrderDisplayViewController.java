package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.Customers;
import com.example.javaav.Model.Meals;
import com.example.javaav.Model.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrderDisplayViewController implements Initializable {

    @FXML
    private ListView<Orders> orderListView;

    private ObservableList<Orders> orderList;

    public OrderDisplayViewController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderList = FXCollections.observableArrayList();

        // Remplir la liste des commandes en attente
        refreshOrderList();

        // Personnaliser l'affichage de chaque élément de la liste
        orderListView.setCellFactory(lv -> new ListCell<>() {
            private final Button cancelBtn = new Button("Canceled");
            private final Button deliverBtn = new Button("Delivered");

            @Override
            public void updateItem(Orders order, boolean empty) {
                super.updateItem(order, empty);

                if (empty || order == null) {
                    setText(null);
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
    }

    /**
     * Rafraîchir la liste des commandes en attente à partir de la liste des clients stockée dans HelloApplication.
     */
    private void refreshOrderList() {
        List<Customers> customers = HelloApplication.restaurant.getCustomersList();
        List<Orders> pendingOrders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getStatus().equals("pending"))
                .collect(Collectors.toList());
        orderList.setAll(pendingOrders);
    }
}
