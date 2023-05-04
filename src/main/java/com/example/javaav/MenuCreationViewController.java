package com.example.javaav;
import com.example.javaav.Model.Ingredients;
import com.example.javaav.Model.Meals;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuCreationViewController {
    @FXML
    private TextField foodNameField;

    @FXML
    private TextField foodImgUrl;

    @FXML
    private TextArea foodDescriptionfield;

    @FXML
    private Spinner <Integer> priceSpinner;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,1);
    @FXML
    private ComboBox<String> ingredientsComboBox;

    @FXML
    private Button createFoodValidationButton;

    @FXML
    private ListView<String> listIngredients;

    @FXML
    private Label errorLabel;
    public void initialize() {
      priceSpinner.setValueFactory(svf);
         List<String> ingredients = Arrays.asList("Ingredient 1", "Ingredient 2", "Ingredient 3");
        ingredientsComboBox.getItems().addAll(ingredients.stream().sorted().collect(Collectors.toList()));
         ingredientsComboBox.getSelectionModel().selectFirst();
       // ListView<String> listView = new ListView<>();
        //listView.getItems().addAll("Ingrédient 1", "Ingrédient 2", "Ingrédient 3");
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
// ...

// Récupère les éléments sélectionnés dans la ListView
       // ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
        //for (String item : selectedItems) {
        //    System.out.println("Ingrédient sélectionné : " + item);
       // }


    }
    @FXML
    private void createMealButtonAction(){
        String name = foodNameField.getText();
        String imgUrl = foodImgUrl.getText();
        float price = (float) priceSpinner.getValue();
        String desc = foodDescriptionfield.getText();
        ArrayList<Ingredients> ingredients = new ArrayList<Ingredients>();
        // récupérez les ingrédients sélectionnés dans votre combobox et ajoutez-les à la liste ingredients
        Meals newMeal = new Meals(name, imgUrl, price, 0, desc, false, 0, ingredients);
        // ajoutez le nouveau plat créé à votre liste de plats existants

        System.out.println(newMeal);
    }
}
