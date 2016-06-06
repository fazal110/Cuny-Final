package com.example.asif.cuny.DataBase;

/**
 * Created by asif on 3/24/2016.
 */
public class BuildingType {
    int typeid;
    String build_title;
    String build_type_sqfootage;
    String build_agency_id;

    public BuildingType(int typeid, String build_title, String build_type_sqfootage, String build_agency_id) {
        this.typeid = typeid;
        this.build_title = build_title;
        this.build_type_sqfootage = build_type_sqfootage;
        this.build_agency_id = build_agency_id;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getBuild_title() {
        return build_title;
    }

    public void setBuild_title(String build_title) {
        this.build_title = build_title;
    }

    public String getBuild_type_sqfootage() {
        return build_type_sqfootage;
    }

    public void setBuild_type_sqfootage(String build_type_sqfootage) {
        this.build_type_sqfootage = build_type_sqfootage;
    }

    public String getBuild_agency_id() {
        return build_agency_id;
    }

    public void setBuild_agency_id(String build_agency_id) {
        this.build_agency_id = build_agency_id;
    }
}
