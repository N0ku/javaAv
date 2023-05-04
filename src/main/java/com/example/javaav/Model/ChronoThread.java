package com.example.javaav.Model;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChronoThread extends Thread{

    @FXML
    private Label labelToUpdate;

    private Restaurant restaurant;

    public ChronoThread(Label label, Restaurant restaurant){
        this.labelToUpdate = label;
        this.restaurant = restaurant;
    }

    public void run() {
        while (true) {
            String seconds = restaurant.getService().getSeconds();
            Platform.runLater(() -> labelToUpdate.setText(seconds));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
