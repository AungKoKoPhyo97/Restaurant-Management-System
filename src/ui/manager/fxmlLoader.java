/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

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

    public Pane getPage(String fileName) {

        if (fileName == "SalesAnalyzedui") {
            try {
                URL fileUrl = ui.main.RestaurantManagement.class.getResource("/ui/salesanalyzed/" + fileName + ".fxml");
                if (fileUrl == null) {
                    AlertController.showErrorAlert("Error", "Check your Fxml Files");
                }
                view = new FXMLLoader().load(fileUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (fileName == "Inventoryui") {
            try {
                URL fileUrl = ui.main.RestaurantManagement.class.getResource("/ui/inventory/" + fileName + ".fxml");
                if (fileUrl == null) {
                    AlertController.showErrorAlert("Error", "Check your Fxml Files");
                }
                view = new FXMLLoader().load(fileUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (fileName == "Mealsui") {
            try {
                URL fileUrl = ui.main.RestaurantManagement.class.getResource("/ui/meals/" + fileName + ".fxml");
                if (fileUrl == null) {
                    AlertController.showErrorAlert("Error", "Check your Fxml Files");
                }
                view = new FXMLLoader().load(fileUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (fileName == "Staffui") {
            try {
                URL fileUrl = ui.main.RestaurantManagement.class.getResource("/ui/staff/" + fileName + ".fxml");
                if (fileUrl == null) {
                    AlertController.showErrorAlert("Error", "Check your Fxml Files");
                }
                view = new FXMLLoader().load(fileUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (fileName == "UpdatePasswordui") {
            try {
                URL fileUrl = ui.main.RestaurantManagement.class.getResource("/ui/updatepassword/" + fileName + ".fxml");
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
