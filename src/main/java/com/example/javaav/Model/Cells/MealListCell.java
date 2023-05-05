package com.example.javaav.Model.Cells;

import com.example.javaav.Model.Ingredients;
import com.example.javaav.Model.Meals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class MealListCell extends ListCell<Meals> {
    private final Label nameLabel = new Label();
    private final Label priceLabel = new Label();
    private final Button detailsButton = new Button("Détails");

    public MealListCell() {
        VBox vBox = new VBox();
        nameLabel.setAlignment(Pos.CENTER_LEFT);
        priceLabel.setAlignment(Pos.CENTER_LEFT);
        detailsButton.setAlignment(Pos.CENTER_RIGHT);
        vBox.getChildren().addAll(nameLabel, priceLabel, detailsButton);
        HBox hBox = new HBox(vBox);
        hBox.setSpacing(10);
        setGraphic(hBox);
        setPrefWidth(USE_COMPUTED_SIZE);

        detailsButton.setOnAction(event -> {
            Meals meal = getItem();
            if (meal != null) {
                showMealDetails(meal);
            }
        });
    }

    @Override
    protected void updateItem(Meals meal, boolean empty) {
        super.updateItem(meal, empty);
        if (empty || meal == null) {
            setText(null);
            setGraphic(null);
            nameLabel.setText("");
            priceLabel.setText("");
        } else {
            nameLabel.setText(meal.getName());
            priceLabel.setText(String.format("%.2f €", meal.getPrice()));
            setGraphic(new HBox(nameLabel, priceLabel, detailsButton));
        }
    }


    private void showMealDetails(Meals meal) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Détails de " + meal.getName());

        // Creation de la grille pour afficher les détails du plat
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
        meal.getIngredients().stream()
                .map(Ingredients::getName)
                .sorted()
                .forEach(listView.getItems()::add);

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
}
