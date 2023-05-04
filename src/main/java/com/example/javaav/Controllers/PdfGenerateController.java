package com.example.javaav.Controllers;

import com.example.javaav.Utils.PdfUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class PdfGenerateController implements Initializable {

    public List<String> nameAttribut= new ArrayList<>();

    public List<String> nameCol = new ArrayList<>();

    public List<HashMap<String,String>> data = new ArrayList<>();

    @FXML
    private ComboBox<String> boxItems;

    @FXML
    private Button buttonGenerate;

    @FXML
    private ListView<String> listItems;

    @FXML
    private Button buttonAdd;

    @FXML
    private TextField namePdf;

    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm:ss");

    public PdfGenerateController(List<String> nameAttribut, List<HashMap<String, String>> data, String className) {
        this.nameAttribut = nameAttribut;
        this.data = data;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(nameAttribut.isEmpty() || data.isEmpty() ){
            System.out.println("efef");
        }
        String formattedDateTime = dateTime.format(formatter);
        namePdf.setText("Employe--" + formattedDateTime);
        boxItems.getItems().addAll(nameAttribut);
        buttonAdd.setOnAction(e->{
            String attribut = boxItems.getValue();
            if(attribut.isBlank()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Data not Found");
                alert.show();
                return;
            }
            listItems.getItems().add(attribut);
            nameCol.add(attribut);
        });

        buttonGenerate.setOnAction(e -> {



            if (nameCol.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Pas d'attribut selectionner");
                alert.show();
                return;
            }
            PdfUtils pdf = new PdfUtils((!namePdf.getText().isBlank() ? namePdf.getText() : "defaul" )+".pdf");
            PdfPTable table = new PdfPTable(nameCol.size());
            pdf.addTableHeader(table, nameCol);
            pdf.addRows(table,data,nameCol);
            try {
                pdf.saveTable(table);
                pdf.closePDf();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML


    public void setNameAttribut(List<String> att) {
        this.nameAttribut.addAll(att);
    }
}
