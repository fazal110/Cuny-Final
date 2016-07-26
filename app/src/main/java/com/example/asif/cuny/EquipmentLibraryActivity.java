package com.example.asif.cuny;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.EmiModel;
import com.example.asif.cuny.DataBase.EquipLibrary;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;


public class EquipmentLibraryActivity extends ActionBarActivity {

    private ListView lv;
    private ArrayList<String> quantitylist;
    private ArrayList<String> titleList;
    private BAL bal;
    private TextView first;
    private TextView second;
    private TextView third;
    private TextView fourth;
    private TextView fifth;
    private View filterview;
    private View line1;
    private View line2;
    private View line3;
    private View line4;
    private View line5;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_library);
        init();
        setActionBar1();
        showlist();
    }

    private void showlist() {
        deleteall();
        importcsv();
        ArrayList<EquipLibrary> list1 = bal.getAllDataFromEqupimentLibrary();
        for(int i=0;i<list1.size();i++){
            titleList.add(list1.get(i).getEquipname());
            quantitylist.add(String.valueOf(list1.get(i).getEquipquantityavailable()));
        }
        ListCustomAdapter adapter = new ListCustomAdapter(EquipmentLibraryActivity.this,R.layout.activity_browse,titleList,quantitylist);
        lv.setAdapter(adapter);
        listclick();

    }

    public void listclick(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<EquipLibrary> list1 = bal.getAllDataFromEqupimentLibrary();
                String grpid = list1.get(i).getGroupid();
                Intent intent = new Intent(EquipmentLibraryActivity.this,EquipmentLibraryRequestActivity.class);
                intent.putExtra("title",titleList.get(i));
                intent.putExtra("grpid",grpid);
                intent.putExtra("assetid",i+1);
                startActivity(intent);
            }
        });
    }

    public void deleteall(){
        bal.deleteAllEquipment();
    }

    private void init() {
        lv = (ListView) findViewById(R.id.list_equipment_library);
        quantitylist = new ArrayList<String>();
        titleList = new ArrayList<String>();
        utils = new Utils(EquipmentLibraryActivity.this);
        bal = new BAL(EquipmentLibraryActivity.this);
        filterview = (View)findViewById(R.id.char_layout);
        first = (TextView)findViewById(R.id.chara);
        second = (TextView)findViewById(R.id.charf);
        third = (TextView)findViewById(R.id.chark);
        fourth = (TextView)findViewById(R.id.charq);
        fifth = (TextView)findViewById(R.id.charu);
        line1 = (View)findViewById(R.id.line1);
        line2 = (View)findViewById(R.id.line2);
        line3 = (View)findViewById(R.id.line3);
        line4 = (View)findViewById(R.id.line4);
        line5 = (View)findViewById(R.id.line5);
        filterclick();
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.equipment_lib_actionbar, null);
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

    public void filterclick(){

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                line5.setVisibility(View.GONE);
                quantitylist = new ArrayList<String>();
                titleList = new ArrayList<String>();
                ArrayList<EquipLibrary> list1 = bal.getFilterDataEquipmentLibrary("AE");
                for (int i=0;i<list1.size();i++){
                    titleList.add(list1.get(i).getEquipname());
                    quantitylist.add("Available Units: "+list1.get(i).getEquipquantityavailable());
                }
                ListCustomAdapter adapter = new ListCustomAdapter(EquipmentLibraryActivity.this,R.layout.activity_browse,titleList,quantitylist);
                lv.setAdapter(adapter);
                listclick();

            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                line5.setVisibility(View.GONE);
                quantitylist = new ArrayList<String>();
                titleList = new ArrayList<String>();
                ArrayList<EquipLibrary> list1 = bal.getFilterDataEquipmentLibrary("FJ");
                for (int i=0;i<list1.size();i++){
                    titleList.add(list1.get(i).getEquipname());
                    quantitylist.add("Available Units: "+list1.get(i).getEquipquantityavailable());
                }

                ListCustomAdapter adapter = new ListCustomAdapter(EquipmentLibraryActivity.this,R.layout.activity_browse,titleList,quantitylist);
                lv.setAdapter(adapter);
                listclick();

            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.VISIBLE);
                line4.setVisibility(View.GONE);
                line5.setVisibility(View.GONE);
                quantitylist = new ArrayList<String>();
                titleList = new ArrayList<String>();
                ArrayList<EquipLibrary> list1 = bal.getFilterDataEquipmentLibrary("KP");
                for (int i=0;i<list1.size();i++){
                    titleList.add(list1.get(i).getEquipname());
                    quantitylist.add("Available Units: "+list1.get(i).getEquipquantityavailable());
                }
                ListCustomAdapter adapter = new ListCustomAdapter(EquipmentLibraryActivity.this,R.layout.activity_browse,titleList,quantitylist);
                lv.setAdapter(adapter);
                listclick();

            }
        });

        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.VISIBLE);
                line5.setVisibility(View.GONE);
                quantitylist = new ArrayList<String>();
                titleList = new ArrayList<String>();
                ArrayList<EquipLibrary> list1 = bal.getFilterDataEquipmentLibrary("QT");
                for (int i=0;i<list1.size();i++){
                    titleList.add(list1.get(i).getEquipname());
                    quantitylist.add("Available Units: "+list1.get(i).getEquipquantityavailable());
                }
                ListCustomAdapter adapter = new ListCustomAdapter(EquipmentLibraryActivity.this,R.layout.activity_browse,titleList,quantitylist);
                lv.setAdapter(adapter);
                listclick();

            }
        });

        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                line5.setVisibility(View.VISIBLE);
                quantitylist = new ArrayList<String>();
                titleList = new ArrayList<String>();
                ArrayList<EquipLibrary> list1 = bal.getFilterDataEquipmentLibrary("UZ");
                for (int i=0;i<list1.size();i++){
                    titleList.add(list1.get(i).getEquipname());
                    quantitylist.add("Available Units: "+list1.get(i).getEquipquantityavailable());
                }
                ListCustomAdapter adapter = new ListCustomAdapter(EquipmentLibraryActivity.this,R.layout.activity_browse,titleList,quantitylist);
                lv.setAdapter(adapter);
                listclick();

            }
        });
    }

    public void importcsv(){
        List<String[]> list = new ArrayList<String[]>();
        String next[] = {};
        try {
            InputStreamReader csvStreamReader = new InputStreamReader(
                    this.getAssets().open(
                            "equipmentLibraryMaster1.csv"));

            CSVReader reader = new CSVReader(csvStreamReader);
            for (;;) {
                next = reader.readNext();
                if (next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {

                titleList.add(list.get(i)[2]);
                quantitylist.add("Available Units: "+list.get(i)[6]);
                list.get(i)[4] = list.get(i)[4].replace("-","");
            EquipLibrary been = new EquipLibrary(list.get(i)[1],list.get(i)[2],list.get(i)[3],list.get(i)[4],
                    Integer.valueOf(list.get(i)[5]),Integer.valueOf(list.get(i)[6])
            ,list.get(i)[7],Integer.valueOf(list.get(i)[8]),list.get(i)[9]);
            bal.InsertDatainEquipmentLibrary(been);

        }
    }

    public class ListCustomAdapter extends ArrayAdapter<String> {
        List<String> value,quantity1;
        private TextView list_text,quantity;

        public ListCustomAdapter(Context context, int resource, List<String> objects,List<String> quantity) {
            super(context, resource, objects);
            value = objects;
            quantity1 = quantity;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try{
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.equiplibrarylist_row,null,false);
                    list_text = (TextView) convertView.findViewById(R.id.title_text);
                    quantity = (TextView) convertView.findViewById(R.id.list_text_quantity);
                    list_text.setText(value.get(position));
                    quantity.setText(quantity1.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipment_library, menu);
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
