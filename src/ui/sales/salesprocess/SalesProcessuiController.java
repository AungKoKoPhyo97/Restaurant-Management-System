/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.sales.salesprocess;

import ui.main.RestaurantMangementController;
import alert.AlertController;
import com.jfoenix.controls.JFXButton;
import database.DBController;
import dataclass.Inventory;
import dataclass.Meals;
import dataclass.MealsDetail;
import dataclass.Sales;
import dataclass.SalesDailyDetailView;
import dataclass.SalesDetailView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javax.print.PrintService;
import ui.sales.staff.StaffSalesuiController;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class SalesProcessuiController implements Initializable {

    public static int sid;

    @FXML
    private HBox tblhbox;
    @FXML
    private JFXButton btnOrder;
    @FXML
    private JFXButton btnCancel;
    private TableView<SalesDetailView> salestbl;
//    private TableColumn<SalesDetailView, Integer> colid;
    private TableColumn<SalesDetailView, String> colname;
    private TableColumn<SalesDetailView, String> coltype;
    private TableColumn<SalesDetailView, Integer> colqty;
    private TableColumn<SalesDetailView, Double> colprice;
//    static ObservableList<SalesDetailView> listmid = FXCollections.observableArrayList();

    private TableRow rowtotal;
    @FXML
    private HBox btnhbox;
    private Date d;
    private String name;
//    private SalesDetailView sdv;
    private int btncount = 0;
    private int count[];
    private int i = 0;
    public ObservableList<SalesDetailView> list = FXCollections.observableArrayList();
    @FXML
    private JFXButton btnBreakfast;
    @FXML
    private JFXButton btnLunch;
    @FXML
    private JFXButton btnSnacks;
    @FXML
    private JFXButton btnDrink;
    private int Sid;
    @FXML
    private HBox btnhbox1;
    @FXML
    private HBox btnhbox2;
    @FXML
    private HBox btnhbox3;
    @FXML
    private VBox vboxbtn;

    public int getSid() {
        return Sid;
    }

    public void setSid(int Sid) {
        this.Sid = Sid;
    }
    static ObservableList slist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadsalesTbl();
//        loadbutton();

    }

    @FXML

    private void handleOrderButton(ActionEvent event) throws IOException {

        if (!list.isEmpty()) {
            if (orderProcess() && subtractInventory()) {
                alert.AlertController.showInfoAlert("Success", "Order Placed!");
                TextArea txaprint = new TextArea();
//                txaprint.setFont(Font.font("Monospaced", 13));
                txaprint.setText("********* Nino Restaurant *********");
                txaprint.appendText("\n"+String.format("%-20s%-3s%10s","Name","Qty","Cost"));
                 txaprint.appendText("\n"+"==================================");
                int totalprice = 0;
                for (SalesDetailView sd : list){
                    int quantityprice = 0;
                    for (int i=0;i<sd.getQty();i++){
                        quantityprice += sd.getPrice();
                    }
                     totalprice += quantityprice;   
                    txaprint.appendText("\n"+String.format("%-20s%-3s%10s", sd.getName(),sd.getQty(),quantityprice));
                }
                txaprint.appendText("\n"+"==================================");
                txaprint.appendText("\n"+String.format("%23s%10s", "Total : ",Integer.toString(totalprice)));
                System.out.println(txaprint.getText());
                print(txaprint);
                
                list.clear();
            } else {
                alert.AlertController.showErrorAlert("Error", "Something went wrong!");
            }
        } else {
            AlertController.showWarningAlert("Warming", "Choosen meals!");
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        salestbl.getItems().clear();
        hboxClean();
        list.clear();
        for (int i = 0; i < count.length; i++) {
            count[i] = 1;
        }
        i = 0;
    }

    private void loadsalesTbl() {

        salestbl = new TableView();

        colname = new TableColumn("Name");
        colname.setCellValueFactory(new PropertyValueFactory("name"));

        coltype = new TableColumn("Type");
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
//        coltype.setPrefWidth(30);

        colqty = new TableColumn("QTY");
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
//        colqty.setPrefWidth(30);

        colprice = new TableColumn("Price");
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));

//        salestbl.setPrefWidth(212);
        salestbl.getColumns().addAll(colname, coltype, colqty, colprice);

        tblhbox.getChildren().clear();
        tblhbox.getChildren().add(salestbl);

    }

    private void loadbutton(String sql) {
        String name;
        int total = 0;
        hboxClean();

        btnhbox.getChildren().clear();

        try (Connection con = DBController.getConnection()) {
            Statement stmt = con.prepareCall(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                name = rs.getString(1);
                JFXButton btn = new JFXButton(name);
                btn.setStyle("-fx-border-color: black;"
                        + "-fx-border-width:2;"
                        + "-fx-border-radius:5;"
                        + "-fx-font-size:12px;"
                );
                total++;
                btn.setPrefSize(100, 100);

                btn.setOnAction((ActionEvent e) -> {
                    int index = -1;
                    String sql1 = "select mid,mname,mtype,mprice from mealstbl where mname = '" + btn.getText() + "'";
                    try (Connection con1 = DBController.getConnection()) {
                        Statement stme = con1.prepareCall(sql1);
                        ResultSet rs1 = stme.executeQuery(sql1);
                        while (rs1.next()) {

                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getName().equalsIgnoreCase(btn.getText())) {
                                    index = i;
                                }
                            }
                            if (index != -1) {
                                list.set(index, new SalesDetailView(rs1.getInt(1), rs1.getString(2), rs1.getString(3), ++count[index], rs1.getInt(4) * count[index]));
                            } else {
                                count[i] = 1;
                                SalesDetailView sd = new SalesDetailView(rs1.getInt(1), rs1.getString(2), rs1.getString(3), count[i], rs1.getInt(4));

                                list.add(sd);
                                i++;
                            }
                        }
                        salestbl.getItems().clear();
                        salestbl.getItems().addAll(list);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });
                btnHbox(btn, total);
                btncount++;
                count = new int[btncount];
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean subtractInventory() {

        String sql = "SELECT iid,mdqty FROM mealsdetailtbl where mid=?";

        try (Connection con = DBController.getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            for (SalesDetailView salesDetailView : list) {
                pstm.setInt(1, salesDetailView.getId());
                ResultSet rs = pstm.executeQuery();
                int mdqty = 0;
                int invqty = 0;

                while (rs.next()) {
                    int iid = rs.getInt(1);
                    mdqty = rs.getInt(2);
                    String sql1 = "SELECT iqty from inventorytbl where iid='" + iid + "'";
                    PreparedStatement pstm1 = con.prepareStatement(sql1);
                    ResultSet rs1 = pstm1.executeQuery();

                    while (rs1.next()) {
                        invqty = rs1.getInt(1);
                    }

                    if (invqty > mdqty) {
                        invqty -= mdqty;
                        String sql2 = "UPDATE inventorytbl set iqty=? where iid=?";
                        PreparedStatement pstm2 = con.prepareStatement(sql2);

                        pstm2.setInt(1, invqty);
                        pstm2.setInt(2, iid);
                        pstm2.executeUpdate();
                    } else {
                        if (invqty <= 0) {
                            AlertController.showWarningAlert("Warning", "Inventory is low!" + invqty);
                            return false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean orderProcess() {
        String sql = "insert into salestbl (slqty, slprice, sldate,sid) values (?,?,?,?)";
        Iterator i = salestbl.getItems().iterator();
        while (i.hasNext()) {

            SalesDetailView sd = (SalesDetailView) i.next();

            TableColumn colname = salestbl.getColumns().get(0);
            TableColumn colqty1 = salestbl.getColumns().get(2);
            TableColumn colprice1 = salestbl.getColumns().get(3);

            RestaurantMangementController rmc = new RestaurantMangementController();

            try (Connection con = DBController.getConnection()) {
                PreparedStatement psmt = con.prepareCall(sql);
                psmt.setInt(1, (Integer) colqty1.getCellObservableValue(sd).getValue());
                psmt.setDouble(2, (Integer) colprice1.getCellObservableValue(sd).getValue());
                psmt.setDate(3, Date.valueOf(LocalDate.now().plusDays(1)));
                psmt.setInt(4, sid);
                psmt.execute();

                psmt = con.prepareCall("select mid from mealstbl where mname = '" + (String) colname.getCellObservableValue(sd).getValue() + "'");
                ResultSet rs1 = psmt.executeQuery();
                rs1.next();

                psmt = con.prepareCall("select max(slid) from salestbl");
                ResultSet rs = psmt.executeQuery();
                rs.next();

                psmt = con.prepareCall("insert into salesdetailtbl (mid, slid) values(?,?)");
                psmt.setInt(1, rs1.getInt(1));
                psmt.setInt(2, rs.getInt(1));
                psmt.execute();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        salestbl.getItems().clear();
        return true;
    }

    @FXML
    private void handleBreakfast(ActionEvent event) {
        String sql = "select mname from mealstbl where mtype='Breakfast'";
        loadbutton(sql);
    }

    @FXML
    private void handleLunch(ActionEvent event) {
        String sql = "select mname from mealstbl where mtype='Lunch'";
        loadbutton(sql);
    }

    @FXML
    private void handleSnack(ActionEvent event) {
        String sql = "select mname from mealstbl where mtype='Snack'";
        loadbutton(sql);
    }

    @FXML
    private void handleDrink(ActionEvent event) {
        String sql = "select mname from mealstbl where mtype='Drink'";
        loadbutton(sql);
    }

    public void btnHbox(Button btn, int total) {

        if (total <= 5) {
            btnhbox.getChildren().add(btn);
        }
        if (total > 5 && total <= 10) {
            btnhbox1.getChildren().add(btn);
        }
        if (total > 10 && total <= 15) {
            btnhbox2.getChildren().add(btn);
        }
        if (total > 15 && total <= 20) {
            btnhbox3.getChildren().add(btn);
        }
    }

    public void hboxClean() {
        btnhbox.getChildren().clear();
        btnhbox1.getChildren().clear();
        btnhbox2.getChildren().clear();
        btnhbox3.getChildren().clear();

    }
    public void print(final Node node) 
    {
        Thread t = new Thread(() -> {
        System.out.println("Creating a printer job...");

    PrinterJob job = PrinterJob.createPrinterJob();
    if (job != null) {
      System.out.println(job.jobStatusProperty().asString());

      boolean printed = job.printPage(node);
      if (printed) {
        job.endJob();
      } else {
        System.out.println("Printing failed.");
      }
    } else {
      System.out.println("Could not create a printer job.");
    }
        });
        t.start();
     
  
        
  }
}
