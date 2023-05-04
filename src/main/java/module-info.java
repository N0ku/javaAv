module com.example.javaav {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.javaav to javafx.fxml;
    exports com.example.javaav;
    exports com.example.javaav.Controllers;
    opens com.example.javaav.Controllers to javafx.fxml;
}