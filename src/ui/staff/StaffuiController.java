/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.staff;

import alert.AlertController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dataclass.Staff;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class StaffuiController implements Initializable {

    @FXML
    private JFXTextField txt_name;
    @FXML
    private JFXTextField txt_nrc;
    @FXML
    private JFXTextField txt_phone;
    @FXML
    private JFXDatePicker dp_dob;
    @FXML
    private JFXTextField txt_address;
    @FXML
    private JFXTextField txt_salary;
    @FXML
    private JFXTextField txt_username;
    @FXML
    private JFXPasswordField txt_password;
    @FXML
    private JFXTextField txfSearchStaff;
    @FXML
    private VBox vStaffTable;
    @FXML
    private JFXButton btn_cancel;
    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXButton btn_update;

    private TableView<Staff> staffTbl;
    private Staff Staff;
    @FXML
    private JFXButton btnRefresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStaffTable();
        searchStaff();
    }

    public void initializeStaffTable() {
        staffTbl = new TableView<>();

        TableColumn<Staff, Integer> col_sid = new TableColumn<>("ID");
        col_sid.setCellValueFactory(new PropertyValueFactory("sid"));

        TableColumn<Staff, String> colname = new TableColumn<>("Name");
        colname.setCellValueFactory(new PropertyValueFactory("sname"));

        TableColumn<Staff, String> colnrc = new TableColumn<>("NRC");
        colnrc.setCellValueFactory(new PropertyValueFactory("snrc"));

        TableColumn<Staff, LocalDate> coldob = new TableColumn<>("DOB");
        coldob.setCellValueFactory(new PropertyValueFactory("sdob"));

        TableColumn<Staff, Double> colSalary = new TableColumn<>("Salary");
        colSalary.setCellValueFactory(new PropertyValueFactory("ssalary"));

        TableColumn<Staff, Integer> colph = new TableColumn<>("Phone");
        colph.setCellValueFactory(new PropertyValueFactory("sphone"));

        TableColumn<Staff, String> coladd = new TableColumn<>("Address");
        coladd.setCellValueFactory(new PropertyValueFactory("saddress"));

        TableColumn<Staff, String> colusername = new TableColumn<>("Username");
        colusername.setCellValueFactory(new PropertyValueFactory("susername"));

        TableColumn<Staff, String> colpassword = new TableColumn<>("Password");
        colpassword.setPrefWidth(83);
        colpassword.setCellValueFactory(new PropertyValueFactory("spassword"));

        staffTbl.getColumns().addAll(col_sid, colname, colnrc, coldob, colSalary, colph, coladd, colusername, colpassword);
        vStaffTable.getChildren().clear();
        vStaffTable.getChildren().add(staffTbl);
        getTextfromTable();
        getStaffs();

    }

    public void getStaffs() {

        ObservableList<Staff> list = Staff.getallStaffData();
        staffTbl.getItems().clear();
        staffTbl.setItems(list);
    }

    public void getTextfromTable() {
        staffTbl.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            Staff s = staffTbl.getSelectionModel().getSelectedItem();
            txt_name.setText(s.getSname());
            txt_nrc.setText(s.getSnrc());
            txt_phone.setText(s.getSphone() + "");
            dp_dob.setValue(s.getSdob());
            txt_address.setText(s.getSaddress());
            txt_salary.setText(s.getSsalary() + "");
            txt_username.setText(s.getSusername());
            txt_password.setText(s.getSpassword());

        });
    }

    @FXML
    private void handlebtnUpdate(javafx.event.ActionEvent event) {

        Staff st = staffTbl.getSelectionModel().getSelectedItem();
        if (st == null) {
            alert.AlertController.showWarningAlert("Warning", "Select data from table first!");

        } else {

            Staff staff = new Staff(st.getSid(), txt_name.getText(),
                    txt_nrc.getText(),
                    dp_dob.getValue(),
                    Integer.parseInt(txt_phone.getText()),
                    txt_address.getText(),
                    Double.parseDouble(txt_salary.getText()),
                    txt_username.getText(),
                    txt_password.getText());

            if (staff.updateStaffData(staff)) {
                AlertController.showInfoAlert("Success", "Staff is Updated!");
                staffTbl.getItems().clear();
                getStaffs();
            } else {
                AlertController.showErrorAlert("Error", "data can't Updadte!");
            }
        }
        cancelHandle();
    }

    @FXML
    private void handlebtnCancel(javafx.event.ActionEvent event
    ) {
        cancelHandle();
    }

    public void cancelHandle() {
        txt_name.setText("");
        txt_nrc.setText("");
        txt_phone.setText("");
        dp_dob.setValue(null);
        txt_address.setText("");
        txt_salary.setText("");
        txt_username.setText("");
        txt_password.setText("");
        txfSearchStaff.setText("");
//        staffTbl.getItems().clear();
        unselectRow();
    }

    @FXML
    private boolean handlebtnAdd(javafx.event.ActionEvent event
    ) {

        if (checkInput()) {
            Staff staff = new Staff(1, txt_name.getText(),
                    txt_nrc.getText(),
                    dp_dob.getValue(),
                    Integer.parseInt(txt_phone.getText()),
                    txt_address.getText(),
                    Double.parseDouble(txt_salary.getText()),
                    txt_username.getText(),
                    txt_password.getText());
            if (dataclass.Staff.checkifStaffdataexist(txt_nrc.getText())) {
                alert.AlertController.showErrorAlert("Error", "Data exists!");
                return false;
            }
            if (!staff.saveStaffData(staff)) {
                AlertController.showInfoAlert("Success", "New Staff is added!");
                staffTbl.getItems().clear();
                getStaffs();
                cancelHandle();
            } else {
                AlertController.showErrorAlert("Error", "data can't added!");
            }
        }
        return false;
    }

    @FXML
    private void SearchStaff(ActionEvent event) {
        String staffName = txfSearchStaff.getText();
        if (!staffName.isEmpty()) {
            ObservableList<Staff> list = Staff.getStaffsbyName(staffName);
            staffTbl.getItems().clear();
            staffTbl.setItems(list);
        } else {
            getStaffs();
        }

    }

    public boolean checkInput() {
        if (txt_name.getText().isEmpty()
                || (!txt_nrc.getText().matches("\\d{1,2}/[a-zA-Z]{6,8}\\([A-Z]\\)\\d{6}"))
                || dp_dob.getValue() == null
                || txt_phone.getText().isEmpty()
                || txt_address.getText().isEmpty()
                || txt_salary.getText().isEmpty()
                || txt_username.getText().isEmpty()
                || txt_password.getText().isEmpty()) {
            AlertController.showWarningAlert("Warning", "Check your Data!");
            return false;
        }
        return true;
    }

    public void unselectRow() {
        staffTbl.getSelectionModel().clearSelection();
    }

    @FXML
    private void refreshTbl(ActionEvent event) {
        getStaffs();
    }

    public void searchStaff() {
        ObservableList<Staff> list = Staff.getallStaffData();
        staffTbl.getItems().setAll(list);

        FilteredList<Staff> filter = new FilteredList<>(list, e -> true);
        txfSearchStaff.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((inventory) -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String s = txfSearchStaff.getText().toLowerCase();
                if (inventory.getSname().contains(s)) {
                    return true;
                }
                return false;

            });
            SortedList<Staff> sl = new SortedList<>(filter);
            sl.comparatorProperty().bind(staffTbl.comparatorProperty());
            staffTbl.getItems().clear();
            staffTbl.getItems().addAll(sl);
        });

    }
}
