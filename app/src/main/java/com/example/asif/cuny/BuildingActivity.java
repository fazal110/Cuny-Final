package com.example.asif.cuny;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.DataBase.BuildingType;
import com.example.asif.cuny.DataBase.DataBaseHelper;
import com.example.asif.cuny.DataBase.ViewData;

import java.util.ArrayList;
import java.util.List;


public class BuildingActivity extends ActionBarActivity {

    private ListView lv;
    private ArrayList<String> list = new ArrayList<>();
    private Utils utils;
    private ViewData viewdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        init();
        setActionBar1();
        setList();

    }

    public void init(){
        lv = (ListView) findViewById(R.id.list_building);
        utils = new Utils(BuildingActivity.this);
        getData();
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.building_actionbar, null);
        actionBar.setCustomView(customview,
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.NO_GRAVITY
                )
        );
    }

    public void ResourceClick(View view){
        Intent i = new Intent(BuildingActivity.this,ResourceActivity.class);
        startActivity(i);
        finish();
    }

    public void AdminClick(View view){
        Intent i = new Intent(BuildingActivity.this,AdminActivity.class);
        startActivity(i);
        finish();
    }

    public void HomeClick(View view){
        Intent i = new Intent(BuildingActivity.this,HomeActivity.class);
        i.putExtra("status","fromBuilding");
        startActivity(i);
        finish();
    }

    public void setList(){

        try{
            ListCustomAdapter adapter = new ListCustomAdapter(BuildingActivity.this,R.layout.activity_list,list);
            View footerView = getLayoutInflater().inflate(R.layout.single_row,
                    lv, false);
            footerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(BuildingActivity.this,AddBuilding.class),ApplicationConstant.building_title_constant);
                }
            });
            lv.addFooterView(footerView);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(BuildingActivity.this,ViewBuilding.class);
                    getData(list.get(i));
                    intent.putExtra("obj",viewdata);

                    startActivity(intent);
                }
            });
            adapter.notifyDataSetChanged();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getData(String title1){
        try{
            DataBaseHelper dataBaseHelper = new DataBaseHelper(BuildingActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            //delete(dataBaseHelper);
            Cursor data = dataBaseHelper.getDataFromDB("","","BuildingMaster",false);
            if(data.getCount()>0){
                while (data.moveToNext()){
                    String title = data.getString(1);
                    title = title.replace("s/"," ");
                    if(!list.contains(title))
                    list.add(title);
                    if(title1.equals(title))
                    viewdata = new ViewData(title,data.getString(2),data.getString(9),data.getString(11));
                }
            }
            ListCustomAdapter adapter = new ListCustomAdapter(BuildingActivity.this,R.layout.activity_list,list);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getData(){
        try{
            DataBaseHelper dataBaseHelper = new DataBaseHelper(BuildingActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            //delete(dataBaseHelper);
            Cursor data = dataBaseHelper.getDataFromDB("","","BuildingMaster",false);
            if(data.getCount()>0){
                while (data.moveToNext()){
                    String title = data.getString(1);
                    title = title.replace("s/"," ");
                    if(!list.contains(title))
                        list.add(title);
                    viewdata = new ViewData(title,data.getString(2),data.getString(9),data.getString(11));
                }
            }
            ListCustomAdapter adapter = new ListCustomAdapter(BuildingActivity.this,R.layout.activity_list,list);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void delete(DataBaseHelper dataBaseHelper) {
        dataBaseHelper.deleteAll("BuildingMaster");
        dataBaseHelper.deleteAll("UserBuildingsMappings");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ApplicationConstant.building_title_constant:{
                try {
                    String title = data.getStringExtra("title_build");
                    if (list.contains(title)) {
                        ListCustomAdapter adapter = new ListCustomAdapter(BuildingActivity.this, R.layout.activity_list, list);
                        lv.setAdapter(adapter);
                        return;
                    } else {
                        list.add(title);
                        ListCustomAdapter adapter = new ListCustomAdapter(BuildingActivity.this, R.layout.activity_list, list);
                        lv.setAdapter(adapter);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    public class ListCustomAdapter extends ArrayAdapter<String> {
        List<String> value;
        private TextView list_text;

        public ListCustomAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            value = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try{
                if(convertView==null){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.single_row,null,false);
                    list_text = (TextView) convertView.findViewById(R.id.list_text);
                }

                list_text.setText(value.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_building, menu);
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
