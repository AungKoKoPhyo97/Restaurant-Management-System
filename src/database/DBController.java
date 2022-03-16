/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import dataclass.SalesDetail;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MCMITS5-055-USER2
 */
public class DBController {

    static String dbserverip = "127.0.0.1";

    public static Connection getConnection() {
        Connection con = null;
        if (con == null) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + dbserverip.trim() + ":3306/restaurantdb?serverTimezone=UTC&useSSL=false", "akkp", "1234");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }

    public static boolean checkDbExist() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://" + dbserverip.trim() + ":3306/?serverTimezone=UTC&useSSL=false", "akkp", "1234");) {
            ResultSet rs = con.getMetaData().getCatalogs();
            while (rs.next()) {
                if (rs.getString(1).equals("restaurantdb")) {
                    return true;
                }
            }
            String sql = "CREATE DATABASE restaurantdb";
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.executeUpdate();
            System.out.println("Database Created");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createAdminTbl() {
        String sql = "CREATE TABLE admintbl"
                + "("
                + "apassword varchar(255)"
                + ")";
        try (Connection con = getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createInventoryTbl() {
        String sql = "CREATE TABLE inventorytbl"
                + "("
                + "iid int not null auto_increment,"
                + "iname varchar(255),"
                + "iqty int,"
                + "iprice double,"
                + "PRIMARY KEY(iid)"
                + ")";
        try (Connection con = getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createMealsTbl() {
        String sql = "CREATE TABLE mealstbl"
                + "("
                + "mid int not null auto_increment,"
                + "mname varchar(255),"
                + "mtype varchar(255),"
                + "mprice double,"
                + "PRIMARY KEY(mid)"
                + ")";
        try (Connection con = getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createMealsDetailTbl() {
        String sql = "CREATE TABLE mealsdetailtbl"
                + "("
                + "mdid int not null auto_increment,"
                + "mdqty int,"
                + "iid int not null,"
                + "mid int not null,"
                + "PRIMARY KEY(mdid),"
                + "FOREIGN KEY(iid) REFERENCES inventorytbl(iid),"
                + "FOREIGN KEY(mid) REFERENCES mealstbl(mid)"
                + ")";
        try (Connection con = getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
            return true;
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public static boolean createStaffTbl() {
        String sql = "CREATE TABLE stafftbl"
                + "("
                + "sid int not null auto_increment,"
                + "sname varchar(255),"
                + "snrc varchar(255),"
                + "sdob date,"
                + "sphone int,"
                + "saddress varchar(255),"
                + "ssalary double,"
                + "susername varchar(255),"
                + "spassword varchar(255),"
                + "PRIMARY KEY(sid)"
                + ")";
        try (Connection con = getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createSalesTbl() {
        String sql = "CREATE TABLE salestbl"
                + "("
                + "slid int not null auto_increment,"
                + "slqty int,"
                + "slprice double,"
                + "sldate date,"
                + "sid int not null,"
                + "PRIMARY KEY(slid),"
                + "FOREIGN KEY (sid) REFERENCES stafftbl(sid)"
                + ")";
        try (Connection con = getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createSalesDetailTbl() {
        String sql = "CREATE TABLE salesdetailtbl"
                + "("
                + "sdid int not null auto_increment,"
                + "mid int not null,"
                + "slid int not null,"
                + "PRIMARY KEY (sdid),"
                + "FOREIGN KEY (mid) REFERENCES mealstbl(mid),"
                + "FOREIGN KEY (slid) REFERENCES salestbl(slid)"
                + ")";
        try (Connection con = getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createDailySalesView() {
        String sql = "Create view dailysalesview as SELECT "
                + "mealstbl.mname AS Name,"
                + "salestbl.slqty AS Qty,"
                + "SUM(mealstbl.mprice*salestbl.slqty) AS Total,"
                + "salestbl.sldate FROM salesdetailtbl "
                + "INNER JOIN mealstbl ON salesdetailtbl.mid = mealstbl.mid "
                + "INNER JOIN salestbl ON salesdetailtbl.slid = salestbl.slid "
                + "GROUP BY sldate,Name "
                + "ORDER BY sldate";
        try (Connection con = getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
