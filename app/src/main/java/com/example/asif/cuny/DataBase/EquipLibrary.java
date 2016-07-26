package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 3/8/2016.
 */
public class EquipLibrary {
    int id;
    String groupid;
    String equipname;
    String equipmodel;
    String equiptagging;
    int equipQuantity;
    int equipquantityavailable;
    String date;
    int equipAddedBy;
    String updatedDate;

    public EquipLibrary(String groupid, String equipname, String equipmodel, String equiptagging,
                        int equipQuantity, int equipquantityavailable, String date, int equipAddedBy, String updatedDate) {
        this.groupid = groupid;
        this.equipname = equipname;
        this.equipmodel = equipmodel;
        this.equiptagging = equiptagging;
        this.equipQuantity = equipQuantity;
        this.equipquantityavailable = equipquantityavailable;
        this.date = date;
        this.equipAddedBy = equipAddedBy;
        this.updatedDate = updatedDate;
    }

    public EquipLibrary() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getEquipname() {
        return equipname;
    }

    public void setEquipname(String equipname) {
        this.equipname = equipname;
    }

    public String getEquipmodel() {
        return equipmodel;
    }

    public void setEquipmodel(String equipmodel) {
        this.equipmodel = equipmodel;
    }

    public String getEquiptagging() {
        return equiptagging;
    }

    public void setEquiptagging(String equiptagging) {
        this.equiptagging = equiptagging;
    }

    public int getEquipQuantity() {
        return equipQuantity;
    }

    public void setEquipQuantity(int equipQuantity) {
        this.equipQuantity = equipQuantity;
    }

    public int getEquipquantityavailable() {
        return equipquantityavailable;
    }

    public void setEquipquantityavailable(int equipquantityavailable) {
        this.equipquantityavailable = equipquantityavailable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEquipAddedBy() {
        return equipAddedBy;
    }

    public void setEquipAddedBy(int equipAddedBy) {
        this.equipAddedBy = equipAddedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
