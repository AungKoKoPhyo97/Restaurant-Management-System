/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.salesanalyzed;

import alert.AlertController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import dataclass.SalesDailyDetailView;
import dataclass.SalesWeekly;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class SalesAnalyzeduiController implements Initializable {

    @FXML
    private HBox hDaily;
    @FXML
    private VBox vDailyProcess;
    @FXML
    private JFXDatePicker dpDailyDate;
    @FXML
    private HBox hDailyBtn;
    @FXML
    private JFXButton btnDailyCancel;
    @FXML
    private JFXButton btnDailySearch;
    @FXML
    private VBox vDailyTbl;
    @FXML
    private JFXDatePicker dpWeeklyStart;
    @FXML
    private JFXDatePicker dpWeeklyEnd;
    @FXML
    private HBox hWeeklyTbl;
    @FXML
    private HBox hWeeklyBtn;
    @FXML
    private JFXButton btnWeeklyCancel;
    @FXML
    private JFXButton btnWeeklySearch;
    private TableView<SalesDailyDetailView> tblDailySales = new TableView<>();
    private TableView<SalesWeekly> tblWeeklySales = new TableView<>();
    @FXML
    private LineChart<String, Double> chartSales;
    @FXML
    private JFXButton btnLoadChart;
    @FXML
    private JFXComboBox<String> cbDateSelect;
    @FXML
    private JFXButton btnToday;
    @FXML
    private NumberAxis AxisY;
    @FXML
    private CategoryAxis AxisX;
    @FXML
    private PieChart pieChart;
    private ObservableList<PieChart.Data> list;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

        comboBox();
        try {
            dailySales();
            weeklySales();
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        loadPiechart();
        pieChart.setData(list);

    }

    public void dailySales() throws SQLException {
        tblDailySales = new TableView<>();
        TableColumn<SalesDailyDetailView, String> colmname = new TableColumn<>("Name");
        colmname.setPrefWidth(90);
        colmname.setCellValueFactory(new PropertyValueFactory("mname"));

        TableColumn<SalesDailyDetailView, Integer> colslid = new TableColumn<>("Qty");
        colslid.setCellValueFactory(new PropertyValueFactory("slid"));

        TableColumn<SalesDailyDetailView, Double> colmprice = new TableColumn<>("Price");
        colmprice.setCellValueFactory(new PropertyValueFactory("mprice"));

        TableColumn<SalesDailyDetailView, String> coldate = new TableColumn<>("Date");
        coldate.setCellValueFactory(new PropertyValueFactory("sldate"));

        tblDailySales.getColumns().addAll(colmname, colslid, colmprice, coldate);

        vDailyTbl.getChildren().clear();
        vDailyTbl.getChildren().add(tblDailySales);

    }

    @FXML
    private void DailySalesCancel(ActionEvent event) {
        dpDailyDate.setValue(null);
        unselectRow();

//        tblDailySales.getItems().clear();
    }

    private void DailyDateCheck() {
        if (dpDailyDate.getValue() == null) {
            AlertController.showWarningAlert("Check your date!", "Date not select");

        }
    }

    @FXML
    private void DailyTodaySales(ActionEvent event) {
        dpDailyDate.setValue(LocalDate.now());
    }

    @FXML
    private void DailySalesSearch(ActionEvent event) {
        LocalDate DailyDate = dpDailyDate.getValue();

        if (DailyDate.isAfter(LocalDate.now())) {
            AlertController.showWarningAlert("Warning", "Date must be Before today!");
        } else {

            ObservableList<SalesDailyDetailView> list = SalesDailyDetailView.getDailySales(DailyDate.plusDays(1));
            tblDailySales.getItems().clear();
            tblDailySales.setItems(list);
        }

    }

    public void loadPiechart() {
        list = FXCollections.observableArrayList();

        String sql = "SELECT NAME,Total FROM `dailysalesview` ORDER BY Total DESC LIMIT 3";
        try (Connection con = database.DBController.getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                list.addAll(new PieChart.Data(rs.getString(1), rs.getDouble(2)));

            }
//            chartSales.getData().add(l);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void weeklySales() throws SQLException {
        tblWeeklySales = new TableView<>();
        TableColumn<SalesWeekly, String> colsldate = new TableColumn<>("Date");
        colsldate.setPrefWidth(100);
        colsldate.setCellValueFactory(new PropertyValueFactory("sldate"));

        TableColumn<SalesWeekly, Integer> colslqty = new TableColumn<>("Qty");
        colslqty.setCellValueFactory(new PropertyValueFactory("slqty"));

        TableColumn<SalesWeekly, Double> colslprice = new TableColumn<>("Price");
        colslprice.setCellValueFactory(new PropertyValueFactory("slprice"));

        tblWeeklySales.getColumns().addAll(colsldate, colslqty, colslprice);
//        tblWeeklySales.getPrefHeight();
        hWeeklyTbl.getChildren().clear();
        hWeeklyTbl.getChildren().add(tblWeeklySales);

    }

    @FXML
    private void weeklySaleCancel(ActionEvent event) {
        dpWeeklyStart.setValue(null);
        dpWeeklyEnd.setValue(null);
        cbDateSelect.setValue(null);
        chartSales.getData().clear();
//        tblWeeklySales.getItems().clear();
        btnLoadChart.setDisable(false);
        unselectRow();

    }

    @FXML
    private void weeklySalesSearch(ActionEvent event) {
        LocalDate weeklySDate = dpWeeklyStart.getValue();
        LocalDate weeklyEDate = dpWeeklyEnd.getValue();

        if (weeklyEDate.isAfter(LocalDate.now())) {
            AlertController.showWarningAlert("Warning", "Date must be Before today!");
        } else {
            DateCheck();
            ObservableList<SalesWeekly> list = SalesWeekly.getTotalSales(weeklySDate.plusDays(1), weeklyEDate.plusDays(1));
            tblWeeklySales.getItems().clear();
            tblWeeklySales.setItems(list);
        }
    }

    @FXML
    private void LoadChart(ActionEvent event) {
        if (tblWeeklySales.getItems().isEmpty() || dpWeeklyStart.getValue() == null || dpWeeklyEnd.getValue() == null) {
            AlertController.showErrorAlert("No Items", "Try searching the Items fiest!");
        } else {
            LocalDate weeklySDate = dpWeeklyStart.getValue().plusDays(1);
            LocalDate weeklyEDate = dpWeeklyEnd.getValue().plusDays(1);
            String sql = "SELECT sldate,SUM(slprice) FROM salestbl WHERE sldate BETWEEN ? AND ? GROUP BY sldate";
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            try (Connection con = database.DBController.getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.setDate(1, Date.valueOf(weeklySDate));
                pstm.setDate(2, Date.valueOf(weeklyEDate));
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getDouble(2)));
                }
                chartSales.getData().add(series);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            btnLoadChart.setDisable(true);
            AxisX.setAnimated(false);
            AxisY.setAnimated(true);
            chartSales.setAnimated(true);
            chartSales.setLegendVisible(false);
//            chartSales.setEffect(new DropShadow(20, Color.BLACK));
        }

    }

    private void comboBox() {
        cbDateSelect.getItems().addAll("Day", "Week", "Month");
    }

    @FXML
    private void cbDateSelect(ActionEvent event) {
        if (cbDateSelect.getValue() == "Day") {
            dpWeeklyStart.setValue(LocalDate.now());
            dpWeeklyEnd.setValue(LocalDate.now());

        } else if (cbDateSelect.getValue() == "Week") {
            dpWeeklyStart.setValue(LocalDate.now().minusWeeks(1));
            dpWeeklyEnd.setValue(LocalDate.now());

        } else {
            dpWeeklyStart.setValue(LocalDate.now().minusMonths(1));
            dpWeeklyEnd.setValue(LocalDate.now());
        }

    }

    private void DateCheck() {
        if (dpWeeklyStart.getValue() == null || dpWeeklyEnd.getValue() == null) {
            AlertController.showWarningAlert("Date not Select", "Date not select");
        } else if (dpWeeklyStart.getValue().isAfter(LocalDate.now())) {
            AlertController.showWarningAlert("Check your date!", "Date not select");
        }
    }

    public void unselectRow() {
        tblDailySales.getSelectionModel().clearSelection();
        tblWeeklySales.getSelectionModel().clearSelection();
    }

}
