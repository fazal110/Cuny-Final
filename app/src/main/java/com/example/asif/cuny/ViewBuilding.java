package com.example.asif.cuny;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.example.asif.cuny.CustomFonts.CustomTextView;
import com.example.asif.cuny.DataBase.BuildingModel;
import com.example.asif.cuny.DataBase.BuildingType;
import com.example.asif.cuny.DataBase.DataBaseHelper;
import com.example.asif.cuny.DataBase.ViewData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ViewBuilding extends ActionBarActivity {

    private List<String> mArrayList;
    private ListView lv;
    Intent intent;
    private CustomTextView title;
    private CustomTextView address;
    private CustomTextView sqfoot;
    private CustomTextView year_build;
    private String build_type;
    private String address1;
    private String total;
    private Utils utils;
    private ArrayList<BuildingModel> list_building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_building);
        setActionBar1();
        intent = getIntent();
        init();
        ShowActivityList();
    }

    private void init() {
        lv = (ListView) findViewById(R.id.list_view);
        title = (CustomTextView) findViewById(R.id.title_);
        address = (CustomTextView) findViewById(R.id.Address);
        sqfoot = (CustomTextView) findViewById(R.id.sqfootage);
        year_build = (CustomTextView) findViewById(R.id.year_build);
        utils = new Utils(getApplicationContext());
        setdata();
    }

    public void EditClick(View view){
        Intent intent = new Intent(ViewBuilding.this,EditBuildingActivity.class);
        getBuildType(title.getText().toString());
        intent.putExtra("Buildname",title.getText().toString());
        intent.putExtra("Buildaddress",address1);
        intent.putExtra("BuildType",build_type);
        intent.putExtra("installedEquip",utils.getdata("total"));
        startActivity(intent);
    }

    public void getBuildType(String buildingname){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(ViewBuilding.this);
        try {
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            buildingname = buildingname.replace(" ","s/");
            Cursor data = dataBaseHelper.getDataFromDB("BuildingName", buildingname, "BuildingMaster", true);
            if (data.getCount() > 0) {
                while (data.moveToNext()) {
                    build_type = data.getString(5);
                    total = data.getString(8);
                    utils.savedata("total",total);
                }
            }
        } catch (IOException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    private void setdata() {
        try{
            ViewData viewData = (ViewData) intent.getSerializableExtra("obj");
            title.setText(viewData.getTitle());
            address1 = viewData.getAddress().replace("s/"," ");
            address.setText("Address: "+address1);
            String sqfootage = viewData.getSqfoot();
            if(sqfootage!=null)
                sqfoot.setText("Square Footage: "+ sqfootage);
            else
                sqfoot.setText("Square Footage: "+ "NA");
            year_build.setText("Year Established: "+viewData.getBuild_year());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.building_actionbar1, null);
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

    public void ShowActivityList() {
        mArrayList = new ArrayList<String>();
        mArrayList.add("Building Materials");
        mArrayList.add("Launch Checklists");
        mArrayList.add("Review Stats");
        mArrayList.add("View Alerts");
        ListCustomAdapter adapter = new ListCustomAdapter(ViewBuilding.this, R.layout.activity_view_building, mArrayList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:{
                        getBuilding(title.getText().toString());
                        Intent intent = new Intent(ViewBuilding.this,BrowseResourceActivity.class);
                        intent.putExtra("InstalledIDs",list_building.get(i).getInstalled_id());
                        intent.putExtra("title",title.getText().toString());
                        startActivity(intent);
                        break;
                    }

                    case 1:{
                        Intent intent = new Intent(ViewBuilding.this,LaunchCheckList.class);
                        startActivity(intent);
                        break;

                    }

                    case 2:{
                        Intent intent = new Intent(ViewBuilding.this,ReviewStatsActivity.class);
                        intent.putExtra("Buildingname",title.getText().toString());
                        startActivity(intent);
                        break;
                    }

                    case 3:{
                        startActivity(new Intent(ViewBuilding.this,ViewAlerts.class));
                        break;
                    }
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void getBuilding(String title_) {
        list_building = new ArrayList<>();
        try{
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.openDataBase();
            Cursor cursor = dataBaseHelper.getDataFromDB("", "", "BuildingMaster", false);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String title = cursor.getString(1).replace("s/"," ");
                    if(title.equals(title_))
                    list_building.add(new BuildingModel(title,cursor.getString(8)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
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
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.single_row,null,false);
                    list_text = (TextView) convertView.findViewById(R.id.list_text);
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
        getMenuInflater().inflate(R.menu.menu_view_building, menu);
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
