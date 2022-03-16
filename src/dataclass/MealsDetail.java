/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataclass;

/**
 *
 * @author MCMITS5-055-USER2
 */
public class MealsDetail {

    private int mdid, mdqty, iid, mid;

    public MealsDetail(int mdid, int mdqty, int iid, int mid) {
        this.mdid = mdid;
        this.mdqty = mdqty;
        this.iid = iid;
        this.mid = mid;
    }

    public int getMdid() {
        return mdid;
    }

    public int getMdqty() {
        return mdqty;
    }

    public int getIid() {
        return iid;
    }

    public int getMid() {
        return mid;
    }

    public void setMdid(int mdid) {
        this.mdid = mdid;
    }

    public void setMdqty(int mdqty) {
        this.mdqty = mdqty;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

}
