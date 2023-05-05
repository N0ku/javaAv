package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.*;
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
    private Spinner<Integer> quantitySpinner;
    @FXML
    private Button addButton;
    @FXML
    private Button finalizeButton;
    @FXML
    private VBox containerResult;
    @FXML
    private Label labelTotalPrice;
    @FXML
    private ComboBox comboBoxTable;
    @FXML
    private ComboBox<Customers> comboBoxClient;
    @FXML
    private VBox containerConfigOrder;
    @FXML
    private VBox containerListMeal;
    @FXML
    private HBox containerSelectOrder;
    @FXML
    private Button btnValidConfig;

    @FXML
    private Button backButton;

    private ArrayList<Meals> meals;
    private Customers custom;
    private Meals selectedMeal;
    public String selectedTableNumber;


    private final ArrayList<Meals> orderedMeals = new ArrayList<>();
    private final SpinnerValueFactory<Integer> valueFactory = new IntegerSpinnerValueFactory(0, 10, 0, 1);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        containerListMeal.setVisible(false);
        containerSelectOrder.setVisible(false);
        containerResult.setVisible(false);
        comboBoxClient.setDisable(true);
        btnValidConfig.setDisable(true);
        labelTotalPrice.setText("");List<String> tableNumbers = HelloApplication.restaurant.getTablesList()
                .stream()
                .filter(t -> !t.isFree())
                .map(t -> String.valueOf(t.getTableNumber()))
                .collect(Collectors.toList());
        comboBoxTable.getItems().addAll(tableNumbers);
        comboBoxTable.setOnAction(event -> {
            // Récupérer le numéro de table sélectionné dans le ComboBox
             selectedTableNumber = comboBoxTable.getSelectionModel().getSelectedItem().toString();
            comboBoxClient.setDisable(false);
            btnValidConfig.setDisable(false);
            // Filtrer les tables en fonction du numéro de table sélectionné
            List<Tables> selectedTables = HelloApplication.restaurant.getTablesList()
                    .stream()
                    .filter(t -> String.valueOf(t.getTableNumber()).equals(selectedTableNumber))
                    .collect(Collectors.toList());

            // Récupérer la liste de clients associée à la première table trouvée (ou null si aucune table ne correspond)
            List<Customers> customersList = selectedTables.stream()
                    .findFirst()
                    .map(t -> t.getCustomers())
                    .orElse(null);

            // Ajouter les noms des clients au ComboBox
            comboBoxClient.getItems().clear();
            if (customersList != null) {
                comboBoxClient.getItems().addAll(customersList);
                comboBoxClient.setCellFactory(new javafx.util.Callback<ListView<Customers>, ListCell<Customers>>() {
                    public ListCell<Customers> call(ListView<Customers> param) {
                        return new ListCell<Customers>() {
                            @Override
                            protected void updateItem(Customers item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setText(null);
                                } else {
                                    setText(item.getId() + " - " + item.getName());
                                }
                            }
                        };
                    }
                });

            }
        });

        btnValidConfig.setOnAction(event -> {
            // Récupérer le client sélectionné dans le ComboBox
            Customers selectedCustomer = comboBoxClient.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                // Récupérer l'utiliser pour configurer la table
                custom = selectedCustomer;
                containerListMeal.setVisible(true);
                containerSelectOrder.setVisible(true);
                containerResult.setVisible(true);
                containerConfigOrder.setVisible(false);
            }
        });

        backButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/RestaurantStatusView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });


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
                if (quantity != 0) {
                    // Vérifier si l'élément est déjà présent dans la liste
                    Optional<Meals> existingMeal = orderedMeals.stream()
                            .filter(meal -> meal.equals(selectedMeal))
                            .findFirst();

                    if (existingMeal.isPresent()) {
                        // Si l'élément est déjà présent, augmenter la quantité
                        int newQuantity = existingMeal.get().getQuantity() + quantity;
                        existingMeal.get().setQuantity(newQuantity);
                    } else {
                        // Sinon, ajouter l'élément à la liste
                        selectedMeal.setQuantity(quantity);
                        orderedMeals.add(selectedMeal);
                    }

                    showConfirmationAlert(selectedMeal.getName(), quantity);
                    displayOrderedMeals();

                } else {
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
                custom.getOrders().add(order);
                List<Customers> matchingCustomers = HelloApplication.restaurant.getCustomersList().stream()
                        .filter(c -> c== custom)
                        .collect(Collectors.toList());
                System.out.println(matchingCustomers);

                // Display the order confirmation
                StringBuilder sb = new StringBuilder("Commande "+order.getId() +"-"+custom.getName() );
                Map<String, Integer> mealQuantities = orderedMeals.stream()
                        .collect(Collectors.groupingBy(Meals::getName, Collectors.summingInt(Meals::getQuantity)));
                mealQuantities.forEach((mealName, quantity) ->
                        sb.append(mealName).append(" x ").append(quantity).append(" : ")
                                .append(quantity * orderedMeals.stream().filter(meal -> meal.getName().equals(mealName))
                                        .findFirst().get().getPrice()).append(" €\n")
                );
                sb.append("\nTotal : ").append(totalPrice).append(" €");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Commande en cours de préparation");
                alert.setHeaderText(null);
                alert.setContentText(sb.toString());
                alert.showAndWait();

                // Reset selected meals
                orderedMeals.clear();
                selectedMeal = null;
                containerConfigOrder.setVisible(true);
                quantitySpinner.setValueFactory(valueFactory);
                containerListMeal.setVisible(false);
                containerSelectOrder.setVisible(false);
                containerResult.setVisible(false);
                comboBoxClient.setDisable(true);
                btnValidConfig.setDisable(true);
                labelTotalPrice.setText("");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Panier vide ");
                alert.setHeaderText(null);
                alert.setContentText("Votre panier est vide ");
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

        } else {
            // Delete details if no meal selected
            mealImageView.setImage(null);
            nameLabel.setText("");
            priceLabel.setText("");
        }
    }

    private void displayOrderedMeals() {
        containerResult.getChildren().clear(); // Clear the VBox

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        orderedMeals.stream().map(meal -> {
             Label  nameLabelBill = new Label(meal.getName());
            Label priceLabelBill = new Label(meal.getPrice() + " €");
            Label quantityLabel = new Label(meal.getQuantity()+"");

            // Add the labels to a HBox
            HBox hbox = new HBox(nameLabelBill, quantityLabel, priceLabelBill);
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
