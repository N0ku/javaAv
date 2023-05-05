package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.Customers;
import com.example.javaav.Model.Meals;
import com.example.javaav.Model.Orders;
import com.example.javaav.Model.Restaurant;
import com.example.javaav.Utils.PdfUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    public String classname;

    public Button buttonBack;

    private double totalDepense;

    private double totalMarge;



    @FXML
    private CheckBox checkboxFinance;

    public PdfGenerateController(List<String> nameAttribut, List<HashMap<String, String>> data, String className) {
        this.nameAttribut = nameAttribut;
        this.data = data;
        this.classname= className;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Restaurant restaurant = HelloApplication.restaurant;

        if(classname.equals("Finance")){
            data = getDataOrders();
            checkboxFinance.setVisible(true);
        }
        if(nameAttribut.isEmpty() || data.isEmpty() ){
            System.out.println("efef");
        }
        String formattedDateTime = dateTime.format(formatter);
        namePdf.setText(this.classname + "--" + formattedDateTime);
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
            try {
                pdf.document.add(new Paragraph("Rapport - "+classname+"  "+ formattedDateTime));
                pdf.document.add(new Paragraph(("                                    ")));

                pdf.document.add(new LineSeparator());
                pdf.document.add(new Paragraph(("                                    ")));
            } catch (DocumentException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Erreur de cfreation");
                alert.show();
            }

            PdfPTable table = new PdfPTable(nameCol.size());
            pdf.addTableHeader(table, nameCol);
            pdf.addRows(table,data,nameCol);
            try {
                pdf.saveTable(table);
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Erreur de cfreation");
                alert.show();
            }

            if(checkboxFinance.isSelected() && classname.equals("Finance")){
                try {
                    pdf.document.add(new Paragraph(("                                    ")));

                    pdf.document.add(new LineSeparator());
                    pdf.document.add(new Paragraph("Total dépense: " + String.valueOf(totalMarge - totalDepense) ));
                    pdf.document.add(new Paragraph("Total gain: " + String.valueOf(totalDepense) ));
                    pdf.document.add(new Paragraph("Total marge: " + String.valueOf(totalMarge) ));
                    pdf.document.add(new Paragraph("Revenu: " + restaurant.getRecipe() ));

                } catch (DocumentException ex) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Erreur de cfreation");
                    alert.show();
                }
            }
            try{
                pdf.closePDf();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success Dialog");
                alert.setHeaderText("Succes");
                alert.setContentText("Pdf  générer");
                alert.show();
            }catch (Exception err){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Erreur de cfreation");
                alert.show();
            }

        });

        buttonBack.setOnAction(e ->{
              try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RestaurantStatusView.fxml"));
                Scene newScene = new Scene(fxmlLoader.load());
                Stage currentStage = (Stage) buttonBack.getScene().getWindow();
                currentStage.setScene(newScene);
            } catch (IOException error) {
                error.printStackTrace();
            }
        });


    }


    private List<HashMap<String,String>> getDataOrders(){
        Restaurant restaurant = HelloApplication.restaurant;
        ArrayList<Customers> custu = restaurant.getCustomersList();
        List<HashMap<String,String>> data = new ArrayList<>();

        List<Orders> orders = custu.stream()
                .flatMap(e -> e.getOrders().stream())
                .filter(order -> order.getStatus().contains("delivered")).toList();

        List<HashMap<String,String>> er = new ArrayList<>();
        orders.stream().forEach(order ->{
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(order.getId()));
            map.put("prix", String.valueOf(order.getTotalPrice()));
            map.put("cout", calcOrder(order, "marge"));
            map.put("marge", calcOrder(order, "cost"));
            calcTotalOrder(order);
            er.add(map);
        });

        return er;
    }


    public void calcTotalOrder(Orders orders){

        if (orders.getMealList().isEmpty()){
            return;
        }

        double result1 =  orders.getMealList().stream()
                .mapToDouble(Meals::getMarge)
                .sum();

        totalMarge = totalMarge + result1;

        totalDepense = totalDepense + orders.getTotalPrice();

    }

    public String calcOrder(Orders orders, String mode){

        if (orders.getMealList().isEmpty()){
            return "O";
        }

        double result =  orders.getMealList().stream()
                .mapToDouble(Meals::getMarge)
                .sum();
        if(mode.contains("cost")){
            result = orders.getTotalPrice() - result;
        }
        return String.valueOf(result);
    }


    public void setNameAttribut(List<String> att) {
        this.nameAttribut.addAll(att);
    }
}
