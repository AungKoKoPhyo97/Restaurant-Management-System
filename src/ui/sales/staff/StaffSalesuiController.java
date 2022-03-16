/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.sales.staff;

import com.jfoenix.controls.JFXButton;
import database.DBController;
import static database.DBController.getConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ui.main.RestaurantMangementController;
import ui.sales.salesprocess.SalesProcessuiController;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class StaffSalesuiController implements Initializable {

    @FXML
    private JFXButton btnSales;

    @FXML
    private JFXButton btnInventory;
    @FXML
    private JFXButton btnExit;
    @FXML
    private BorderPane StaffBorder;
    private HBox hName;

    @FXML
    private Label lblName;
    @FXML
    private Label lblNrc;

    @FXML
    private Label lblid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ui.sales.staff.fxmlLoader object = new ui.sales.staff.fxmlLoader();
        Pane view = object.getPane("SalesProcessui");
        StaffBorder.setCenter(view);
        styleSheet();
        btnSales.setStyle("-fx-background-color:#CCD1D9;"
                + "-fx-border-width:0 0 0 4;"
                + "-fx-border-color:white;");

    }

    public void setText(int sid, String sname, String snrc) {
        this.lblid.setText("ID : " + sid);
        this.lblName.setText("Username : " + sname);
        this.lblNrc.setText("Nrc : " + snrc);
    }

    @FXML
    private void Salesfxml(ActionEvent event
    ) {
        SceneAnimation("SalesProcessui");
        styleSheet();
        btnSales.setStyle("-fx-background-color:#CCD1D9;"
                + "-fx-border-width:0 0 0 4;"
                + "-fx-border-color:white;");
    }

    @FXML
    private void InventoryFxml(ActionEvent event
    ) {
        SceneAnimation("SalesInventoryui");
        styleSheet();
        btnInventory.setStyle("-fx-background-color:#CCD1D9;"
                + "-fx-border-width:0 0 0 4;"
                + "-fx-border-color:white;");

    }

    @FXML
    private void exitProgram(ActionEvent event
    ) {
        if (alert.AlertController.showConfirmAlert("Quit", "Are you sure you want to quit!")) {
            Platform.exit();
        }
    }

    public void SceneAnimation(String btnName) {
        fxmlLoader object = new fxmlLoader();
        Pane view = object.getPane(btnName);

        Scene scene = btnSales.getScene();
        view.translateXProperty().set(scene.getHeight());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(view.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        StaffBorder.setCenter(view);
    }

    public void styleSheet() {
        btnSales.setStyle("-fx-background-color:#AAB2BD;"
                + "-fx-border-width:0 0 0 0;"
                + "-fx-border-color:white;");
        btnInventory.setStyle("-fx-background-color:#AAB2BD;"
                + "-fx-border-width:0 0 0 0;"
                + "-fx-border-color:white;");
    }
}
