/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataclass;

import static database.DBController.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author MCMITS5-055-USER2
 */
public class Staff {

    private int sid;
    private String sname, snrc;
    private LocalDate sdob;
    private int sphone;
    private String saddress;
    private double ssalary;
    private String susername;
    private String spassword;

    public Staff(int sid, String sname, String snrc, LocalDate sdob, int sphone, String saddress, double ssalary, String susername, String spassword) {
        this.sid = sid;
        this.sname = sname;
        this.snrc = snrc;
        this.sdob = sdob;
        this.sphone = sphone;
        this.saddress = saddress;
        this.ssalary = ssalary;
        this.susername = susername;
        this.spassword = spassword;
    }

    public int getSid() {
        return sid;
    }

    public String getSname() {
        return sname;
    }

    public String getSnrc() {
        return snrc;
    }

    public LocalDate getSdob() {
        return sdob;
    }

    public int getSphone() {
        return sphone;
    }

    public String getSaddress() {
        return saddress;
    }

    public double getSsalary() {
        return ssalary;
    }

    public String getSusername() {
        return susername;
    }

    public String getSpassword() {
        return spassword;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setSnrc(String snrc) {
        this.snrc = snrc;
    }

    public void setSdob(LocalDate sdob) {
        this.sdob = sdob;
    }

    public void setSphone(int sphone) {
        this.sphone = sphone;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }

    public void setSsalary(double ssalary) {
        this.ssalary = ssalary;
    }

    public void setSusername(String susername) {
        this.susername = susername;
    }

    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }

    public boolean saveStaffData(Staff s) {

        try (Connection con = getConnection()) {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO stafftbl(sname,snrc,sphone,saddress,ssalary,susername,spassword,sdob) VALUES(?,?,?,?,?,?,?,?)");
            stmt.setString(1, s.getSname());
            stmt.setString(2, s.getSnrc());

            stmt.setDate(8, java.sql.Date.valueOf(s.getSdob().plusDays(1)));
            stmt.setInt(3, s.getSphone());
            stmt.setString(4, s.getSaddress());
            stmt.setDouble(5, s.getSsalary());
            stmt.setString(6, s.getSusername());
            stmt.setString(7, s.getSpassword());
            //System.out.println("savestaffmethod");
            stmt.executeUpdate();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateStaffData(Staff s) {

        String sql = "update stafftbl set sname=?,snrc=?,sdob=?,sphone=?,saddress=?,ssalary=?,susername=?,spassword=? where sid=?";
        try (Connection con = getConnection()) {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getSname());
            stmt.setString(2, s.getSnrc());
            stmt.setDate(3, java.sql.Date.valueOf(s.getSdob().plusDays(1)));
            stmt.setInt(4, s.getSphone());
            stmt.setString(5, s.getSaddress());
            stmt.setDouble(6, s.getSsalary());
            stmt.setString(7, s.getSusername());
            stmt.setString(8, s.getSpassword());
            stmt.setInt(9, s.getSid());
            //System.out.println("savestaffmethod");
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static ObservableList<Staff> getallStaffData() {
        ObservableList<Staff> list = FXCollections.observableArrayList();
        try (Connection con = getConnection()) {
            String sql = "Select * from stafftbl";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Staff s2 = new Staff(rs.getInt(1), rs.getString(2), rs.getString(3), LocalDate.parse(rs.getDate(4).toString()),
                        rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getString(9));
                list.add(s2);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.isEmpty()) {
            System.out.println("list empty");
        }
        return list;

    }

    public static ObservableList<Staff> getStaffsbyName(String staffName) {
        ObservableList<Staff> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM stafftbl WHERE sname LIKE ?";
        try (Connection con = getConnection(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setString(1, "%" + staffName + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Staff s = new Staff(rs.getInt(1), rs.getString(2), rs.getString(3), LocalDate.parse(rs.getDate(4).toString()),
                        rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getString(8), rs.getString(9));
                list.add(s);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static boolean checkifStaffdataexist(String snrc) {

        for (Staff s : getallStaffData()) {
            if (s.getSnrc().contains(snrc)) {
                return true;
            }
        }
        return false;
    }
}
