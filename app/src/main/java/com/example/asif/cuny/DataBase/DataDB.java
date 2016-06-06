package com.example.asif.cuny.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class DataDB {
	int id;
    String title;
    String description;
    String resType;
    String size;
    String equipcategory;
    String classif;
    String contentLocation;
    String uploadDatetime;
    String userId;
    String lastUpdateDateTime;

    public DataDB(String title, String description, String resType, String size, String equipcategory, String classif, String contentLocation, String uploadDatetime, String userId, String lastUpdateDateTime) {
        this.title = title;
        this.description = description;
        this.resType = resType;
        this.size = size;
        this.equipcategory = equipcategory;
        this.classif = classif;
        this.contentLocation = contentLocation;
        this.uploadDatetime = uploadDatetime;
        this.userId = userId;
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public DataDB() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEquipcategory() {
        return equipcategory;
    }

    public void setEquipcategory(String equipcategory) {
        this.equipcategory = equipcategory;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getClassif() {
        return classif;
    }

    public void setClassif(String classif) {
        this.classif = classif;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getContentLocation() {
        return contentLocation;
    }

    public void setContentLocation(String contentLocation) {
        this.contentLocation = contentLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploadDatetime() {
        return uploadDatetime;
    }

    public void setUploadDatetime(String uploadDatetime) {
        this.uploadDatetime = uploadDatetime;
    }

    public String getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(String lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }
}
