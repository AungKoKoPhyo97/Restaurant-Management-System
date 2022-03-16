/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataclass;

import database.DBController;
import java.sql.Date;
import java.sql.Connection;
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
public class SalesDailyDetailView {

    private String mname;
    private int slid;
    private double mprice;
    private LocalDate sldate;

    public SalesDailyDetailView(String mname, int slid, double mprice, LocalDate sldate) {
        this.mname = mname;
        this.slid = slid;
        this.mprice = mprice;
        this.sldate = sldate;
    }

    public SalesDailyDetailView(String string, double aDouble) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getMname() {
        return mname;
    }

    public int getSlid() {
        return slid;
    }

    public double getMprice() {
        return mprice;
    }

    public LocalDate getSldate() {
        return sldate;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setSlid(int slid) {
        this.slid = slid;
    }

    public void setMprice(double mprice) {
        this.mprice = mprice;
    }

    public void setSldate(LocalDate sldate) {
        this.sldate = sldate;
    }

    public static ObservableList<SalesDailyDetailView> getDailySales(LocalDate dailyDate) {

        ObservableList<SalesDailyDetailView> sldvlist = FXCollections.observableArrayList();
        String sql = "select * from dailysalesview  WHERE sldate =? ORDER BY Total DESC";
        try (Connection con = DBController.getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setDate(1, Date.valueOf(dailyDate));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString(4));
                SalesDailyDetailView s = new SalesDailyDetailView(rs.getString(1), rs.getInt(2), rs.getDouble(3), date);
                sldvlist.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sldvlist;

    }
}
