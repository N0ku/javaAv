package com.example.javaav.Model;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CellMeals extends ListCell<Meals> {

    private final ImageView mealIcon = new ImageView();
    private final AnchorPane content = new AnchorPane();


    @Override
    protected void updateItem(Meals item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (!empty && item != null) {
            final String text = String.format("%s %s","Nom: " + item.getName(),"");
            setText(text);
            mealIcon.setImage(new Image(item.getImgUrl()));
            mealIcon.getStyleClass().add("meal-icon");
            mealIcon.setFitWidth(20);
            mealIcon.setFitHeight(20);
            mealIcon.setPreserveRatio(false);
            if (!content.getChildren().contains(mealIcon)) {
                content.getChildren().add(mealIcon);
            }
            setGraphic(content);
        }
    }
}
