package com.example.asif.cuny;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asif.cuny.DataBase.BuildingModel;
import com.example.asif.cuny.DataBase.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class BrowseBuildingActivity extends ActionBarActivity {

    private ListView lv;
    private ArrayList<BuildingModel> list_building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_building);
        init();
        setlist();
    }

    private void init() {
        lv = (ListView) findViewById(R.id.list_browse_building);
    }

    private void setlist() {
        getBuilding();
        ListCustomAdapter adapter = new ListCustomAdapter(this,R.layout.activity_browse_building,list_building);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try{
                    Intent intent = new Intent(BrowseBuildingActivity.this,BrowseResourceActivity.class);
                    intent.putExtra("InstalledIDs",list_building.get(i).getInstalled_id());
                    intent.putExtra("title",list_building.get(i).getBuildingname());
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void getBuilding() {
        list_building = new ArrayList<>();
        try{
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.openDataBase();
            Cursor cursor = dataBaseHelper.getDataFromDB("", "", "BuildingMaster", false);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String title = cursor.getString(1).replace("s/"," ");
                    list_building.add(new BuildingModel(title,cursor.getString(8)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public class ListCustomAdapter extends ArrayAdapter<BuildingModel> {
        List<BuildingModel> value;
        private TextView list_text;

        public ListCustomAdapter(Context context, int resource, ArrayList<BuildingModel> objects) {
            super(context, resource, objects);
            value = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try{
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.single_row1,null,false);
                    list_text = (TextView) convertView.findViewById(R.id.list_text);

                    list_text.setText(value.get(position).getBuildingname());
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_building, menu);
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
