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
 * @author MCMITS5-055-USER2
 */
public class SalesDetail {

    private int sdid, mid, slid;

    public SalesDetail(int sdid, int mid, int slid) {
        this.sdid = sdid;
        this.mid = mid;
        this.slid = slid;
    }

    public int getSdid() {
        return sdid;
    }

    public int getMid() {
        return mid;
    }

    public int getSlid() {
        return slid;
    }

    public void setSdid(int sdid) {
        this.sdid = sdid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setSlid(int slid) {
        this.slid = slid;
    }

}
