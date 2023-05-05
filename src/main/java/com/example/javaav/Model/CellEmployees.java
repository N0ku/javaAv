package com.example.javaav.Model;

import javafx.scene.control.ListCell;

public class CellEmployees extends ListCell<Employees> {
    @Override
    protected void updateItem(Employees item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (!empty && item != null) {
            final String text = String.format("%s %s","Nom: " + item.getName(),"Age: " + item.getAge());
            setText(text);
        }
    }
}
