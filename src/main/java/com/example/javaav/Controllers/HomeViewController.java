package com.example.javaav.Controllers;

import com.example.javaav.MainApplication;
import com.example.javaav.Model.ChronoThread;
import com.example.javaav.Model.Restaurant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {
    @FXML
    private Label chronoLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button quitButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Restaurant restaurant = MainApplication.restaurant;

        ChronoThread chrono = new ChronoThread(chronoLabel,restaurant);
        chrono.start();

        quitButton.setOnAction(event->{
            MainApplication.quitter();
        });
        startButton.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/example/javaav/RestaurantStatusView.fxml"))));
                Scene currentScene = startButton.getScene();
                currentScene.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        startButton.getStyleClass().add("button-style");
        quitButton.getStyleClass().add("button-style");
    }
}