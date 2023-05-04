package com.example.javaav.Controllers;

import com.example.javaav.Model.Employees;
import com.example.javaav.Model.Meals;
import com.example.javaav.Model.Orders;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      /*  backButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/HomeView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }); */
    }
}
