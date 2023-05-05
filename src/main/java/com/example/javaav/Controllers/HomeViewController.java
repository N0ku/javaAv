package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.ChronoThread;
import com.example.javaav.Model.Restaurant;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {
    @FXML
    private Label chronoLabel;

    @FXML
    private Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Restaurant restaurant = HelloApplication.restaurant;

        ChronoThread chrono = new ChronoThread(chronoLabel,restaurant);
        chrono.start();

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

    }
}