module com.example.javaav {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires itextpdf;
    requires java.desktop;


    opens com.example.javaav to javafx.fxml;
    exports com.example.javaav;
    exports com.example.javaav.Controllers;
    opens com.example.javaav.Controllers to javafx.fxml;
    opens com.example.javaav.Model to javafx.base;
    opens com.example.javaav.Model.Cells to javafx.base;
}