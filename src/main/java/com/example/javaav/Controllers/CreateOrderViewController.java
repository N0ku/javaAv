package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.Customers;
import com.example.javaav.Model.Ingredients;
import com.example.javaav.Model.Meals;
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

    private ArrayList<Meals> meals;
    private ArrayList<Customers> customer;
    private Meals selectedMeal;



    private final ArrayList<Meals> orderedMeals = new ArrayList<>();
    private final SpinnerValueFactory<Integer> valueFactory = new IntegerSpinnerValueFactory(0, 10, 0, 1);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        meals = HelloApplication.restaurant.getMealsList();

        // Configuration de la liste des plats dans le ListView
        ObservableList<Meals> observableList = FXCollections.observableArrayList(meals);
        mealListView.setItems(observableList);
        mealListView.setCellFactory(param -> new ListCell<Meals>() {
            private final Label nameLabel = new Label();
            private final Label priceLabel = new Label();
            private final Button detailsButton = new Button("Détails");

            @Override
            protected void updateItem(Meals meal, boolean empty) {
                super.updateItem(meal, empty);
                if (empty || meal == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    nameLabel.setText(meal.getName());
                    priceLabel.setText(String.format("%.2f €", meal.getPrice()));
                    detailsButton.setOnAction(event -> showMealDetails(meal));
                    VBox vBox = new VBox(nameLabel, priceLabel, detailsButton);
                    HBox hBox = new HBox(vBox);
                    setGraphic(hBox);
                }
            }

            private void showMealDetails(Meals meal) {
                Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setTitle("Détails de " + meal.getName());

                // Création de la grille pour afficher les détails du plat
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                // Ajout de l'image du plat
                ImageView imageView = new ImageView(new Image(meal.getImgUrl()));
                imageView.setFitHeight(200);
                imageView.setFitWidth(200);
                grid.add(imageView, 0, 0, 2, 1);

                // Ajout du nom et du prix du plat
                grid.add(new Label("Nom : "), 0, 1);
                grid.add(new Label(meal.getName()), 1, 1);
                grid.add(new Label("Prix : "), 0, 2);
                grid.add(new Label(meal.getPrice() + " €"), 1, 2);

                // Ajout de la description du plat
                grid.add(new Label("Description : "), 0, 3);
                grid.add(new Label(meal.getDesc()), 1, 3);

                // Ajout de la liste des ingrédients du plat
                ListView<String> listView = new ListView<>();
                for (Ingredients ingredient : meal.getIngredients()) {
                    String text = ingredient.getName();
                    listView.getItems().add(text);
                }
                grid.add(new Label("Ingrédients : "), 0, 4);
                grid.add(listView, 1, 4);

                // Ajout d'un bouton pour fermer la modale
                Button closeButton = new Button("Fermer");
                closeButton.setOnAction(e -> dialog.close());

                // Création d'une boîte pour afficher la grille et le bouton
                VBox vbox = new VBox(grid, closeButton);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(20);

                // Affichage de la boîte dans la modale
                Scene dialogScene = new Scene(vbox, 600, 400);
                dialog.setScene(dialogScene);
                dialog.showAndWait();
            }

        });


        mealListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMeal = newValue;
            showMealSelected();
            // Configuration des valeurs du spinner pour choisir la quantité de chaque plat à ajouter à la commande
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 1);
            quantitySpinner.setValueFactory(valueFactory);
        });

        // Configuration de l'action du bouton pour ajouter le plat sélectionné à la commande
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
        // Configuration de l'action du bouton pour finaliser la commande
        finalizeButton.setOnAction(event -> {
            if (!orderedMeals.isEmpty()) {
                double totalPrice = orderedMeals.stream()
                        .mapToDouble(meal -> meal.getPrice() * meal.getQuantity())
                        .sum();

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
                alert.setTitle("Commande finalisée");
                alert.setHeaderText(null);
                alert.setContentText(sb.toString());
                alert.showAndWait();

                // Réinitialisation des plats sélectionnés
                orderedMeals.clear();
                selectedMeal = null;
                quantitySpinner.setValueFactory(valueFactory);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Commande vide");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez ajouter des plats à la commande.");
                alert.showAndWait();
            }
        });
    }

    private void showMealSelected() {
        if (selectedMeal != null) {
            // Affichage des détails du plat sélectionné
            mealImageView.setImage(new Image(selectedMeal.getImgUrl()));
            nameLabel.setText(selectedMeal.getName());
            priceLabel.setText(String.format("%.2f €", selectedMeal.getPrice()));
            descLabel.setText(selectedMeal.getDesc());

        } else {
            // Effacement des détails si aucun plat n'est sélectionné
            mealImageView.setImage(null);
            nameLabel.setText("");
            priceLabel.setText("");
            descLabel.setText("");
        }
    }

    private void displayOrderedMeals() {
        containerResult.getChildren().clear(); // vide la VBox
        orderedMeals.stream()
                .map(Meals::getName) // transforme chaque Meal en son nom
                .map(Label::new) // crée un Label pour chaque nom
                .forEach(containerResult.getChildren()::add); // ajoute chaque Label à la VBox
    }
    //Méthode pour afficher un message de confirmation
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
