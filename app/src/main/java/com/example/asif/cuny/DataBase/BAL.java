package com.example.asif.cuny.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asif.cuny.LaunchCheckList;
import com.example.asif.cuny.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fazal on 10/11/2015.
 */
public class BAL {
    DBConnect db;
    private String[] arr;
    private List<String> list_item = new ArrayList<>();

    public BAL(Context context) {
        db = new DBConnect(context);
    }

    public  Long InsertData(DataBeen been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConnect.COL_2, been.getClassifTitle());
            contentValues.put(DBConnect.COL_3, been.getClaassifdateTime());
            contentValues.put(DBConnect.COL_4, been.getClassifUpdatedDateTime());
            sqLiteDatabase.insert(DBConnect.TABLE_NAME, null, contentValues);
            sqLiteDatabase.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public  Long InsertPosition(PositionList been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_ID",been.getId());
            contentValues.put("ListTitle", been.getListTitle());
            contentValues.put("Position", been.getPosition());
            sqLiteDatabase.insert(DBConnect.TABLE_NAME5, null, contentValues);
            sqLiteDatabase.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public boolean UpdatePosition(PositionList been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_ID",been.getId());
            contentValues.put("ListTitle", been.getListTitle());
            contentValues.put("Position", been.getPosition());
            sqLiteDatabase.update(DBConnect.TABLE_NAME5, contentValues, "_ID = ?", new String[]{been.getId()+""});
            sqLiteDatabase.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public  Long InsertDatainCheckList(CheckListItem been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EquipmentID", been.getId());
            contentValues.put("CheckedItemID", been.getChecklistid());
            sqLiteDatabase.insert(DBConnect.TABLE_NAME8, null, contentValues);
            sqLiteDatabase.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public  boolean UpdateDatainCheckList(CheckListItem been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("EquipmentID", been.getId());
            contentValues.put("CheckedItemID", been.getChecklistid());
            sqLiteDatabase.update("student", contentValues, "EquipmentID = ?", new String[]{been.getId() + ""});
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public  Long InsertData1(DataBeen been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConnect.COL_2, been.getClassifEqTitle());
            contentValues.put(DBConnect.COL_3, been.getClaassifEqdateTime());
            contentValues.put(DBConnect.COL_4, been.getClassifEqUpdatedDateTime());
            sqLiteDatabase.insert(DBConnect.TABLE_NAME1, null, contentValues);
            sqLiteDatabase.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public  boolean UpdateDatainLaunchList(LaunchList been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("IDCheckedPosition", been.getId());
            contentValues.put("Position", been.getPos());
            sqLiteDatabase.update("student", contentValues, "EquipmentID = ?", new String[]{been.getId() + ""});
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public  Long InsertDatainLaunchList(LaunchList been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("IDCheckedPosition", been.getId());
            contentValues.put("Position", been.getPos());
            sqLiteDatabase.insert(DBConnect.TABLE_NAME7, null, contentValues);
            sqLiteDatabase.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public  Long DCASBuildingData(DCASBuildingModel been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", been.getId());
            contentValues.put("DCASBuildingName", been.getBuildingname());
            sqLiteDatabase.insert(DBConnect.TABLE_NAME6, null, contentValues);
            sqLiteDatabase.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public  Long InsertDatainContentMaster(DataDB been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConnect.COL[1], been.getTitle());
            contentValues.put(DBConnect.COL[2], been.getDescription());
            contentValues.put(DBConnect.COL[3], been.getResType());
            contentValues.put(DBConnect.COL[4], been.getSize());
            contentValues.put(DBConnect.COL[5], been.getEquipcategory());
            contentValues.put(DBConnect.COL[6], been.getClassif());
            contentValues.put(DBConnect.COL[7], been.getContentLocation());
            contentValues.put(DBConnect.COL[8], been.getUploadDatetime());
            contentValues.put(DBConnect.COL[9], been.getUserId());
            contentValues.put(DBConnect.COL[10], been.getLastUpdateDateTime());
            sqLiteDatabase.insert(DBConnect.TABLE_NAME2, null, contentValues);
            sqLiteDatabase.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public  Long InsertDatainEMICourseCalender(EmiModel been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConnect.COL_EMI[1], been.getTitle());
            contentValues.put(DBConnect.COL_EMI[2], been.getStartdate());
            contentValues.put(DBConnect.COL_EMI[3], been.getEnddate());
            contentValues.put(DBConnect.COL_EMI[4], been.getSchedule());
            contentValues.put(DBConnect.COL_EMI[5], been.getDescrip());
            contentValues.put(DBConnect.COL_EMI[6], been.getInstructor());
            contentValues.put(DBConnect.COL_EMI[7], been.getLocAddress());
            contentValues.put(DBConnect.COL_EMI[8], been.getZipcode());
            contentValues.put(DBConnect.COL_EMI[9], been.getCoursecity());
            contentValues.put(DBConnect.COL_EMI[10], been.getLastupdatedate());
            sqLiteDatabase.insert(DBConnect.TABLE_NAME3, null, contentValues);
            sqLiteDatabase.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public  Long InsertDatainEquipmentLibrary(EquipLibrary been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConnect.COL_LIB_EQUIP[1], been.getGroupid());
            contentValues.put(DBConnect.COL_LIB_EQUIP[2], been.getEquipname());
            contentValues.put(DBConnect.COL_LIB_EQUIP[3], been.getEquipmodel());
            contentValues.put(DBConnect.COL_LIB_EQUIP[4], been.getEquiptagging());
            contentValues.put(DBConnect.COL_LIB_EQUIP[5], been.getEquipQuantity());
            contentValues.put(DBConnect.COL_LIB_EQUIP[6], been.getEquipquantityavailable());
            contentValues.put(DBConnect.COL_LIB_EQUIP[7], been.getDate());
            contentValues.put(DBConnect.COL_LIB_EQUIP[8], been.getEquipAddedBy());
            contentValues.put(DBConnect.COL_LIB_EQUIP[9], been.getUpdatedDate());
            sqLiteDatabase.insert(DBConnect.TABLE_NAME4, null, contentValues);
            sqLiteDatabase.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return (long)0;
    }

    public int getBuildingId(String buildname){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        String query = "SELECT Id FROM "+DBConnect.TABLE_NAME5+" where Title = '"+buildname+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if(cursor.getCount() >0) {
            while (cursor.moveToNext()) {
                int buildname1 = cursor.getInt(0);
                return  buildname1;
            }
        }
        return 0;
    }

    public int GetBuildingId(String Buildingname){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        String query = "SELECT ID FROM DCASBuilding where DCASBuildingName = '"+Buildingname+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if(cursor.getCount() >0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                return  id;
            }
        }
        return 0;
    }

    public List<Integer> getPos(String equipid){
        ArrayList<PositionList> arrayList = new ArrayList<>();
        List<Integer> list_pos = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            /*String query = "SELECT LaunchListCheckedPosition.Position " +
                    " from LaunchListCheckedPosition join CheckList on " +
                    "LaunchListCheckedPosition.IDCheckedPosition = CheckList.EquipmentID where EquipmentID = '"+equipid+"'";*/

            String query = "SELECT LaunchListCheckedPosition.Position " +
                    " from LaunchListCheckedPosition where IDCheckedPosition = '"+equipid+"'";

            Cursor cursor = sqLiteDatabase.rawQuery(query,null);

            if(cursor.getCount() >0) {
                while (cursor.moveToNext()) {
                    list_pos.add(cursor.getInt(0));
                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list_pos;
    }

    public List<Integer> getPosition(String buildname){
        //item = item.replace(" ","s/");
        ArrayList<PositionList> arrayList = new ArrayList<>();
        List<Integer> list_pos = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT PositionList.ListTitle, PositionList.Position" +
                    " from PositionList join DCASBuilding on PositionList._ID = DCASBuilding.ID where " +
                    "DCASBuilding.DCASBuildingName = '"+buildname+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);

            if(cursor.getCount() >0) {
                while (cursor.moveToNext()) {
                    list_pos.add(cursor.getInt(1));
                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list_pos;
    }

    public ArrayList<EquipLibrary> getFilterDataEquipmentLibrary(String equipTagging){
        ArrayList<EquipLibrary> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT EquipmentName,EquipmentAvailableQuantity FROM "+DBConnect.TABLE_NAME4+" where EquipmentTagging = '"+equipTagging+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    EquipLibrary been = new EquipLibrary();
                    been.setEquipname(cursor.getString(0));
                    been.setEquipquantityavailable(cursor.getInt(1));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<EquipLibrary> getAllDataFromEqupimentLibrary(){
        ArrayList<EquipLibrary> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME4;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    EquipLibrary been = new EquipLibrary(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getInt(8),
                            cursor.getString(9));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<EmiModel> getAllEMICourseCalender(){
        ArrayList<EmiModel> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME3;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    EmiModel been = new EmiModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8),
                            cursor.getString(9),cursor.getString(10));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<String> getAllResTypeByequipment(int equip){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT ResourceType FROM "+DBConnect.TABLE_NAME2+" where EquipmentCategory = "+equip;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB();
                    been.setTitle(cursor.getString(0));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been.getTitle());

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<String> getAllResType(int classifid){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT ResourceType FROM "+DBConnect.TABLE_NAME2+" where Classification = "+classifid;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    arrayList.add(String.valueOf(cursor.getInt(0)));

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }


    public ArrayList<String> getAllResType(String title){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT ResourceType FROM "+DBConnect.TABLE_NAME2+" where ContentTitle LIKE '%"+title+"%'";
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB();
                    been.setTitle(cursor.getString(0));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been.getTitle());

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<String> getAllResType(){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT ResourceType FROM "+DBConnect.TABLE_NAME2;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB();
                    been.setTitle(cursor.getString(0));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been.getTitle());

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<String> getAllResTypeboiler(int equipid){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT ResourceType FROM "+DBConnect.TABLE_NAME2+" where EquipmentCategory ="+equipid;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB();
                    been.setTitle(cursor.getString(0));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been.getTitle());

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<DataDB> getAllContentDataBoiler(int equipcategoryid){
        ArrayList<DataDB> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where EquipmentCategory ="+equipcategoryid;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<DataDB> getAllContentData(int classifid){
        ArrayList<DataDB> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where Classification = "+classifid;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<DataDB> getAllContentData(int classifid,String resource){
        ArrayList<DataDB> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where ResourceType = '"+resource+"' AND  Classification = "+classifid;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<DataDB> getAllContentData(){
        ArrayList<DataDB> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<DataDB> getAllContentData(String title){
        ArrayList<DataDB> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where ContentTitle LIKE '%"+title+"%'";
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<DataDB> getAllContentData1(String equipcat){
        ArrayList<DataDB> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where EquipmentCategory = '"+equipcat+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<DataDB> getAllContentDataResDB(String resourcetype){
        ArrayList<DataDB> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where ResourceType = '"+resourcetype+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<String> getAllContentDataRes(String resourcetype,boolean istitle){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where ResourceType = '"+resourcetype+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    /*DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));*/
                    //System.out.print("id======================"+been.getStudentID());
                    if(istitle)
                    arrayList.add(cursor.getString(1));
                    else
                        arrayList.add(cursor.getString(3));

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<DataDB> getAllContentData(String title,String resourcetype){
        ArrayList<DataDB> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where ContentTitle LIKE '%"+title+"%' AND ResourceType = '"+resourcetype+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<DataDB> getAllContentData1(String equip,String resourcetype){
        ArrayList<DataDB> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where EquipmentCategory = '"+equip+"' AND ResourceType = '"+resourcetype+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                            cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),
                            cursor.getString(9),cursor.getString(10));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been);

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<String> getAllContentTitle(int classifid){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where Classification = "+classifid;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB();
                    been.setTitle(cursor.getString(1));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been.getTitle());

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<String> getAllContentTitleboiler(int equipid){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME2+" where EquipmentCategory ="+equipid;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataDB been = new DataDB();
                    been.setTitle(cursor.getString(1));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been.getTitle());

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }
/*
    public  boolean UpdateStudent(DataBeen been){
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("studentID", been.getStudentID());
            contentValues.put("studentName", been.getStudentName());
            contentValues.put("studentage", been.getStudentage());
            sqLiteDatabase.update("student", contentValues, "studentID = ?", new String[]{been.getStudentID() + ""});
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }*/



    public int deleteRecord(String id)
    {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        return sqLiteDatabase.delete(DBConnect.TABLE_NAME,DBConnect.COL_1+" = ?",new String[]{id});

    }

    public boolean deleteAll(){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+DBConnect.TABLE_NAME);
        sqLiteDatabase.close();
        return true;
    }

    public boolean deleteAll(String tablename){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+tablename);
        sqLiteDatabase.close();
        return true;
    }

    public boolean deleteAllEquipment(){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+DBConnect.TABLE_NAME4);
        sqLiteDatabase.close();
        return true;
    }

    public boolean deleteAllCalender(){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+DBConnect.TABLE_NAME3);
        sqLiteDatabase.close();
        return true;
    }

    public boolean deleteAll1(){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+DBConnect.TABLE_NAME1);
        sqLiteDatabase.close();
        return true;
    }

    public boolean deleteAll2(){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+DBConnect.TABLE_NAME2);
        sqLiteDatabase.close();
        return true;
    }

    public ArrayList<String> getAllData(){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataBeen been = new DataBeen();
                    been.setClassifId(cursor.getInt(0));
                    been.setClassifTitle(cursor.getString(1));
                    been.setClaassifdateTime(cursor.getString(2));
                    been.setClassifUpdatedDateTime(cursor.getString(3));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been.getClassifTitle());

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }



    public ArrayList<String> getAllData1(){
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME1;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataBeen been = new DataBeen();
                    been.setClassifId(cursor.getInt(0));
                    been.setClassifTitle(cursor.getString(1));
                    been.setClaassifdateTime(cursor.getString(2));
                    been.setClassifUpdatedDateTime(cursor.getString(3));
                    //System.out.print("id======================"+been.getStudentID());
                    arrayList.add(been.getClassifTitle());

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<String> getAllid(){
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String query = "SELECT * FROM "+DBConnect.TABLE_NAME;
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            if(cursor.getCount() >0){
                while (cursor.moveToNext()){
                    DataBeen been = new DataBeen();
                    been.setClassifId(cursor.getInt(0));
                    been.setClassifTitle(cursor.getString(1));
                    System.out.print("id======================" + been.getClassifTitle());
                    arrayList.add(been.getClassifId()+"");

                }
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }
}
