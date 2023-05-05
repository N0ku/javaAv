package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.Employees;
import com.example.javaav.Model.Restaurant;
import com.example.javaav.Utils.PdfUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

import org.json.*;

public class DisplayEmployeeViewController implements Initializable {

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button generatePdf;

    @FXML
    private TableColumn<Employees, Integer> columnIdEmploye;

    @FXML
    private TableColumn<Employees, String> columnJob;

    @FXML
    private TableColumn<Employees, String> columnLastName;

    @FXML
    private TableColumn<Employees, String> columnMail;

    @FXML
    private TableColumn<Employees, String> columnName;

    @FXML
    private TableView<Employees> globalTab;

    @FXML
    private TextField textSearch;

    @FXML
    private Button backButton;

    ObservableList<Employees> personData = FXCollections.observableArrayList();

    String dataJson = "[]";

    Restaurant restaurant = HelloApplication.restaurant;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            personData.addAll(restaurant.getEmployeesList());
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Data not Found");
            alert.show();
        }

        columnIdEmploye.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("id"));
        columnJob.setCellValueFactory(new PropertyValueFactory<Employees, String>("jobName"));
        columnName.setCellValueFactory(new PropertyValueFactory<Employees, String>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<Employees, String>("salary"));
        columnMail.setCellValueFactory(new PropertyValueFactory<Employees, String>("mail"));
        globalTab.setItems(personData);

        buttonAdd.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreationEmployeeView.fxml"));
                Scene newScene = new Scene(fxmlLoader.load());
                Stage currentStage = (Stage) buttonAdd.getScene().getWindow();
                currentStage.setScene(newScene);
            } catch (IOException error) {
                error.printStackTrace();
            }
        });
        
           backButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects
                        .requireNonNull(getClass().getResource("/com/example/javaav/RestaurantStatusView.fxml"))));
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        generatePdf.setOnAction(e -> {
            List<String> r = List.of("id","name","tel","mail","salary");
            List<HashMap<String,String>> er = new ArrayList<>();
            personData.stream().forEach(person ->{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(person.getId()));
                map.put("name", person.getName());
                map.put("tel", person.getTel());
                map.put("mail", person.getMail());
                map.put("salary", String.valueOf(person.getSalary()));
                er.add(map);
            });
            PdfGenerateController controllerEmplo = new PdfGenerateController(r,er, "Employee");

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("PdfGenerateView.fxml"));
                loader.setController(controllerEmplo);
                Scene newScene = new Scene(loader.load());

                Stage currentStage = (Stage) generatePdf.getScene().getWindow();
                currentStage.setScene(newScene);
            } catch (IOException error) {
                error.printStackTrace();
            }
        });


        buttonDelete.setOnAction(e ->{
            deleteEmployee();
        });
    }


    private void deleteEmployee() {
        int selectedIndex = globalTab.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personData.remove(selectedIndex);
            restaurant.getEmployeesList().remove(selectedIndex);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Pas de ligne séléctionner");
            alert.show();
        }
    }


}
