/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import alert.AlertController;
import database.DBController;
import dataclass.Admin;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Dell
 */
public class RestaurantManagement extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/ui/main/RestaurantMangement.fxml"));
//                Parent root = FXMLLoader.load(getClass().getResource("/ui/sales/staff/StaffSalesui.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Nino Restaurant");
        stage.getIcons().add(new Image("/resources/image/title.png"));
        stage.show();

    }

    public static void main(String[] args) {
        if (!DBController.checkDbExist()) {
            DBController.createStaffTbl();
            DBController.createAdminTbl();
            DBController.createMealsTbl();
            DBController.createSalesTbl();
            DBController.createInventoryTbl();
            DBController.createMealsDetailTbl();
            DBController.createSalesDetailTbl();
            DBController.createDailySalesView();
            Admin.insertDefaultPassword();
        }
        launch(args);
    }

}
