package com.example.javaav.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DisplayEmployeeViewController {

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonDelete;

    @FXML
    private TableColumn<?, ?> columnIdEmploye;

    @FXML
    private TableColumn<?, ?> columnJob;

    @FXML
    private TableColumn<?, ?> columnLastName;

    @FXML
    private TableColumn<?, ?> columnMail;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableView<?> globalTab;

    @FXML
    private TextField textSearch;
}
