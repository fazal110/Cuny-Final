package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 3/25/2016.
 */
public class ViewAlert {
    String viewalert,datatime;

    public ViewAlert(String viewalert, String datatime) {
        this.viewalert = viewalert;
        this.datatime = datatime;
    }

    public String getViewalert() {
        return viewalert;
    }

    public void setViewalert(String viewalert) {
        this.viewalert = viewalert;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
