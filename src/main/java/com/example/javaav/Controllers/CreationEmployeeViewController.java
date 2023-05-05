package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.Employees;
import com.example.javaav.Model.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class CreationEmployeeViewController implements Initializable {

    @FXML
    private TextField textName;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textYears;
    @FXML
    private TextField textPhone;
    @FXML
    private TextField textSalary;
    @FXML
    private TextField textMail;
    @FXML
    private ComboBox<String> boxJobName;
    @FXML
    private Button buttonCreate;
    @FXML
    private Label warningText;
    @FXML
    private Button backButton;

    private ObservableList<Employees> employeesData = FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Restaurant restaurant = HelloApplication.restaurant;
        List<TextField> inputs = Arrays.asList(textLastName,textName,textPhone,textSalary,textYears,textMail);
        buttonCreate.setOnAction(e -> {
            AtomicBoolean valid = new AtomicBoolean(true);
            inputs.stream().forEach(input ->
            {
                if (input == null || input.getText().isBlank()) {
                    warningText.setVisible(true);
                    valid.set(false);
                }
            });
            if (!valid.get() || boxJobName.getValue() == null){
                return;
            }
            Employees newEmployee = new Employees(textName.getText(),textMail.getText(),
                    textPhone.getText(),Integer.parseInt(textYears.getText()),"dezzf",
                    boxJobName.getValue(),34, Float.parseFloat(textSalary.getText()));

            restaurant.getEmployeesList().add(newEmployee);
            nextScene();
        });

        textPhone.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        boxJobName.setPromptText("Choisissez un emploi");
        boxJobName.getItems().addAll("Cuisinier", "Serveur", "Barman","Entretien");

        backButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/RestaurantStatusView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void blockLetter(KeyEvent e){
        char character = e.getText().charAt(0);
        if (!Character.isDigit(character)) {
            e.consume();
        }
    }

    public void nextScene(){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DisplayEmployeeView.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());


            Stage currentStage = (Stage) this.buttonCreate.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
