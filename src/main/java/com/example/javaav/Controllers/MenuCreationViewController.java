package com.example.javaav.Controllers;
import com.example.javaav.HelloApplication;
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
    private final ArrayList<Meals> orderedMeals = new ArrayList<>();
    public void initialize() {
        ArrayList<Meals> meals = HelloApplication.restaurant.getMealsList();
        System.out.println(meals);
         priceSpinner.setValueFactory(svf);
        List<String> ingredients = meals.stream()
                .flatMap(meal -> meal.getIngredients().stream())
                .map(Ingredients::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        ingredientsComboBox.getItems().addAll(ingredients);
        ingredientsComboBox.getSelectionModel().selectFirst();




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


    }
    @FXML
    private void createMealButtonAction(){
        String name = foodNameField.getText();
        String imgUrl = foodImgUrl.getText();
        float price = (float) priceSpinner.getValue();
        String desc = foodDescriptionfield.getText();
        ArrayList<Ingredients> ingredients = new ArrayList<>();


        List<Ingredients> filteredIngredients = ingredients.stream()
                .filter(ingredient -> ingredient.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println(filteredIngredients);

// Get selected ingredient from the combo box
        String selectedIngredient =  ingredientsComboBox.getSelectionModel().toString();

// Create a new Ingredients object with the selected ingredient
        Ingredients ingredient = new Ingredients(1,1,selectedIngredient,new ArrayList<String>());

// Add the ingredient to the list
        ingredients.add(ingredient);

        // récupérez les ingrédients sélectionnés dans votre combobox et ajoutez-les à la liste ingredients
        Meals newMeal = new Meals(name, imgUrl, price, 0, desc, false, 0, ingredients);
        HelloApplication.restaurant.getMealsList().add(newMeal);

        // ajoutez le nouveau plat créé à votre liste de plats existants
        System.out.println(HelloApplication.restaurant.getMealsList());
        foodNameField.clear();
        foodImgUrl.clear();
        priceSpinner.getValueFactory().setValue(0);
        foodDescriptionfield.clear();
        ingredientsComboBox.getSelectionModel().clearSelection();
    }
}
