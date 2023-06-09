package com.example.javaav.Model.Cells;

import com.example.javaav.Model.Customers;
import javafx.scene.control.ListCell;

public class CellCustomers extends ListCell<Customers> {

    
    /** 
     * @param item
     * @param empty
     */
    @Override
    protected void updateItem(Customers item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (!empty && item != null) {
            final String text = String.format("%s %s","Nom: " + item.getName(),"Age: " + item.getAge());
            setText(text);
        }
    }
}