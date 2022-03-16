/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.sales.inventory;

import alert.AlertController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dataclass.Inventory;
import dataclass.Meals;
import java.net.URL;
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
public class SalesInventoryuiController implements Initializable {

    @FXML
    private JFXTextField txfSearch;
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private JFXButton btnCancel;

    TableView<Inventory> tblinv = new TableView<>();
    TableView<Meals> tblmeals = new TableView<>();
    @FXML
    private VBox vbmealstbl;
    @FXML
    private VBox vbinvtable;
    @FXML
    private JFXComboBox<String> cbType;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        createInvTbl();
        createMealsTbl();
        txfSearch.setEditable(false);
        comboBoxType();
        tblmeals.setEditable(false);
        tblinv.setEditable(false);
    }

    @FXML
    private void refreshTbl(ActionEvent event) {
        getInventory();
        getMeals();
        tblinv.setVisible(true);
        tblmeals.setVisible(true);
        txfSearch.setEditable(false);
        cbType.setValue(null);

    }

    @FXML
    private void CancelAction(ActionEvent event) {
        txfSearch.setText("");
        cbType.setValue(null);
        tblinv.getSelectionModel().clearSelection();
        tblmeals.getSelectionModel().clearSelection();
        tblinv.setVisible(true);
        tblmeals.setVisible(true);
        txfSearch.setEditable(false);

    }

    private void createInvTbl() {
        tblinv = new TableView<>();

        TableColumn<Inventory, Integer> colid = new TableColumn<>("ID");
        colid.setPrefWidth(100);
        colid.setCellValueFactory(new PropertyValueFactory("iid"));

        TableColumn<Inventory, String> coliname = new TableColumn<>("Name");
        coliname.setPrefWidth(160);
        coliname.setCellValueFactory(new PropertyValueFactory("iname"));

        TableColumn<Inventory, Integer> coliqty = new TableColumn<>("Quantity");
        coliqty.setPrefWidth(100);
        coliqty.setCellValueFactory(new PropertyValueFactory("iqty"));

        TableColumn<Inventory, Double> coliprice = new TableColumn<>("Price");
        coliprice.setPrefWidth(100);
        coliprice.setCellValueFactory(new PropertyValueFactory("iprice"));

        tblinv.getColumns().addAll(colid, coliname, coliqty, coliprice);
        vbinvtable.getChildren().clear();
        vbinvtable.getChildren().add(tblinv);
        getInventory();
    }

    public void createMealsTbl() {
        tblmeals = new TableView<>();
        TableColumn<Meals, Integer> colmd = new TableColumn<>("ID");
        colmd.setPrefWidth(100);
        colmd.setCellValueFactory(new PropertyValueFactory("mid"));

        TableColumn<Meals, String> colmname = new TableColumn<>("Name");
        colmname.setPrefWidth(130);
        colmname.setCellValueFactory(new PropertyValueFactory("mname"));

        TableColumn<Meals, String> colmtype = new TableColumn<>("Type");
        colmtype.setPrefWidth(100);
        colmtype.setCellValueFactory(new PropertyValueFactory("mtype"));

        TableColumn<Meals, Double> colmprice = new TableColumn<>("Price");
        colmprice.setPrefWidth(100);
        colmprice.setCellValueFactory(new PropertyValueFactory("mprice"));

        tblmeals.getColumns().addAll(colmd, colmname, colmtype, colmprice);
        vbmealstbl.getChildren().clear();
        vbmealstbl.getChildren().add(tblmeals);
        getMeals();

    }

    public void getInventory() {
        ObservableList<Inventory> list = Inventory.getAllInventoryData();
        tblinv.getItems().clear();
        tblinv.setItems(list);
    }

    public void getMeals() {
        ObservableList<Meals> list = Meals.getAllMealsData();
        tblmeals.getItems().clear();
        tblmeals.setItems(list);
    }

    public void searchInventory() {
        ObservableList<Inventory> list = Inventory.getAllInventoryData();
        tblinv.getItems().clear();
        tblinv.getItems().setAll(list);
        FilteredList<Inventory> filter = new FilteredList<>(list, e -> true);
        txfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((inventory) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String s = txfSearch.getText().toLowerCase();
                if (inventory.getIname().contains(s)) {
                    return true;
                }
                return false;
            });
            SortedList<Inventory> sl = new SortedList<>(filter);
            sl.comparatorProperty().bind(tblinv.comparatorProperty());
            tblinv.getItems().clear();
            tblinv.getItems().addAll(sl);
        });
    }

    public void searchMeals() {
        ObservableList<Meals> list = Meals.getAllMealsData();
        tblmeals.getItems().clear();
        tblmeals.getItems().setAll(list);
        FilteredList<Meals> filter = new FilteredList<>(list, e -> true);
        txfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Meals) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String s = txfSearch.getText().toLowerCase();
                if (Meals.getMname().contains(s)) {
                    return true;
                }
                return false;
            });
            SortedList<Meals> sl = new SortedList<>(filter);
            sl.comparatorProperty().bind(tblmeals.comparatorProperty());
            tblmeals.getItems().clear();
            tblmeals.getItems().addAll(sl);
        });
    }

    private void comboBoxType() {
        cbType.getItems().addAll("Inventory", "Meals");
    }

    @FXML
    private void TypeSearch(ActionEvent event) {
        if (cbType.getValue() == "Inventory") {
            txfSearch.setEditable(true);
            txfSearch.setText("");
            searchInventory();
            tblmeals.setVisible(false);
            tblinv.setVisible(true);

            return;
        }
        if (cbType.getValue() == "Meals") {
            txfSearch.setEditable(true);
            txfSearch.setText("");
            searchMeals();
            tblinv.setVisible(false);
            tblmeals.setVisible(true);

            return;
        }
    }

}
