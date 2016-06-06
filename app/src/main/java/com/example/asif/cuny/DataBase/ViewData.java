package com.example.asif.cuny.DataBase;

import java.io.Serializable;

/**
 * Created by asif on 3/24/2016.
 */
public class ViewData implements Serializable {
    String title,address,sqfoot,build_year;

    public ViewData(String title, String address, String sqfoot, String build_year) {
        this.title = title;
        this.address = address;
        this.sqfoot = sqfoot;
        this.build_year = build_year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSqfoot() {
        return sqfoot;
    }

    public void setSqfoot(String sqfoot) {
        this.sqfoot = sqfoot;
    }

    public String getBuild_year() {
        return build_year;
    }

    public void setBuild_year(String build_year) {
        this.build_year = build_year;
    }
}
