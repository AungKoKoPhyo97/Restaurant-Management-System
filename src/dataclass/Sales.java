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
import javafx.collections.ObservableList;

/**
 *
 * @author MCMITS5-055-USER2
 */
public class Sales {

    private int slid, slqty;
    private double slprice;
    private LocalDate sldate;
    private int sid;

    public Sales(int slid, int slqty, double slprice, LocalDate sldate, int sid) {
        this.slid = slid;
        this.slqty = slqty;
        this.slprice = slprice;
        this.sldate = sldate;
        this.sid = sid;
    }

    public int getSlid() {
        return slid;
    }

    public int getSlqty() {
        return slqty;
    }

    public double getSlprice() {
        return slprice;
    }

    public LocalDate getSldate() {
        return sldate;
    }

    public int getSid() {
        return sid;
    }

    public void setSlid(int slid) {
        this.slid = slid;
    }

    public void setSlqty(int slqty) {
        this.slqty = slqty;
    }

    public void setSlprice(double slprice) {
        this.slprice = slprice;
    }

    public void setSldate(LocalDate sldate) {
        this.sldate = sldate;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

}
