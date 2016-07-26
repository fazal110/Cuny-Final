package com.example.asif.cuny;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.DataBaseHelper;
import com.example.asif.cuny.DataBase.DataBeen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class BrowseActivity extends ActionBarActivity {

    ListView lv;
    private ArrayList<String> mArrayList;
    private BAL bal;
    private Intent searchrepointent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        init();
        setActionBar1();
        getdata();
        ShowActivityList();

    }

    public void getdata(){
        try {
            mArrayList = new ArrayList<>();
            DataBaseHelper dataBaseHelper = new DataBaseHelper(BrowseActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            Cursor cursor = dataBaseHelper.getDataFromDB("","","ClassificationMaster",false);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    mArrayList.add(cursor.getString(1));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(){
        bal = new BAL(BrowseActivity.this);
        lv = (ListView) findViewById(R.id.list_browse);
        searchrepointent = getIntent();
    }

    public void setActionBar1(){
        try{
            ActionBar actionBar = getSupportActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            View customview = getLayoutInflater().inflate(R.layout.browse_actionbar,null);
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

    public void ShowActivityList(){
        try{
            ListCustomAdapter adapter = new ListCustomAdapter(BrowseActivity.this,R.layout.activity_browse,mArrayList);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (searchrepointent.getStringExtra("status") != null) {
                        Intent intentback = new Intent();
                        intentback.putExtra("title", mArrayList.get(i));
                        intentback.putExtra("id", i+1);
                        setResult(ApplicationConstant.select_equipment_activity_requestcode, intentback);
                        finish();
                    } else {
                        switch (i) {
                            case 0: {
                                Intent intent = new Intent(BrowseActivity.this, BrowseResourceActivity.class);
                                intent.putExtra("classifid", 1);
                                startActivity(intent);
                                break;
                            }
                            case 1: {
                                Intent intent = new Intent(BrowseActivity.this, BrowseResourceActivity.class);
                                intent.putExtra("classifid", 2);
                                startActivity(intent);
                                break;
                            }
                            case 2: {
                                Intent intent = new Intent(BrowseActivity.this, BrowseResourceActivity.class);
                                intent.putExtra("classifid", 3);
                                startActivity(intent);
                                break;
                            }

                            case 3: {
                                Intent intent = new Intent(BrowseActivity.this, BrowseResourceActivity.class);
                                intent.putExtra("classifid", 4);
                                startActivity(intent);
                                break;
                            }

                        }
                    }
                }
            });
            adapter.notifyDataSetChanged();
        }catch (Exception e){e.printStackTrace();}
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
                    convertView = inflater.inflate(R.layout.single_row1,null,false);
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
        getMenuInflater().inflate(R.menu.menu_browse, menu);
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
