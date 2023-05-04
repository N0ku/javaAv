package com.example.javaav.Controllers;

import com.example.javaav.HelloApplication;
import com.example.javaav.Model.ChronoThread;
import com.example.javaav.Model.Restaurant;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {
    @FXML
    private Label chronoLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Restaurant restaurant = HelloApplication.restaurant;

        ChronoThread chrono = new ChronoThread(chronoLabel,restaurant);
        chrono.start();

    }
}