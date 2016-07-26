package com.example.asif.cuny;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.CustomFonts.CustomEditText;
import com.example.asif.cuny.DataBase.DataBaseHelper;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class AddBuilding extends AppCompatActivity {


    private CustomEditText build_name_et;
    private CustomEditText select_build_type;
    private CustomEditText select_installed_equip;
    private Utils utils;
    private String build_address;
    private String build_city;
    private String build_zip;
    private String build_year;
    private String build_type_id;
    private String build_agency_id;
    private String build_type_SqFoot;
    private String total;
    private int pos = 1;
    private String installed_equip;
    private int buildingid;
    private String build_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_building);
        setActionBar1();
        init();
    }

    private void init() {
        build_name_et = (CustomEditText) findViewById(R.id.build_name_et);
        select_build_type = (CustomEditText) findViewById(R.id.select_build_type);
        select_installed_equip = (CustomEditText) findViewById(R.id.select_equip_installed);
        utils = new Utils(AddBuilding.this);

    }

    public void AddClick(View view){
      try{
          utils.savedata("title_build", build_name_et.getText().toString());
          Intent Intentback = new Intent();
          Intentback.putExtra("title_build",build_name_et.getText().toString());
          setResult(ApplicationConstant.building_title_constant, Intentback);
          //insertData();
          if(checkdata()){
              insertData();
          }
          finish();
      }catch (Exception e){
          e.printStackTrace();
      }

    }

    public boolean checkdata(){
        try{
            DataBaseHelper dataBaseHelper = new DataBaseHelper(AddBuilding.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            String build_name = build_name_et.getText().toString();
            build_name = build_name.replace(" ","s/");
            Cursor data = dataBaseHelper.getDataFromDB("BuildingName",build_name,"BuildingMaster",true);
            if(data.getCount()>0){
                while (data.moveToNext()){
                    Toast.makeText(getApplicationContext(),"Record Already Exist",Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void insertData(){
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(AddBuilding.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            String build_name = build_name_et.getText().toString();
            build_name = build_name.replace(" ","s/");
            build_address = build_address.replace(" ","s/");
            build_city = build_city.replace(" ","s/");
          long added =  dataBaseHelper.insertData1(dataBaseHelper,build_name, build_address, build_zip, build_city, build_type_id,
                    "NA", "NA", installed_equip, build_type_SqFoot, build_agency_id, build_year);
            if(added!=-1){

                int buildingid = pos;
                if(utils.getdata("pos")!=null){
                   buildingid = Integer.valueOf(utils.getdata("pos"))+1;
                }
                  boolean isInserted = dataBaseHelper.insertUserBuildingsMappings(utils.getdata("UserID"),String.valueOf(buildingid));
                  utils.savedata("pos",String.valueOf(buildingid));
                 senddatatoServer(utils.getdata("UserID"),String.valueOf(buildingid));
                if(isInserted)
                    Toast.makeText(AddBuilding.this,"Insert Data Successfully",Toast.LENGTH_LONG).show();

            }else
                Toast.makeText(AddBuilding.this,"Sorry, Data Not Inserted",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void senddatatoServer(final String userid, final String buildingid){
        Thread thread_send = new Thread(new Runnable() {
            @Override
            public void run() {
              try{
                  HttpClient httpclient = new DefaultHttpClient();
                  // showPB("Loading....");
                  String link = "https://celeritas-solutions.com/cds/hivelet/addUserBuildingMappings.php?UserID="+userid+"&BuildingID="+buildingid;
                  HttpPost httppost = new HttpPost(link);

                  ResponseHandler<String> responseHandler = new BasicResponseHandler();
                  final String response = httpclient.execute(httppost,
                          responseHandler);

                  Log.i("response", "Response : " + response);

                  if(response.equalsIgnoreCase("Records Added.")){
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(getApplicationContext(),"Record Added Successfully",Toast.LENGTH_LONG).show();
                          }
                      });
                  }else{
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(getApplicationContext(),"Sorry, Record Not Added",Toast.LENGTH_LONG).show();
                          }
                      });
                  }
              }catch (Exception e){
                  e.printStackTrace();
              }
            }
        });
        thread_send.start();
    }

    public void BuildingNameClick(View view){
        Intent i = new Intent(AddBuilding.this, BuildingNames.class);
        i.putExtra("status","buildingname");
        startActivityForResult(i, ApplicationConstant.building_name_constant);
    }

    public void BuildingTypeClick(View view){
        Intent i = new Intent(AddBuilding.this, BuildingTypeActivity.class);
        i.putExtra("status","buildingtype");
        startActivityForResult(i, ApplicationConstant.building_type_constant);
    }

    public void SelectInstalledEquipment(View view){
       try{
           if(build_name!=null) {
               Intent i = new Intent(AddBuilding.this, InstalledEquipmentActivity.class);
               i.putExtra("status", "InstalledEquipment");
               utils.editor.putBoolean("isFinished",true);
               i.putExtra("Buildingid", buildingid);
               utils.savedata("IsCheckList", "");
               startActivityForResult(i, ApplicationConstant.building_installed_equipment_constant);
           }else{
               final Toast toast = Toast.makeText(getApplicationContext(),"At First, Select Building Name",Toast.LENGTH_LONG);
               toast.show();
               Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       toast.cancel();
                   }
               },10000);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            try{
                switch (requestCode){
                    case ApplicationConstant.building_name_constant:{
                        try {
                            buildingid = data.getIntExtra("Buidingid",0);
                            build_name = data.getStringExtra("BuildingName");
                            build_address = data.getStringExtra("BuildingAddress");
                            build_city = data.getStringExtra("BuildingCity");
                            build_zip = data.getStringExtra("BuildingZip");
                            build_year = data.getStringExtra("BuildingYear");
                            build_name_et.setText(build_name);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        break;
                    }

                    case ApplicationConstant.building_type_constant:{
                        try {
                            String build_type = data.getStringExtra("BuildingType");
                            build_type_id = data.getStringExtra("BuildingType");
                            build_agency_id = data.getStringExtra("BuildingTypeAgencyid");
                            build_type_SqFoot = data.getStringExtra("BuildingTypesqFootage");
                            select_build_type.setText(build_type);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        break;
                    }

                    case ApplicationConstant.building_installed_equipment_constant:{
                        try{
                            installed_equip = data.getStringExtra("count");
                            total = data.getStringExtra("total");
                           String installed_equip_total = installed_equip+" out of "+total;
                            select_installed_equip.setText(installed_equip_total);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

    }

    public void setActionBar1(){
        try{
            ActionBar actionBar = getSupportActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            View customview = getLayoutInflater().inflate(R.layout.addbuilding_actionbar,null);
            ImageView back = (ImageView) customview.findViewById(R.id.back1);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            actionBar.setCustomView(customview,
                    new ActionBar.LayoutParams(
                            ActionBar.LayoutParams.MATCH_PARENT,
                            ActionBar.LayoutParams.MATCH_PARENT,
                            Gravity.NO_GRAVITY
                    )
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
