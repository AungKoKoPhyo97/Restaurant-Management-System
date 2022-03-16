/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataclass;

import database.DBController;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Acer
 */
public class SalesWeekly {

    private LocalDate sldate;
    private int slqty;
    private double slprice;

    public SalesWeekly(LocalDate sldate, int slqty, double slprice) {
        this.sldate = sldate;
        this.slqty = slqty;
        this.slprice = slprice;
    }

    public LocalDate getSldate() {
        return sldate;
    }

    public int getSlqty() {
        return slqty;
    }

    public double getSlprice() {
        return slprice;
    }

    public void setSldate(LocalDate sldate) {
        this.sldate = sldate;
    }

    public void setSlqty(int slqty) {
        this.slqty = slqty;
    }

    public void setSlprice(double slprice) {
        this.slprice = slprice;
    }

    public static ObservableList<SalesWeekly> getTotalSales(LocalDate weeklySDate, LocalDate weeklyEDate) {
        ObservableList<SalesWeekly> list = FXCollections.observableArrayList();
        String sql = "SELECT sldate,SUM(slqty),SUM(slprice) FROM salestbl  WHERE sldate BETWEEN ? AND ? GROUP BY sldate";
        try (Connection con = DBController.getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setDate(1, Date.valueOf(weeklySDate));
            pstm.setDate(2, Date.valueOf(weeklyEDate));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString(1));
                SalesWeekly sw = new SalesWeekly(date, rs.getInt(2), rs.getDouble(3));
                list.add(sw);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
