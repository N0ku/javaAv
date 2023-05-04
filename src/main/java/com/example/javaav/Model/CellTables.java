package com.example.javaav.Model;

import javafx.scene.control.ListCell;

import java.util.ArrayList;

public class CellTables extends ListCell<Tables> {
    @Override
    protected void updateItem(Tables item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (!empty && item != null) {
            String customers = item.getCustomersToString();
            String text;
            if (customers.isEmpty()) {
                text = String.format("Table: %s%n%s%n%s %d", item.getTableNumber(), "Table disponible", "Nombre de place dispo:", item.getSize());
            } else {
                text = String.format("Table: %s%n%s%n%s %d", item.getTableNumber(), customers, "Nombre de place dispo:", item.getSize());
            }
            setText(text);
        }
    }
}