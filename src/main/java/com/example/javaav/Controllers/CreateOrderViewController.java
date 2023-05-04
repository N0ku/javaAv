package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CreateOrderViewController implements Initializable {

    @FXML
    private ListView<Meals> mealListView;
    @FXML
    private ImageView mealImageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Spinner<Integer> quantitySpinner;
    @FXML
    private Button addButton;
    @FXML
    private Button finalizeButton;
    @FXML
    private VBox containerResult;
    @FXML
    private Label labelTotalPrice;

    private ArrayList<Meals> meals;
    private Meals selectedMeal;
    public Label nameLabelBill ;
    public  Label priceLabelBill;


    private final ArrayList<Meals> orderedMeals = new ArrayList<>();
    private final SpinnerValueFactory<Integer> valueFactory = new IntegerSpinnerValueFactory(0, 10, 0, 1);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        meals = HelloApplication.restaurant.getMealsList();


        // ListView Flat List Configuration
        ObservableList<Meals> observableList = FXCollections.observableArrayList(meals);
        mealListView.setItems(observableList);
        mealListView.setCellFactory(param ->new MealListCell());

        mealListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMeal = newValue;
            showMealSelected();
            quantitySpinner.setValueFactory(valueFactory);
        });

        // Setting the button action to add the selected dish to the control
        addButton.setOnAction(event -> {
            if (selectedMeal != null) {
                int quantity = quantitySpinner.getValue();
                if(quantity != 0){
                    selectedMeal.setQuantity(quantity);
                    orderedMeals.add(selectedMeal);
                    showConfirmationAlert(selectedMeal.getName(), quantity);
                    displayOrderedMeals();
                }else{
                    showErrorAlert();
                }

            }
        });
        // Configure the button action to finalize the command
        finalizeButton.setOnAction(event -> {
            if (!orderedMeals.isEmpty()) {
                double totalPrice = orderedMeals.stream()
                        .mapToDouble(meal -> meal.getPrice() * meal.getQuantity())
                        .sum();

                // Create the order
                ArrayList<Meals> mealsDto = new ArrayList<>(orderedMeals);
                Orders order = new Orders(mealsDto, totalPrice);

                // Display the order confirmation
                StringBuilder sb = new StringBuilder("Commande :\n\n");
                Map<String, Integer> mealQuantities = orderedMeals.stream()
                        .collect(Collectors.groupingBy(Meals::getName, Collectors.summingInt(Meals::getQuantity)));
                mealQuantities.forEach((mealName, quantity) ->
                        sb.append(mealName).append(" x ").append(quantity).append(" : ")
                                .append(quantity * orderedMeals.stream().filter(meal -> meal.getName().equals(mealName))
                                        .findFirst().get().getPrice()).append(" €\n")
                );
                sb.append("\nTotal : ").append(totalPrice).append(" €");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order finalized");
                alert.setHeaderText(null);
                alert.setContentText(sb.toString());
                alert.showAndWait();

                // Reset selected meals
                orderedMeals.clear();
                selectedMeal = null;
                quantitySpinner.setValueFactory(valueFactory);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty order");
                alert.setHeaderText(null);
                alert.setContentText("Please add meals to the order.");
                alert.showAndWait();
            }
        });

    }

    private void showMealSelected() {
        if (selectedMeal != null) {
            // View details of selected meal
            mealImageView.setImage(new Image(selectedMeal.getImgUrl()));
            nameLabel.setText(selectedMeal.getName());
            priceLabel.setText(String.format("%.2f €", selectedMeal.getPrice()));
            descLabel.setText(selectedMeal.getDesc());

        } else {
            // Delete details if no meal selected
            mealImageView.setImage(null);
            nameLabel.setText("");
            priceLabel.setText("");
            descLabel.setText("");
        }
    }

    private void displayOrderedMeals() {
        containerResult.getChildren().clear(); // Clear the VBox

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        orderedMeals.stream().map(meal -> {
            nameLabelBill = new Label(meal.getName());
            priceLabelBill = new Label(meal.getPrice() + " €");
            Label quantityLabel = new Label(meal.getQuantity()+" fois");

            // Add the labels to a HBox
            HBox hbox = new HBox(nameLabel, quantityLabel, priceLabel);
            hbox.setSpacing(10);

            return hbox;
        }).forEach(vbox.getChildren()::add);

        // Calculate the total price for all meals
        double totalPrice = orderedMeals.stream()
                .mapToDouble(meal -> meal.getPrice() * meal.getQuantity())
                .sum();

        labelTotalPrice.setText("Total : " + totalPrice + " €");

        // Make the VBox scrollable
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(200);

        containerResult.getChildren().add(scrollPane);
    }



    //Method to display confirmation messages
    private void showConfirmationAlert(String mealName, int quantity) {
        String message = String.format("Le plat %s a été ajouté %d fois à la commande.", mealName, quantity);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Plat ajouté");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorAlert( ) {
        String message = "Vous êtes dans l'obligations d'avoir au moins 1 plat. ";
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
