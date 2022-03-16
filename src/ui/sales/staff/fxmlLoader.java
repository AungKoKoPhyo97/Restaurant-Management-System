/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.sales.staff;

import alert.AlertController;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author Acer
 */
public class fxmlLoader {

    private Pane view;

    public Pane getPane(String fileName) {
        if (fileName == "SalesProcessui") {
            try {
                URL fileUrl = ui.main.RestaurantManagement.class.getResource("/ui/sales/salesprocess/" + fileName + ".fxml");
                if (fileUrl == null) {
                    AlertController.showErrorAlert("Error", "Check your Fxml Files");
                }
                view = new FXMLLoader().load(fileUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (fileName  == "SalesInventoryui") {
            try {
                URL fileUrl = ui.main.RestaurantManagement.class.getResource("/ui/sales/inventory/" + fileName + ".fxml");
                if (fileUrl == null) {
                    AlertController.showErrorAlert("Error", "Check your Fxml Files");
                }
                view = new FXMLLoader().load(fileUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return view;
    }
}
