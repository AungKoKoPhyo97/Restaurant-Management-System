/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.meals;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.DBController;
import dataclass.Inventory;
import dataclass.Meals;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class MealsuiController implements Initializable {

    @FXML
    private JFXTextField txfmname;
    @FXML
    private JFXTextField txfmprice;
    @FXML
    private TextArea txaIngredients;
    @FXML
    private JFXButton mSave;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private VBox test;
    @FXML
    private JFXTextField txfsearch;
    @FXML
    private HBox tblhbox;
    @FXML
    private JFXTextField txfqty;
    @FXML
    private JFXButton mAdd;
    private TableView<Inventory> Ingredientstbl;
    private TableColumn<Inventory, Integer> coliid;
    private TableColumn<Inventory, String> coliname;
    private TableColumn<Inventory, Integer> coliqty;
    private TableColumn<Inventory, Double> coliprice;
    @FXML
    private JFXTextField mname;
    @FXML
    private JFXTextField mprice;
    @FXML
    private JFXTextField txfsearch1;
    @FXML
    private HBox tblhbox1;
    @FXML
    private JFXButton btnupdate;
    @FXML
    private JFXButton btncancel;

    private TableView<dataclass.Meals> view;

    private TableColumn<dataclass.Meals, Integer> colid;
    private TableColumn<dataclass.Meals, Integer> coltype;
    private TableColumn<dataclass.Meals, Integer> colname;
    private TableColumn<dataclass.Meals, Double> colprice;
    @FXML
    private JFXComboBox<String> cbType;
    @FXML
    private JFXComboBox<String> cbMType;
    @FXML
    private JFXButton btnupdate1;
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private VBox vMeals;
    @FXML
    private VBox vInventory;
    TableView<Inventory> tblinv = new TableView<>();
    TableView<Meals> tblmeals = new TableView<>();
    ObservableList<Inventory> invList;
    @FXML
    private JFXTextField txfMealsSearch;

    public void initialize(URL url, ResourceBundle rb) {
        txaIngredients.setEditable(false);
        loadIngreTbl();
        loadtbl();
        combox();
        Mcombox();
    }

    @FXML
    private boolean InsertData(ActionEvent event) {
        String sql = "insert into mealstbl (mname,mtype,mprice) values (?,?,?)";

        if (txfmname.getText().isEmpty() || txfmprice.getText().isEmpty() || cbType.getValue().isEmpty() || txaIngredients.getText().isEmpty()) {
            alert.AlertController.showWarningAlert("Warning", "Please insert data!");
        } else {
            try (Connection con = DBController.getConnection();) {
                PreparedStatement psmt = con.prepareCall(sql);
                psmt.setString(1, txfmname.getText().toLowerCase());
                psmt.setString(2, cbType.getValue().toString());
                psmt.setDouble(3, Double.parseDouble(txfmprice.getText()));
                if (dataclass.Meals.checkifmealsdataexist(txfmname.getText())) {
                    alert.AlertController.showErrorAlert("Error", "Data exists!");
                    return false;
                }
                psmt.execute();

                sql = "Select max(mid) from mealstbl";
                ResultSet rs = psmt.executeQuery(sql);

                while (rs.next()) {
                    String list[] = txaIngredients.getText().split("\n");
                    sql = "Insert into mealsdetailtbl (mdqty, iid, mid) values (?,?,?)";
                    for (String line : list) {
                        String arr[] = line.split(",");
                        psmt = con.prepareCall(sql);
                        psmt.setInt(1, Integer.parseInt(arr[2]));
                        psmt.setInt(2, Integer.parseInt(arr[0]));
                        psmt.setInt(3, rs.getInt(1));
                        psmt.execute();
                    }
                }
                handleCancelData(null);
                alert.AlertController.showInfoAlert("Success", "Data Successfully Inserted!");
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    @FXML
    private void handleCancelData(ActionEvent event) {

        txfmname.setText("");
        txfmprice.setText("");
        txaIngredients.setText("");
        txfsearch.setText("");
        txfqty.setText("");
        cbType.setValue(null);

    }

    @FXML
    private void handleAddIntoMeal(ActionEvent event) {
        if (Ingredientstbl.getSelectionModel().getSelectedItem() == null) {
            alert.AlertController.showWarningAlert("Nothing selected", "Please select Ingredients!");
        }
        if (cbType.getValue() == null) {
            alert.AlertController.showWarningAlert("Empty", "Please insert type!");
        }
        if (txfqty.getText().isEmpty()) {
            alert.AlertController.showWarningAlert("Empty", "Please insert quantity!");
        }
        if (!txfqty.getText().isEmpty() && Ingredientstbl.getSelectionModel().getSelectedItem() != null) {
            Inventory i = Ingredientstbl.getSelectionModel().getSelectedItem();
            txaIngredients.appendText(i.getIid() + "," + i.getIname() + "," + txfqty.getText() + "\n");
            txfqty.setText("");
        }

    }

    private void loadIngreTbl() {
        Ingredientstbl = new TableView<>();

        coliid = new TableColumn<>("Id");
        coliid.setPrefWidth(60);
        coliid.setCellValueFactory(new PropertyValueFactory("iid"));

        coliname = new TableColumn<>("Name");
        coliname.setPrefWidth(100);
        coliname.setCellValueFactory(new PropertyValueFactory("iname"));

        coliqty = new TableColumn<>("Quantity");
        coliqty.setPrefWidth(100);
        coliqty.setCellValueFactory(new PropertyValueFactory("iqty"));

        coliprice = new TableColumn<>("Price");
        coliprice.setPrefWidth(100);
        coliprice.setCellValueFactory(new PropertyValueFactory("iprice"));

        Ingredientstbl.getColumns().addAll(coliid, coliname, coliqty, coliprice);
        tblhbox.getChildren().clear();
        tblhbox.getChildren().add(Ingredientstbl);

        ObservableList<Inventory> list = Inventory.getAllInventoryData();
        Ingredientstbl.getItems().setAll(list);

        FilteredList<Inventory> filter = new FilteredList<>(list, e -> true);
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
            SortedList<Inventory> sl = new SortedList<>(filter);
            sl.comparatorProperty().bind(Ingredientstbl.comparatorProperty());
            Ingredientstbl.getItems().clear();
            Ingredientstbl.getItems().addAll(sl);
        });

    }

    @FXML
    private void mupdate(ActionEvent event) {
        String sql = "update mealstbl set mname=? ,mtype=?, mprice=? where mid=?";
        try (Connection con = database.DBController.getConnection()) {
            PreparedStatement ps = con.prepareCall(sql);
            if (view.getSelectionModel().getSelectedItem() == null) {
                alert.AlertController.showWarningAlert("Nothing selected", "Please select Ingredients!");
            }
            if (mname.getText().isEmpty() || mprice.getText().isEmpty() || cbMType.getValue().isEmpty()) {
                alert.AlertController.showWarningAlert("Empty", "Please insert Data!");
            }
//            (!txfiname.getText().isEmpty()||!txfiprice.getText().isEmpty()||!txfiquantity.getText().isEmpty() && invtbl.getSelectionModel().getSelectedItem()!= null){
            dataclass.Meals i = view.getSelectionModel().getSelectedItem();

            ps.setString(1, mname.getText());
            ps.setString(2, cbMType.getValue().toString());
            ps.setDouble(3, Double.parseDouble(mprice.getText()));
            ps.setInt(4, i.getMid());

            ps.execute();
            loadtbl();
            alert.AlertController.showInfoAlert("Success", "Update Meals!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mname.setText("");
        mprice.setText("");
        cbMType.setValue(null);

    }

    public void loadtbl() {

        view = new TableView<>();

        colid = new TableColumn<>("ID");
        colid.setPrefWidth(60);
        colid.setCellValueFactory(new PropertyValueFactory<>("mid"));

        colname = new TableColumn<>("Name");
        colname.setPrefWidth(100);
        colname.setCellValueFactory(new PropertyValueFactory<>("mname"));

        coltype = new TableColumn<>("Type");
        coltype.setPrefWidth(100);
        coltype.setCellValueFactory(new PropertyValueFactory<>("mtype"));

        colprice = new TableColumn<>("Price");
        colprice.setPrefWidth(100);
        colprice.setCellValueFactory(new PropertyValueFactory<>("mprice"));

        view.getColumns().addAll(colid, colname, coltype, colprice);
        tblhbox1.getChildren().clear();
        tblhbox1.getChildren().add(view);

        colid.setPrefWidth(30);
        colname.setPrefWidth(100);
        colprice.setPrefWidth(60);

        ObservableList<Meals> list = Meals.getAllMealsData();
        view.getItems().setAll(list);

        FilteredList<Meals> filter = new FilteredList<>(list, e -> true);
        txfsearch1.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((meals) -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String s = txfsearch1.getText().toLowerCase();
                if (meals.getMname().toLowerCase().contains(s)) {
                    return true;
                }
                return false;

            });
            SortedList<Meals> sl = new SortedList<>(filter);
            sl.comparatorProperty().bind(view.comparatorProperty());
            view.getItems().clear();
            view.getItems().addAll(sl);
        });
        view.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            Meals i = view.getSelectionModel().getSelectedItem();
            mname.setText(i.getMname());
            cbMType.setValue(i.getMtype());
            mprice.setText(i.getMprice() + "");

        });
    }

    @FXML
    private void msearch(ActionEvent event) {

    }

    @FXML
    private void mCancel(ActionEvent event) {
        mname.setText("");
        mprice.setText("");
        cbMType.setValue(null);
        view.getSelectionModel().clearSelection();
    }

    public void combox() {
        cbType.getItems().addAll("Breakfast", "Lunch", "Snack", "Drink");
    }

    public void Mcombox() {
        cbMType.getItems().addAll("Breakfast", "Lunch", "Snack", "Drink");

    }

    @FXML
    private void btnRefresh(ActionEvent event) {
        ObservableList<dataclass.Meals> list = dataclass.Meals.getAllMealsData();
        view.getItems().clear();
        view.getItems().setAll(list);
    }

}
