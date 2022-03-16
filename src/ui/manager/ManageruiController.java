/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import alert.AlertController;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class ManageruiController implements Initializable {

    private BorderPane bdr;
    @FXML
    private JFXButton btnAnalyzed;
    @FXML
    private JFXButton btnInventory;
    @FXML
    private JFXButton btnMeals;
    @FXML
    private JFXButton btnStaff;
    @FXML
    private JFXButton btnUpdatePassword;
    @FXML
    private BorderPane managerBorder;
    @FXML
    private JFXButton btnExit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxmlLoader object = new fxmlLoader();
        Pane view = object.getPage("SalesAnalyzedui");
        managerBorder.setCenter(view);
        styleSheet();

        btnAnalyzed.setStyle("-fx-background-color:#CCD1D9;"
                + "-fx-border-width:0 0 0 4;"
                + "-fx-border-color:white;");
    }

    @FXML
    private void Analyzesfxml(ActionEvent event) {
        SceneAnimation("SalesAnalyzedui");
        styleSheet();

        btnAnalyzed.setStyle("-fx-background-color:#CCD1D9;"
                + "-fx-border-width:0 0 0 4;"
                + "-fx-border-color:white;");
    }

    @FXML
    private void InventoryFxml(ActionEvent event) {
        SceneAnimation("Inventoryui");
        styleSheet();

        btnInventory.setStyle("-fx-background-color:#CCD1D9;"
                + "-fx-border-width:0 0 0 4;"
                + "-fx-border-color:white;");
    }

    @FXML
    private void MealsFxml(ActionEvent event) {
        SceneAnimation("Mealsui");
        styleSheet();
        btnMeals.setStyle(" -fx-background-color:#CCD1D9;"
                + "-fx-border-width:0 0 0 4;"
                + "-fx-border-color:white;");
    }

    @FXML
    private void StaffFxml(ActionEvent event) {
        SceneAnimation("Staffui");
        styleSheet();
        btnStaff.setStyle(" -fx-background-color:#CCD1D9;"
                + "-fx-border-width:0 0 0 4;"
                + "-fx-border-color:white;");
    }

    @FXML
    private void UpdatePasswordFxml(ActionEvent event) {
        SceneAnimation("UpdatePasswordui");
        styleSheet();
        btnUpdatePassword.setStyle(" -fx-background-color:#CCD1D9;"
                + "-fx-border-width:0 0 0 4;"
                + "-fx-border-color:white;");
    }

    public void SceneAnimation(String btnName) {
        //SetScene
        fxmlLoader object = new fxmlLoader();
        Pane view = object.getPage(btnName);
        //SetAnimation
        Scene scene = btnAnalyzed.getScene();
        view.translateXProperty().set(scene.getHeight());
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(view.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        //SetScene in borderPane
        managerBorder.setCenter(view);
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        
        if(alert.AlertController.showConfirmAlert("Quit", "Are you sure you want to quit!")){
            Platform.exit();
        }
        

    }
    

    public void styleSheet() {
        btnAnalyzed.setStyle("-fx-background-color:#AAB2BD;"
                + "-fx-border-width:0 0 0 0;"
                + "-fx-border-color:white;");
        btnInventory.setStyle("-fx-background-color:#AAB2BD;"
                + "-fx-border-width:0 0 0 0;"
                + "-fx-border-color:white;");
        btnMeals.setStyle("-fx-background-color:#AAB2BD;"
                + "-fx-border-width:0 0 0 0;"
                + "-fx-border-color:white;");
        btnStaff.setStyle("-fx-background-color:#AAB2BD;"
                + "-fx-border-width:0 0 0 0;"
                + "-fx-border-color:white;");
        btnUpdatePassword.setStyle("-fx-background-color:#AAB2BD;"
                + "-fx-border-width:0 0 0 0;"
                + "-fx-border-color:white;");
    }

}
