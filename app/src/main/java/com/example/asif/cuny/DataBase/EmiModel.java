package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 3/7/2016.
 */
public class EmiModel {
    int id;
    String title;
    String startdate;
    String enddate;
    String schedule;
    String descrip;
    String instructor;
    String locAddress;
    int zipcode;
    String coursecity;
    String lastupdatedate;

    public EmiModel(int id, String title, String startdate, String enddate, String schedule,
                    String descrip, String instructor, String locAddress, int zipcode, String coursecity, String lastupdatedate) {
        this.id = id;
        this.title = title;
        this.startdate = startdate;
        this.enddate = enddate;
        this.schedule = schedule;
        this.descrip = descrip;
        this.instructor = instructor;
        this.locAddress = locAddress;
        this.zipcode = zipcode;
        this.coursecity = coursecity;
        this.lastupdatedate = lastupdatedate;
    }

    public EmiModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(String locAddress) {
        this.locAddress = locAddress;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCoursecity() {
        return coursecity;
    }

    public void setCoursecity(String coursecity) {
        this.coursecity = coursecity;
    }

    public String getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(String lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }
}
