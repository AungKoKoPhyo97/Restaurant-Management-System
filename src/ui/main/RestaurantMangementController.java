/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.main;

import dataclass.Staff;
import alert.AlertController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DBController;
import dataclass.Admin;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.sales.salesprocess.SalesProcessuiController;
import ui.sales.staff.StaffSalesuiController;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class RestaurantMangementController implements Initializable {

    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXTextField txfUsername;
    @FXML
    private JFXPasswordField txfPassword;
    FXMLLoader loader;

    int sid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txfPassword.setOnAction(e -> {
            try {
                HandleLogin(null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    @FXML
    private void HandleCancel(ActionEvent event) {
        txfPassword.setText("");
        txfUsername.setText("");
    }

    @FXML
    private void HandleLogin(ActionEvent event) throws IOException {
        String username = txfUsername.getText();
        String password = txfPassword.getText();
        String Nrc;

        Parent root = null;
        String sql = "SELECT apassword FROM admintbl";

        try (Connection con = DBController.getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Admin m = new Admin(rs.getString(1));
                if (username.equals("manager") && password.equals(rs.getString(1))) {
                    root = FXMLLoader.load(getClass().getResource("/ui/manager/Managerui.fxml"));
                    AlertController.showInfoAlert("Success", "Login Success!");
                    getWindow(root);
                    return;
                } else {
                    if (username.isEmpty() || password.isEmpty()) {
                        AlertController.showWarningAlert("Warning", "Insert your username & password!");
                        return;
                    }
                    String sql1 = "SELECT sid,snrc,spassword from stafftbl where susername='" + txfUsername.getText() + "'";
                    PreparedStatement pstm1 = con.prepareStatement(sql1);
                    {
                        ResultSet rs1 = pstm1.executeQuery();
                        while (rs1.next()) {
                            sid = rs1.getInt(1);
                            Nrc = rs1.getString(2);

                            if (password.equals(rs1.getString(3))) {
                                loader = new FXMLLoader(getClass().getResource("/ui/sales/staff/StaffSalesui.fxml"));
                                root = loader.load();
                                SalesProcessuiController.sid = sid;
                                System.out.println(sid);
                                StaffSalesuiController staffSales = loader.getController();
                                staffSales.setText(sid, username, Nrc);
                                AlertController.showInfoAlert("Success", "Login Success!");
                                getWindow(root);
                                return;
                            } else {
                                AlertController.showErrorAlert("Error", "Check your Username & Password!");
                                return;
                            }
                        }

                    }
                    AlertController.showErrorAlert("Error", "Check your Username & Password!");
                    return;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void getWindow(Parent pane) throws IOException {
        Stage st = (Stage) txfPassword.getScene().getWindow();
        st.setScene(new Scene(pane, 1250, 600));
        st.setTitle("Nino Restaurant");
        st.setResizable(true);
        st.centerOnScreen();
        st.getIcons().add(new Image("/resources/image/title.png"));
        st.show();

    }

}
