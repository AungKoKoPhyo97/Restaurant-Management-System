/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.inventory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.control.SelectedCellsMap;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author MCMITS5-055-USER2
 */
public class InventoryuiController implements Initializable {

    @FXML
    private JFXTextField txfiname;
    @FXML
    private JFXTextField txfiquantity;
    @FXML
    private JFXTextField txfiprice;
    @FXML
    private JFXTextField txfsearch;
    @FXML
    private HBox tblhbox;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnUpdate;
    private TableView<dataclass.Inventory> invtbl;
    private TableColumn<dataclass.Inventory, Integer> colid;
    private TableColumn<dataclass.Inventory, String> colname;
    private TableColumn<dataclass.Inventory, Double> colprice;
    private TableColumn<dataclass.Inventory, Integer> colqty;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

        loadtbl();

    }

    @FXML
    private boolean savedata(ActionEvent event) {
        if (dataclass.Inventory.checkifinventorydataexist(txfiname.getText())) {
            alert.AlertController.showErrorAlert("Error", "Data exists!");
            return false;
        }
        String sql = "insert into inventorytbl (iname,iqty,iprice) values(?,?,?)";
        if (txfiname.getText().isEmpty() || txfiprice.getText().isEmpty()) {
            alert.AlertController.showWarningAlert("Warning", "Please insert data!");
        } else {
            try (Connection con = database.DBController.getConnection()) {
                PreparedStatement ps = con.prepareCall(sql);
                ps.setString(1, txfiname.getText().toLowerCase());
                ps.setInt(2, Integer.parseInt(txfiquantity.getText()));
                ps.setDouble(3, Double.parseDouble(txfiprice.getText()));
                ps.execute();

                cancel(null);
                alert.AlertController.showInfoAlert("Success", "Data Successfully Inserted!");
                loadtbl();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                alert.AlertController.showWarningAlert("Warning", "Check your data!");
            }
        }
        return false;
    }

    @FXML
    private void cancel(ActionEvent event) {
        txfiname.setText("");
        txfiquantity.setText("");
        txfiprice.setText("");
        invtbl.getSelectionModel().clearSelection();
    }

    @FXML
    private void updatedata(ActionEvent event) {
        String sql = "update inventorytbl set iname=? , iqty=?, iprice=? where iid=?";
        try (Connection con = database.DBController.getConnection()) {
            PreparedStatement ps = con.prepareCall(sql);
            if (invtbl.getSelectionModel().getSelectedItem() == null) {
                alert.AlertController.showWarningAlert("Nothing selected", "Please select Ingredients!");
            }
            if (txfiname.getText().isEmpty() || txfiprice.getText().isEmpty() || txfiquantity.getText().isEmpty()) {
                alert.AlertController.showWarningAlert("Empty", "Please insert quantity!");
            }
//            (!txfiname.getText().isEmpty()||!txfiprice.getText().isEmpty()||!txfiquantity.getText().isEmpty() && invtbl.getSelectionModel().getSelectedItem()!= null){
            dataclass.Inventory i = invtbl.getSelectionModel().getSelectedItem();

            ps.setString(1, txfiname.getText());
            ps.setInt(2, Integer.parseInt(txfiquantity.getText()));
            ps.setDouble(3, Double.parseDouble(txfiprice.getText()));
            ps.setInt(4, i.getIid());

            ps.execute();
            loadtbl();
            alert.AlertController.showInfoAlert("Success", "Data Successfully Updates!");
        } catch (Exception e) {
            e.printStackTrace();
            alert.AlertController.showWarningAlert("Warning", "Check your data!");
        }
        txfiname.setText("");
        txfiquantity.setText("");
        txfiprice.setText("");

    }

    public void loadtbl() {

        invtbl = new TableView<>();

//        invtbl.setFixedCellSize(20);
//        invtbl.setPrefWidth(20);
        colid = new TableColumn<>("ID");
        colid.setCellValueFactory(new PropertyValueFactory<>("iid"));

        colname = new TableColumn<>("Name");
        colname.setCellValueFactory(new PropertyValueFactory<>("iname"));

        colprice = new TableColumn<>("Price");
        colprice.setCellValueFactory(new PropertyValueFactory<>("iprice"));

        colqty = new TableColumn<>("Quantity");
        colqty.setCellValueFactory(new PropertyValueFactory<>("iqty"));

        invtbl.getColumns().addAll(colid, colname, colprice, colqty);
        invtbl.getItems().setAll(dataclass.Inventory.getAllInventoryData());
        tblhbox.getChildren().clear();
        tblhbox.getChildren().add(invtbl);

        colid.setPrefWidth(30);
        colname.setPrefWidth(100);
        colprice.setPrefWidth(60);
        colqty.setPrefWidth(60);

        ObservableList<dataclass.Inventory> list = dataclass.Inventory.getAllInventoryData();
        invtbl.getItems().setAll(list);

        FilteredList<dataclass.Inventory> filter = new FilteredList<>(list, e -> true);
        txfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((inventory) -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String s = txfsearch.getText().toLowerCase();
                if (inventory.getIname().toLowerCase().contains(s)) {
                    return true;
                }
                return false;

            });
            SortedList<dataclass.Inventory> sl = new SortedList<>(filter);
            sl.comparatorProperty().bind(invtbl.comparatorProperty());
            invtbl.getItems().clear();
            invtbl.getItems().addAll(sl);
        });
        invtbl.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            dataclass.Inventory i = invtbl.getSelectionModel().getSelectedItem();
            txfiname.setText(i.getIname());
            txfiprice.setText(i.getIprice() + "");
            txfiquantity.setText(i.getIqty() + "");
        });
    }

}
