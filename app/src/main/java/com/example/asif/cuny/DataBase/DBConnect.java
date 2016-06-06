package com.example.asif.cuny.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by VIN on 10/11/2015.
 */
public class DBConnect extends SQLiteOpenHelper {

    public static String DB_NAME = "Cuny_DB";
    public static int DB_VERSION = 1;
    public static String TABLE_NAME = "ClassificationMaster";
    public static String TABLE_NAME1 = "ClassificationEquipmentMaster";
    public static String TABLE_NAME2 = "ContentMaster";
    public static String TABLE_NAME3 = "EMICourseCalender";
    public static String TABLE_NAME4 = "EquipmentLibraryMaster";
    public static String TABLE_NAME5 = "PositionList";
    public static String TABLE_NAME6 = "DCASBuilding";
    public static String TABLE_NAME7 = "LaunchListCheckedPosition";
    public static String TABLE_NAME8 = "CheckList";
    public static String COL_1 = "ClassificationID";
    public static String COL_2 = "ClassificationTitle";
    public static String COL_3 = "AddedDateTime";
    public static String COL_4 = "LastUpdatedDateTime";
    public static String[] COL = {"ContentId", "ContentTitle", "ContentDescription", "ResourceType", "ContentSize","EquipmentCategory",
     "Classification", "ContentLocation", "ContentUploadedDateTime", "ContentUploadedByUserId", "LastUpdatedDateTime"};
    public static String[] COL_EMI = {
            "CourseId" ,"CourseTitle","CourseStartDate", "CourseEndDate", "CourseSchedule", "CourseDescription", "CourseInstructor",
            "CourseLocationAddress","CourseZipCode", "CourseCity", "LastUpdatedDate"
    };

    public static String[] COL_LIB_EQUIP = {
            "EquipmentAssetsID", "EquipmentGroupID", "EquipmentName", "EquipmentModel", "EquipmentTagging", "EquipmentQuantityTotal",
            "EquipmentAvailableQuantity", "EquipmentAddedDate", "EquipmentAddedBy", "EquipmenQuantityUpdatedDate"
    };
    SQLiteDatabase db;


    public DBConnect(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String table = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+COL_1+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+COL_2+" TEXT, "
        +COL_3+" TEXT, "+COL_4+" TEXT)";
        String table1 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME1+"("+COL_1+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+COL_2+" TEXT, "
                +COL_3+" TEXT, "+COL_4+" TEXT)";

        String table2 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME2+"(ContentId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,ContentTitle TEXT, " +
                "ContentDescription TEXT, ResourceType INTEGER, ContentSize TEXT, EquipmentCategory INTEGER, Classification INTEGER, " +
                "ContentLocation TEXT, ContentUploadedDateTime TEXT, ContentUploadedByUserId INTEGER, LastUpdatedDateTime TEXT)";

        String table3 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME3+"(CourseId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,CourseTitle TEXT, " +
                "CourseStartDate TEXT, CourseEndDate TEXT, CourseSchedule TEXT, CourseDescription TEXT, CourseInstructor TEXT, " +
                "CourseLocationAddress TEXT, CourseZipCode INTEGER, CourseCity INTEGER, LastUpdatedDate TEXT)";

        String table4 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME4+"(EquipmentAssetsID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,EquipmentGroupID TEXT, " +
                "EquipmentName TEXT, EquipmentModel TEXT, EquipmentTagging TEXT, EquipmentQuantityTotal INTEGER, EquipmentAvailableQuantity INTEGER, " +
                "EquipmentAddedDate TEXT, EquipmentAddedBy INTEGER, EquipmenQuantityUpdatedDate TEXT)";

        String table5 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME5+"(_ID INTEGER NOT NULL, ListTitle TEXT, Position INTEGER)";

        String table6 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME6+"(ID INTEGER NOT NULL, DCASBuildingName TEXT)";

        String table7 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME7+"(IDCheckedPosition INTEGER NOT NULL, Position INTEGER)";

        String table8 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME8+"(EquipmentID INTEGER NOT NULL, CheckedItemID INTEGER)";

        db.execSQL(table);
        db.execSQL(table1);
        db.execSQL(table2);
        db.execSQL(table3);
        db.execSQL(table4);
        db.execSQL(table5);
        db.execSQL(table6);
        db.execSQL(table7);
        db.execSQL(table8);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
