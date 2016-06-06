package com.example.asif.cuny;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.cuny.CheckBoxAdapter.Item;
import com.example.asif.cuny.CheckBoxAdapter.ItemListAdapter;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.DataBaseHelper;
import com.example.asif.cuny.DataBase.EmiModel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;


public class EMICourseRegistrationActivity extends ListActivity {

    ListView lv;
    private ArrayList<String> title, date,schedule,loc;
    ArrayList<Item> listitem = new ArrayList<>();
    private Utils utils;
    private BAL bal;
    public int pos;
    private boolean islongclicked;
    private ItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emicourse_registration);
        init();
        //setactionbar1();
        shoeActivityList();
    }

    private void init() {
        //lv = (ListView) findViewById(R.id.list_emicourse);
        title = new ArrayList<String>();
        date = new ArrayList<String>();
        schedule = new ArrayList<String>();
        loc = new ArrayList<String>();
        utils = new Utils(EMICourseRegistrationActivity.this);
        bal = new BAL(EMICourseRegistrationActivity.this);
        back();

    }

    private void back() {
        View view = findViewById(R.id.emicourse_actionbar);
        ImageView back = (ImageView) view.findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void shoeActivityList() {
        getdata();
        adapter = new ItemListAdapter(EMICourseRegistrationActivity.this,listitem);
        setListAdapter(adapter);
        lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setItemsCanFocus(false);

    }

    private void showSelectedItemIds() {
        SparseBooleanArray checkedItems = lv.getCheckedItemPositions();
        if (checkedItems != null) {
            for (int i=0; i<checkedItems.size(); i++) {
                if (checkedItems.valueAt(i)) {
                    Item item = (Item) lv.getAdapter().getItem(
                            checkedItems.keyAt(i));
                    int pos1 = adapter.getPosition(item);
                    pos = pos1;
                    Log.i("", pos+"");
                }
            }
        }

    }


    public void RegisterClick(View view){
        boolean count1 = getcount1();
        if(count1) {
            Intent intent = new Intent(EMICourseRegistrationActivity.this, EMICourseRegister.class);
            showSelectedItemIds();
            int courseid = pos + 1;
            intent.putExtra("title", listitem.get(pos).getTitle());
            intent.putExtra("courseid", courseid);
            startActivity(intent);
        }else {
            final Toast toast = Toast.makeText(getApplicationContext(), "Please select the EMI Course you would like to register for", Toast.LENGTH_LONG);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 15000);
        }
    }

    public boolean getcount(){
        SparseBooleanArray checkedItems = lv.getCheckedItemPositions();
        ArrayList<Boolean> countbool = new ArrayList<>();
        for(int i=0;i<checkedItems.size();i++){
            if(checkedItems.get(i)==true){
                countbool.add(true);
            }
        }
        int count = countbool.size();
        if(count==0){
            return false;
        }else return true;
        //utils.savedata("count1",count+"");
    }

    public boolean getcount1() {
        int count = 0;
        for (int i = 0; i < lv.getCount(); i++) {
            if (lv.isItemChecked(i)) {
                count++;
            }
        }

        if(count==0) return false;

        return true;
    }




    public void setactionbar1(){
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View customView = getLayoutInflater().inflate(R.layout.emicourse_actionbar, null);
        ImageView imageButton = (ImageView) customView
                .findViewById(R.id.back1);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        actionBar.setCustomView(customView);
        Toolbar parent =(Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

    public class ListCustomAdapter extends ArrayAdapter<String> {
        List<String> value,date,schedule,location;
        private TextView emi_title,emi_date,emi_shedule,emi_loc;
        //int[] posarr = new int[5];
        public ListCustomAdapter(Context context, int resource, List<String> objects, List<String> objdate,
                                 List<String> objschedule, List<String> objloc) {
            super(context, resource, objects);
            value = objects;
            date = objdate;
            schedule = objschedule;
            location = objloc;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            try{

                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.emicourse_single_row,null,false);
                    emi_title = (TextView) convertView.findViewById(R.id.title_emi);
                    emi_date = (TextView) convertView.findViewById(R.id.dates_emi);
                    emi_shedule = (TextView) convertView.findViewById(R.id.shedule_emi);
                    emi_loc = (TextView) convertView.findViewById(R.id.location_emi);
                    final CheckBox chk = (CheckBox) convertView.findViewById(R.id.chk);
                    /*emi_title.setTypeface(custom_font);
                    emi_date.setTypeface(custom_font);
                    emi_shedule.setTypeface(custom_font);
                    emi_loc.setTypeface(custom_font);*/

                    emi_title.setText(value.get(position));
                    emi_date.setText("Dates : "+date.get(position));
                    emi_shedule.setText("Schedule : "+schedule.get(position));
                    emi_loc.setText("Location : " + location.get(position));






            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public void getdata(){
        try {

            DataBaseHelper dataBaseHelper = new DataBaseHelper(EMICourseRegistrationActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            Cursor cursor = dataBaseHelper.getDataFromDB("","","EMICourseCalendar",false);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    Item item = new Item(cursor.getInt(0),cursor.getString(1),cursor.getString(2)+" - "+cursor.getString(3)
                            ,cursor.getString(4),cursor.getString(7));
                    listitem.add(item);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emicourse_registration, menu);
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
