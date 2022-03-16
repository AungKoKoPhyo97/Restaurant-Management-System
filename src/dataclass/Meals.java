/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import static database.DBController.getConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author MCMITS5-055-USER2
 */
public class Meals {

    private int mid;
    private String mname, mtype;
    private double mprice;

    public Meals(int mid, String mname, String mtype, double mprice) {
        this.mid = mid;
        this.mname = mname;
        this.mtype = mtype;
        this.mprice = mprice;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public double getMprice() {
        return mprice;
    }

    public void setMprice(double mprice) {
        this.mprice = mprice;
    }

//    public boolean InsertData(Meals m) {
//        String sql = "insert into meals (mname,mtype,mprice) values (?,?,?)";
//        try (Connection con = getConnection();
//                PreparedStatement psmt = con.prepareCall(sql)) {
//
//            psmt.setString(1, m.getMname());
//            psmt.setString(2, m.getMtype());
//            psmt.setDouble(3, m.getMprice());
//            psmt.execute();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public static ObservableList<Meals> getAllMealsData() {
        ObservableList<Meals> list = FXCollections.observableArrayList();
        String sql = "Select * from mealstbl";
        try (Connection con = database.DBController.getConnection();) {
            PreparedStatement psmt = con.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Meals m = new Meals(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean checkifmealsdataexist(String s) {

        for (Meals m : getAllMealsData()) {
            if (m.getMname().contains(s)) {
                return true;
            }
        }
        return false;
    }
}
