package com.example.asif.cuny;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.asif.cuny.CheckBoxAdapter.ItemListAdapter1;
import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.DBConnect;
import com.example.asif.cuny.DataBase.DataBaseHelper;
import com.example.asif.cuny.DataBase.PositionList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InstalledEquipmentActivity extends ListActivity {

    private int pos;
    private ListView lv;
    private List<String> mArrayList;
    private Intent intent;
    private int count;
    private Utils utils;
    private BAL bal;
    String buildingid;
    private String item;
    private ItemListAdapter1 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed_equipment);
        //setActionBar1();
        init();
        ShowActivityList();
    }

    private void init() {
        lv = getListView();
        mArrayList = new ArrayList<>();
        intent = getIntent();
        bal = new BAL(InstalledEquipmentActivity.this);
        utils = new Utils(InstalledEquipmentActivity.this);
        back();

       //bal.deleteAll(DBConnect.TABLE_NAME5);
    }
/*
    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.browse_equipment_actionbar, null);
        ImageView back = (ImageView) customview.findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback = new Intent();
                String count = utils.getdata("count");
                intentback.putExtra("count",count);
                String total = utils.getdata("total");
                intentback.putExtra("total",total);
                utils.savedata("count","");
                setResult(ApplicationConstant.building_installed_equipment_constant,intentback);
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
    }*/

    public void back(){
        View view = findViewById(R.id.actionbar);
        ImageView back = (ImageView) view.findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback = new Intent();
                getcount1();
                String count = utils.getdata("count");
                intentback.putExtra("count",count);
                String total = utils.getdata("total");
                intentback.putExtra("total",total);
                utils.savedata("count","");
                setResult(ApplicationConstant.building_installed_equipment_constant,intentback);
                finish();
            }
        });
    }

    public void ShowActivityList(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(InstalledEquipmentActivity.this);
        try {
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
            lv.setVisibility(View.VISIBLE);
            Cursor data = dataBaseHelper.getDataFromDB("","","EquipmentCategoryMaster",false);
            if(data.getCount()>0){
                while (data.moveToNext()){
                    mArrayList.add(data.getString(1));
                }
            }
            //ListCustomAdapter adapter = new ListCustomAdapter(InstalledEquipmentActivity.this,R.layout.activity_installed_equipment,mArrayList);
        Intent intent = getIntent();
        /*if(intent.getStringExtra("buildname")!=null){
            savepos();
        }*/
        utils.savedata("status_", "");
        if(intent.getStringExtra("status1")!=null) {
            utils.savedata("status_", "status");
        }
        utils.savedata("build_name1","");
        if(intent.getStringExtra("building_name")!=null){
            utils.savedata("build_name1",intent.getStringExtra("building_name"));
        }

        adapter = new ItemListAdapter1(InstalledEquipmentActivity.this,mArrayList);
        setListAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setItemsCanFocus(false);
        adapter.notifyDataSetChanged();
        utils.savedata("total", mArrayList.size() + "");

    }

    public void getcount(){
        SparseBooleanArray checkedItems = lv.getCheckedItemPositions();
        ArrayList<Boolean> countbool = new ArrayList<>();
        for(int i=0;i<checkedItems.size();i++){
            if(checkedItems.get(i)==true){
                countbool.add(true);
            }
        }
        int count = countbool.size();

        utils.savedata("count",count+"");
        savepos();
    }

    public void getcount1(){
        int count =0;
        for(int i=0;i<lv.getCount();i++){
            if(lv.isItemChecked(i)){
                count++;
            }
        }

        utils.savedata("count",count+"");
        savepos();
    }

    public void savepos() {
        int len = lv.getCount();
        //bal.deleteAll(DBConnect.TABLE_NAME5);
         if(intent.getIntExtra("Buildingid", 0)!=0){
             bal.deleteRecord(intent.getIntExtra("Buildingid", 0)+"");
         }
        SparseBooleanArray checked = lv.getCheckedItemPositions();
        for (int i = 0; i < len; i++) {
            if (checked.get(i)) {
                item = mArrayList.get(i);
                int pos = i;

                    PositionList model = new PositionList(intent.getIntExtra("Buildingid", 0), item, pos);
                    bal.InsertPosition(model);


                //utils.savedata(intent.getIntExtra("Buildingid",0) + i + "", String.valueOf(pos));
                //utils.savedata("Building_id", item);
            } else {
                //utils.editor.remove(item + intent.getStringExtra("Buildingid"));
                //utils.editor.remove(intent.getStringExtra("buildname") + i);
            }
        }
    }
   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_installed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }
}
