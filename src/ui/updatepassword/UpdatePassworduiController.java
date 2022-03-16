/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.updatepassword;

import alert.AlertController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DBController;
import dataclass.Admin;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class UpdatePassworduiController implements Initializable {

    @FXML
    private JFXPasswordField txfOldPassword;
    @FXML
    private JFXPasswordField txfNewPassword;
    @FXML
    private JFXPasswordField txfNewPasswordRe;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnChange;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cancelChange(ActionEvent event) {
        txfOldPassword.setText("");
        txfNewPassword.setText("");
        txfNewPasswordRe.setText("");

    }

    @FXML
    private void ChangePassword(ActionEvent event) {
        if (txfNewPassword.getText().isEmpty() || txfNewPasswordRe.getText().isEmpty() || txfOldPassword.getText().isEmpty()) {
            AlertController.showWarningAlert("Error", "Insert Data!");
            return;
        }
        if (txfNewPassword.getText().equals(txfNewPasswordRe.getText())) {
            String sql = "UPDATE admintbl set apassword=? where 1";
            try (Connection con = DBController.getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.setString(1, txfNewPasswordRe.getText());
                pstm.executeUpdate();
                AlertController.showInfoAlert("Success", "Password Updated Successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            AlertController.showErrorAlert("Error", "Something went wrong and password can't be change!");

        }

    }

}
