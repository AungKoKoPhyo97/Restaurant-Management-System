/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataclass;

import database.DBController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 *
 * @author MCMITS5-055-USER2
 */
public class Inventory {

    private int iid;
    private String iname;
    private int iqty;
    private double iprice;

    public Inventory(int iid, String iname, int iqty, double iprice) {
        this.iid = iid;
        this.iname = iname;
        this.iqty = iqty;
        this.iprice = iprice;
    }

    public int getIid() {
        return iid;
    }

    public String getIname() {
        return iname;
    }

    public int getIqty() {
        return iqty;
    }

    public double getIprice() {
        return iprice;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public void setIqty(int iqty) {
        this.iqty = iqty;
    }

    public void setIprice(double iprice) {
        this.iprice = iprice;
    }

    public static ObservableList<Inventory> getAllInventoryData() {
        ObservableList<Inventory> list = FXCollections.observableArrayList();
        String sql = "Select * from inventorytbl";
        try (Connection con = DBController.getConnection();) {
            PreparedStatement psmt = con.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Inventory i = new Inventory(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4));
                list.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean checkifinventorydataexist(String s) {

        for (Inventory i : getAllInventoryData()) {
            if (i.getIname().contains(s)) {
                return true;
            }
        }
        return false;
    }
}
