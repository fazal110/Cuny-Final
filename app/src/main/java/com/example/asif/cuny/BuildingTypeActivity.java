package com.example.asif.cuny;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.BuildingType;
import com.example.asif.cuny.DataBase.DataBaseHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildingTypeActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<BuildingType> list = new ArrayList<BuildingType>();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_type);
        init();
        setActionBar1();
        ShowActivityList();
    }

    public void init(){
        lv = (ListView) findViewById(R.id.list_building_type);
        intent = getIntent();
    }


    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.bildingtype_actionbar, null);
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

    public void ShowActivityList(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(BuildingTypeActivity.this);
        try {
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor data = dataBaseHelper.getBuildingType();
        if(data.getCount()>0){
            while (data.moveToNext()){
                BuildingType buildingType = new BuildingType(data.getInt(0),data.getString(1),data.getString(2),data.getString(3));
                list.add(buildingType);
            }
        }
        ListCustomAdapter adapter = new ListCustomAdapter(BuildingTypeActivity.this,R.layout.activity_building_type,list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!intent.getStringExtra("status").equals("")){
                    Intent intentback = new Intent();
                    intentback.putExtra("BuildingType",list.get(i).getBuild_title());
                    intentback.putExtra("BuildingTypeid",list.get(i).getTypeid());
                    intentback.putExtra("BuildingTypeAgencyid",list.get(i).getBuild_agency_id());
                    intentback.putExtra("BuildingTypesqFootage",list.get(i).getBuild_type_sqfootage());

                    setResult(ApplicationConstant.building_type_constant,intentback);
                    finish();
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    public class ListCustomAdapter extends ArrayAdapter<BuildingType> {
        List<BuildingType> value;
        private TextView list_text;

        public ListCustomAdapter(Context context, int resource, List<BuildingType> objects) {
            super(context, resource, objects);
            value = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try{
                if(convertView==null){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.single_row1,null,false);
                    list_text = (TextView) convertView.findViewById(R.id.list_text);
                }

                list_text.setText(value.get(position).getBuild_title());
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }


}
