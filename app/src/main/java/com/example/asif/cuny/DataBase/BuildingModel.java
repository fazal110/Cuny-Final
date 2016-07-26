package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 4/11/2016.
 */
public class BuildingModel {
    String buildingname;
    String installed_id;

    public BuildingModel(String buildingname, String installed_id) {
        this.buildingname = buildingname;
        this.installed_id = installed_id;
    }

    public String getBuildingname() {
        return buildingname;
    }

    public void setBuildingname(String buildingname) {
        this.buildingname = buildingname;
    }

    public String getInstalled_id() {
        return installed_id;
    }

    public void setInstalled_id(String installed_id) {
        this.installed_id = installed_id;
    }
}
