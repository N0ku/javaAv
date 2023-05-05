package com.example.javaav.Model.Cells;

import com.example.javaav.Model.Orders;
import javafx.scene.control.ListCell;

public class CellOrders extends ListCell<Orders> {
    @Override
    protected void updateItem(Orders item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        if (!empty && item != null) {
            final String text = String.format("%s %n%s %n%s", "Id commande: " + item.getId() ,"Repas: " + item.getMealListToString(),"Total Price: " + item.getTotalPrice());
            setText(text);
        }
    }
}
