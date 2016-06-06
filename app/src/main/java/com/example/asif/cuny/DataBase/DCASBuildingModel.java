package com.example.asif.cuny.DataBase;

/**
 * Created by Fazal on 03-Apr-16.
 */
public class DCASBuildingModel {

    int id;
    String buildingname;

    public DCASBuildingModel(int id, String buildingname) {
        this.id = id;
        this.buildingname = buildingname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuildingname() {
        return buildingname;
    }

    public void setBuildingname(String buildingname) {
        this.buildingname = buildingname;
    }
}
