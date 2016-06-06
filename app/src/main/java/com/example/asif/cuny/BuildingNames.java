package com.example.asif.cuny;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.CustomFonts.CustomEditText;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.DBConnect;
import com.example.asif.cuny.DataBase.DCASBuilding;
import com.example.asif.cuny.DataBase.DCASBuildingModel;
import com.example.asif.cuny.DataBase.DataBaseHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildingNames extends AppCompatActivity {

    private List<DCASBuilding> mArrayList = new ArrayList<DCASBuilding>();
    private ListView lv;
    private CustomEditText zipet;
    private Intent intent;
    private BAL bal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_names);
        setActionBar1();
        init();
    }

    private void init() {
        lv = (ListView) findViewById(R.id.list_building_names);
        lv.setVisibility(View.GONE);
        zipet = (CustomEditText) findViewById(R.id.zipet);
        intent = getIntent();
        bal = new BAL(this);
        //bal.deleteAll(DBConnect.TABLE_NAME6);
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.addbuilding_actionbar, null);
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
    }

    public void FindClick(View view){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ShowActivityList();
    }


    public void ShowActivityList(){
        //TestAdapter testadapter = new TestAdapter(BuildingNames.this);
        /*testadapter.createDatabase();
        testadapter.open();
        Cursor data = testadapter.getTestData();*/
        DataBaseHelper dataBaseHelper = new DataBaseHelper(BuildingNames.this);
        try {
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String zip = zipet.getText().toString();

            if(zip.equals("")){
                AlertDialog.Builder builder1 = new AlertDialog.Builder(BuildingNames.this);
                builder1.setTitle("Warning!!");
                builder1.setMessage("Zip Code must be required to further proceed, Thanks");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }else {

                lv.setVisibility(View.VISIBLE);
                Cursor data = dataBaseHelper.getDataFromDB("Zip",zip,"DCASBuildingMaster",true);
                if(data.getCount()>0){
                    while (data.moveToNext()){
                        DCASBuilding dcasBuilding = new DCASBuilding(data.getInt(0), data.getString(4),data.getString(5),data.getString(6),
                                data.getString(8),data.getString(11));
                        DCASBuildingModel dcasBuildingModel = new DCASBuildingModel(data.getInt(0),data.getString(4));
                        bal.DCASBuildingData(dcasBuildingModel);
                        mArrayList.add(dcasBuilding);
                    }
                }
                ListCustomAdapter adapter = new ListCustomAdapter(BuildingNames.this, R.layout.activity_building_names,mArrayList);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(!intent.getStringExtra("status").equals("")){
                            Intent intentback = new Intent();
                            intentback.putExtra("BuildingName",mArrayList.get(i).getBuildname());
                            intentback.putExtra("Buidingid",mArrayList.get(i).getId());
                            intentback.putExtra("BuildingAddress",mArrayList.get(i).getBuildaddress());
                            intentback.putExtra("BuildingCity",mArrayList.get(i).getBuildcity());
                            intentback.putExtra("BuildingZip",mArrayList.get(i).getBuildzip());
                            intentback.putExtra("BuildingYear",mArrayList.get(i).getBuildyearBuild());
                            setResult(ApplicationConstant.building_name_constant, intentback);
                            finish();
                        }
                    }
                });
            }
    }

    public class ListCustomAdapter extends ArrayAdapter<DCASBuilding> {
        List<DCASBuilding> value;
        private TextView list_text;

        public ListCustomAdapter(Context context, int resource, List<DCASBuilding> objects) {
            super(context, resource, objects);
            value = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try{
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.single_row1,null,false);
                    list_text = (TextView) convertView.findViewById(R.id.list_text);

                list_text.setText(value.get(position).getBuildname());
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }

}
