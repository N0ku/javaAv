package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.Employees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import org.json.*;

public class DisplayEmployeeViewController implements Initializable {

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonDelete;

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

    Employees empoTest = new Employees(8,"eee","ffef","0987654",12,"dzdfzf","ffefef",12,2000);

    ObservableList<Employees> personData = FXCollections.observableArrayList();

    String dataJson = "[]";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.dataJson= new String(Files.readAllBytes(Paths.get("src/main/resources/com/example/javaav/json/employee.json")));
            System.out.println(dataJson);
            handleEmployeeFromJson(dataJson);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Data not Found");
            alert.show();

        }
        personData.add(empoTest);
        columnIdEmploye.setCellValueFactory(new PropertyValueFactory<Employees, Integer>("id"));
        columnJob.setCellValueFactory(new PropertyValueFactory<Employees, String >("jobName"));
        columnName.setCellValueFactory(new PropertyValueFactory<Employees, String>("name"));
        columnMail.setCellValueFactory(new PropertyValueFactory<Employees, String >("mail"));
        globalTab.setItems(personData);

        buttonAdd.setOnAction(e ->{
            rewriteJsonEmployee();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreationEmployeeView.fxml"));
                Scene newScene = new Scene(fxmlLoader.load());
                Stage currentStage = (Stage) buttonAdd.getScene().getWindow();
                currentStage.setScene(newScene);
            } catch (IOException error) {
                error.printStackTrace();
            }
        });

        buttonDelete.setOnAction(e -> {
            int selectedIndex = globalTab.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
               personData.remove(selectedIndex);

            } else {
                System.out.println("No row selected");
            }
        });
    }


    private void rewriteJsonEmployee(){
        JSONArray jsonArray = new JSONArray(dataJson);
        personData.stream().forEach(person ->{
            Optional<JSONObject> matchingObject = IntStream.range(0,jsonArray.length()).mapToObj(jsonArray::getJSONObject)
                    .filter(e ->
                         e.getInt("id") == person.getId()
                    )
                    .findFirst();
            if (matchingObject.isEmpty()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", person.getId());
                jsonObject.put("name", person.getName());
                jsonObject.put("mail", person.getMail());
                jsonObject.put("tel", person.getTel());
                jsonObject.put("age", person.getAge());
                jsonObject.put("adress", person.getAdress());
                jsonObject.put("jobName", person.getJobName());
                jsonObject.put("workHours", person.getWorkHours());
                jsonObject.put("salary", person.getSalary());
                jsonArray.put(jsonObject);
            }
        });
        try {
            Files.writeString(Paths.get("src/main/resources/com/example/javaav/json/employee.json"), jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

      

    }

    private void handleEmployeeFromJson(String json){
        JSONArray arrayEmployee = new JSONArray(json);
          IntStream
                .range(0,arrayEmployee.length()).mapToObj(arrayEmployee::getJSONObject).forEach(e ->{
                    personData.add(new Employees(e.getInt("id"),e.getString("name"),e.getString("mail"),
                    e.getString("tel"),e.getInt("age"), e.getString("adress"),e.getString("jobName"),
                    e.getInt("workHours"),e.getFloat("salary")));
                });
    }

}
