package com.example.asif.cuny.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by anas on 3/8/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.asif.cuny/databases/";
    // Data Base Name.
    private static final String DATABASE_NAME = "CunyDatabaseTest.sqlite";
    // Data Base Version.
    private static final int DATABASE_VERSION = 1;
    // Table Names of Data Base.
    static final String TABLE_Name = "DCASBuildingMaster";

    public Context context;
    static SQLiteDatabase sqliteDataBase;
    private String query;
    private String TABLE_Name1 = "BuildingTypeMaster";

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     * Parameters of super() are    1. Context
     *                              2. Data Base Name.
     *                              3. Cursor Factory.
     *                              4. Data Base Version.
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
        this.context = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * By calling this method and empty database will be created into the default system path
     * of your application so we are gonna be able to overwrite that database with our database.
     * */
    public void createDataBase() throws IOException{
        //check if the database exists
        boolean databaseExist = checkDataBase();

        if(databaseExist){
            // Do Nothing.
        }else{
            this.getWritableDatabase();
            copyDataBase();
        }// end if else dbExist
    } // end createDataBase().

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring byte stream.
     * */
    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * This method opens the data base connection.
     * First it create the path up till data base of the device.
     * Then create connection with data base.
     */
    public void openDataBase() throws SQLException{
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }

    /**
     * Apply your methods and class to fetch data using raw or queries on data base using
     * following demo example code as:
     */
    public Cursor getDataFromDB(String colname,String colvariable,String tablename,boolean iscon){
        if(iscon==true){
            query = "select * From "+tablename+" where "+colname+" = '"+colvariable+"'";
        }else {
            query = "select * From " + tablename;
        }
        Cursor cursor = sqliteDataBase.rawQuery(query, null);

        return cursor;
    }

    public void getdataFromResponseMaster(String tablename){
        query = "SELECT * FROM "+tablename+" where ";
    }

    public Cursor getDataFromDB1(String colname,String colvariable,String tablename,boolean iscon){
        if(iscon==true){
            query = "select * From "+tablename+" where "+colname+" LIKE  %"+colvariable+"%";
        }else {
            query = "select * From " + tablename;
        }
        Cursor cursor = sqliteDataBase.rawQuery(query, null);

        return cursor;
    }

    public ArrayList<String> getBuildingdata(String installedid){
        ArrayList<String> list = new ArrayList<>();

        query = "SELECT ContentID,ContentTitle,EquipmentCategory,ResourceType,Classification," +
                "ContentLocation,ContentSize From ContentMaster where EquipmentCategory IN ('"+installedid+"')";
        Cursor cursor = sqliteDataBase.rawQuery(query, null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                String title = cursor.getString(1);
                list.add(title);
            }
        }
        return list;
    }

    public String getBuildingClassification(String contentTitle){
        ArrayList<String> list = new ArrayList<>();
        query = "SELECT Classification from ContentMaster where ContentTitle = '"+contentTitle+"'";
        Cursor cursor = sqliteDataBase.rawQuery(query, null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){

                return cursor.getString(0);
            }
        }
        return null;
    }

    public Integer getBuildingPos(String contentTitle){
        //ArrayList<Integer> list = new ArrayList<>();
        query = "SELECT ContentID from ContentMaster where ContentTitle = '"+contentTitle+"'";
        Cursor cursor = sqliteDataBase.rawQuery(query, null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){

                return cursor.getInt(0);
            }
        }
        return 0;
    }

    public  Long insertData1(DataBaseHelper dataBaseHelper,String BuildingName, String BuildingAddress, String BuildingZipCode, String BuildingCity,
                            String BuildingTypeID, String BuildingImageURL, String UploaddateTime, String InstalledEquipmentIDs,
                            String BuildingSquareFootage, String BuildingAgencyID, String BuildingYearBuilt){
        try{

            ContentValues contentValues = new ContentValues();
            contentValues.put("BuildingName", BuildingName);
            contentValues.put("BuildingAddress", BuildingAddress);
            contentValues.put("BuildingZipCode", BuildingZipCode);
            contentValues.put("BuildingCity", BuildingCity);
            contentValues.put("BuildingTypeID", BuildingTypeID);
            contentValues.put("BuildingImageURL", BuildingImageURL);
            contentValues.put("UploaddateTime", UploaddateTime);
            contentValues.put("InstalledEquipmentIDs", InstalledEquipmentIDs);
            contentValues.put("BuildingSquareFootage", BuildingSquareFootage);
            contentValues.put("BuildingAgencyID", BuildingAgencyID);
            contentValues.put("BuildingYearBuilt", BuildingYearBuilt);
            sqliteDataBase.insert("BuildingMaster",null,contentValues);

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public boolean insertUserBuildingsMappings(String Userid, String Buildingid){
        try {
            query = "insert or ignore into UserBuildingsMappings (UserID,BuildingId) Values('"+Userid+"', '"+Buildingid+"')";
            sqliteDataBase.execSQL(query);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean InsertUserCheckListResponse(String Userid, String Checklistid, String ChecklistItemId, String UniqueId,
                                               String CheckListResponseDataTime, String ItemChecked){
        try {
            query = "insert or ignore into UserCheckListsResponseMaster (UserID,CheckListID,CheckListItemID," +
                    "CheckListItemChecked,ChekListResponseDateTime,UniqueID) Values('"+Userid+"', " +
                    "'"+Checklistid+"', '"+ChecklistItemId+"', '"+ItemChecked+"', '"+CheckListResponseDataTime+"', '"+UniqueId+"')";
            sqliteDataBase.execSQL(query);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean InsertUserCheckListsListing(String Checklistid, String Userid,  String startdate, String enddate, String UniqueId
                                               , String status){
        try {
            query = "insert or ignore into UserCheckListsListing (CheckListID,UserID,CheckListStartedDateTime," +
                    "CheckListCompletedDateTime,UniqueID,Status) Values('"+Checklistid+"', " +
                    "'"+Userid+"', '"+startdate+"', '"+enddate+"', '"+UniqueId+"', '"+status+"')";
            sqliteDataBase.execSQL(query);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String total,String buildingname){
        try{
            query = "Update BuildingMaster set InstalledEquipmentIDs = '"+total+"' where BuildingName = '"+buildingname+"'";
            sqliteDataBase.execSQL(query);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void updateEquipment(String updatevalue,String buildname, String tablename){
        buildname = buildname.replace(" ","s/");
        ContentValues contentValues = new ContentValues();
        contentValues.put("InstalledEquipmentIDs",updatevalue);
        sqliteDataBase.update("BuildingMaster",contentValues,"BuildingName =?",new String[]{buildname});
        //String q = "UPDATE "+ tablename +" SET InstalledEquipmentIDs = "+ updatevalue +" WHERE BuildingName = "+buildname;
        //sqliteDataBase.execSQL(q);
        sqliteDataBase.close();
    }



    public ArrayList<String> getpastCheckListData(String UserId){
        ArrayList<String> list = new ArrayList<>();
        query = "SELECT DISTINCT C.EquipmentCategoryTitle,U.CheckListCompletedDateTime,U.CheckListID,U.UniqueID," +
                "U.Status  FROM EquipmentCategoryMaster C JOIN CheckListItemMaster M ON C.EquipmentCategoryID=M.EquipmentID" +
                " JOIN UserCheckListsListing U ON U.CheckListID=M.CheckListID where U.UserID='"+UserId+"'";
        Cursor cursor = sqliteDataBase.rawQuery(query,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                //list.clear();
                if(!list.contains(cursor.getString(0)))
                list.add(cursor.getString(0));
            }
        }
        return list;
    }

    public void deleteAll(String tablename){
        sqliteDataBase.execSQL("delete from "+tablename);
        sqliteDataBase.close();
    }

    public Cursor getBuildingType(){
        query = "select * From " + TABLE_Name1;
        Cursor cursor = sqliteDataBase.rawQuery(query, null);
        return cursor;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // No need to write the create table query.
        // As we are using Pre built data base.
        // Which is ReadOnly.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No need to write the update table query.
        // As we are using Pre built data base.
        // Which is ReadOnly.
        // We should not update it as requirements of application.
    }
}
