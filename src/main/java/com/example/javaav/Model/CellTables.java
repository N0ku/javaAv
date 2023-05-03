package com.example.javaav.Model;

import javafx.scene.control.ListCell;

import java.util.ArrayList;

public class CellTables extends ListCell<Tables> {
    @Override
    protected void updateItem(Tables item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (!empty && item != null) {
            ArrayList<Customers> customers = item.getCustomers();
            String text;
            if (customers.isEmpty()) {
                text = String.format("Table: %s%n%s", item.getTableNumber(), "Table disponible");
            } else {
                text = String.format("Table: %s%n%s", item.getTableNumber(), customers);
            }
            setText(text);
        }
    }
}
