package com.example.javaav.Controllers;
import com.example.javaav.MainApplication;
import com.example.javaav.Model.Ingredients;
import com.example.javaav.Model.Meals;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;


public class MenuCreationViewController  {
    @FXML
    private TextField foodNameField;

    @FXML
    private TextField foodImgUrl;

    @FXML
    private TextArea foodDescriptionfield;

    @FXML
    private Spinner <Integer> priceSpinner;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1);
    @FXML
    private ComboBox<String> ingredientsComboBox;

    @FXML
    private Button createFoodValidationButton;

    @FXML
    private ListView<String> listIngredients;

    @FXML
    private Button backButton;
    @FXML
    private Label ingredientsListSetUp;
    private final ArrayList<Meals> orderedMeals = new ArrayList<>();
    public  static List<Ingredients> ingredientsList = new ArrayList<>();


    public void initialize() {
    //initialize the combobox to display the list of ingredients
        ArrayList<Meals> meals = MainApplication.restaurant.getMealsList();
        System.out.println(meals);
         priceSpinner.setValueFactory(svf);
        List<String> ingredients = meals.stream()
                .flatMap(meal -> meal.getIngredients().stream())
                .map(Ingredients::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        ingredients.add(0, "");
        ingredientsComboBox.getItems().addAll(ingredients);
        ingredientsComboBox.getSelectionModel().selectFirst();
        String selectedIngredientName = ingredientsComboBox.getValue();
        System.out.println(selectedIngredientName);

        ingredientsComboBox.setOnMousePressed(mouseEvent -> {
            String selectedItem = ingredientsComboBox.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem);
            ArrayList<String> selectedItems = new ArrayList<>();
            selectedItems.add(selectedItem);
            System.out.println(meals);

            selectedItems.stream()
                    .map(ingredientName -> meals.stream()
                            .flatMap(meal -> meal.getIngredients().stream())
                            .filter(ingredient -> ingredient.getName().equals(ingredientName))
                            .findFirst()
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .forEach(ingredientsList::add);
            ingredientsListSetUp.setText(ingredientsList.stream().map(Ingredients :: getName).collect(Collectors.toList()).toString());
            System.out.println();

//---------------------------------------------
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

//---------------------------------------------------------------------------------------------------------------------
        /* List<String> ingredients = Arrays.asList("Ingredient 1", "Ingredient 2", "Ingredient 3");
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
       // }*/
//---------------------------------------------------------------------------------------------------------------------
    }

    @FXML
    private void createMealButtonAction(){
    // get the variable from the form
        String name = foodNameField.getText();
        String imgUrl = foodImgUrl.getText();
        float price = (float) priceSpinner.getValue();
        String desc = foodDescriptionfield.getText();
        ArrayList<Ingredients> ingredients = new ArrayList<>();

//---------------------------------------------------------------------------------------------------------------------
      //  Meals selectedMeal = ingredientsComboBox.getSelectionModel().getSelectedItem();
       // String name = selectedMeal.getName();
        //String ingredients = selectedMeal.getIngredients();
      //  double price = selectedMeal.getPrice();
//---------------------------------------------------------------------------------------------------------------------

    //get the value of selected ingredient and put it to the list of ingredients

//---------------------------------------------------------------------------------------------------------------------
      //  List<Ingredients> filteredIngredients = ingredients.stream()
       //         .filter(ingredient -> ingredient.getName().toLowerCase().contains(name.toLowerCase()))
       //         .collect(Collectors.toList());
      //  System.out.println(filteredIngredients);
//---------------------------------------------------------------------------------------------------------------------


// Get selected ingredient from the combo box
        String selectedIngredient =  ingredientsComboBox.getSelectionModel().toString();

// Create a new Ingredients object with the selected ingredient
        Ingredients ingredient = new Ingredients(1,1,selectedIngredient);

// Add the ingredient to the list
        ingredients.add(ingredient);
        Float marge = ingredientsList.stream()
                .map(e -> price - e.getPrice())
                .reduce(0f, Float::sum);

        // récupérez les ingrédients sélectionnés dans votre combobox et ajoutez-les à la liste ingredients
        Meals newMeal = new Meals(name, imgUrl, price, 0, desc,  marge, (ArrayList<Ingredients>) ingredientsList);
        MainApplication.restaurant.getMealsList().add(newMeal);

        // ajoutez le nouveau plat créé à votre liste de plats existants
        System.out.println(MainApplication.restaurant.getMealsList());
        System.out.println(ingredientsList.size());
        System.out.println(ingredientsList.size());
        foodNameField.clear();
        foodImgUrl.clear();
        priceSpinner.getValueFactory().setValue(0);
        foodDescriptionfield.clear();
        ingredientsComboBox.getSelectionModel().clearSelection();
    }

}
