package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 3/24/2016.
 */
public class DCASBuilding {
    int id;
    String buildname;
    String buildaddress;
    String buildcity;
    String buildzip;
    String buildyearBuild;

    public DCASBuilding(int id, String buildname, String buildaddress, String buildcity, String buildzip, String buildyearBuild) {
        this.id = id;
        this.buildname = buildname;
        this.buildaddress = buildaddress;
        this.buildcity = buildcity;
        this.buildzip = buildzip;
        this.buildyearBuild = buildyearBuild;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuildname() {
        return buildname;
    }

    public void setBuildname(String buildname) {
        this.buildname = buildname;
    }

    public String getBuildaddress() {
        return buildaddress;
    }

    public void setBuildaddress(String buildaddress) {
        this.buildaddress = buildaddress;
    }

    public String getBuildcity() {
        return buildcity;
    }

    public void setBuildcity(String buildcity) {
        this.buildcity = buildcity;
    }

    public String getBuildzip() {
        return buildzip;
    }

    public void setBuildzip(String buildzip) {
        this.buildzip = buildzip;
    }

    public String getBuildyearBuild() {
        return buildyearBuild;
    }

    public void setBuildyearBuild(String buildyearBuild) {
        this.buildyearBuild = buildyearBuild;
    }
}
