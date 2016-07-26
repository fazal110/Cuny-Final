package com.example.asif.cuny;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.CustomFonts.CustomEditText;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.DataBaseHelper;


public class EditBuildingActivity extends ActionBarActivity {

    private Intent intent;
    private String installed_equip;
    private String total;
    private int id;
    private BAL bal;
    private Utils utils;
    private String buildname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_building);
        setActionBar1();
        init();
    }

    private void init() {
        bal = new BAL(getApplicationContext());utils = new Utils(getApplicationContext());
        intent = getIntent();
        ((CustomEditText) findViewById(R.id.build_name_et)).setText(intent.getStringExtra("Buildname"));
        ((CustomEditText) findViewById(R.id.build_address)).setText(intent.getStringExtra("Buildaddress"));
        ((CustomEditText) findViewById(R.id.build_type)).setText(intent.getStringExtra("BuildType"));
        ((CustomEditText) findViewById(R.id.select_equip_installed)).setText(intent.getStringExtra("installedEquip"));
    }

    public void EquipmentinstalledClick(View view){
        Intent i = new Intent(EditBuildingActivity.this,InstalledEquipmentActivity.class);
        i.putExtra("edit", "edit");
        i.putExtra("building_name", intent.getStringExtra("Buildname"));
        getBuildingid(intent.getStringExtra("Buildname"));
        i.putExtra("Buildingid", id);
        startActivityForResult(i, ApplicationConstant.building_installed_equipment_constant);
    }

    public void getBuildingid(String buildname){
        try{
            id = bal.GetBuildingId(buildname);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.editbuild_actionbar,null);
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

    public void EditClick(View view){
        try{
            DataBaseHelper dataBaseHelper = new DataBaseHelper(EditBuildingActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            String value =  ((CustomEditText) findViewById(R.id.select_equip_installed)).getText().toString();
            buildname = ((CustomEditText) findViewById(R.id.build_name_et)).getText().toString();
            dataBaseHelper.updateEquipment(value,buildname,"BuildingMaster");
            Toast.makeText(getApplicationContext(),"Data Updated",Toast.LENGTH_LONG).show();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Data Not Updated", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ApplicationConstant.building_installed_equipment_constant:{
                try{
                    installed_equip = data.getStringExtra("count");
                    total = data.getStringExtra("total");
                    utils.savedata("total",total);
                    installed_equip = installed_equip+" out of "+utils.getdata("total");
                    ((CustomEditText) findViewById(R.id.select_equip_installed)).setText(installed_equip);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_building, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
