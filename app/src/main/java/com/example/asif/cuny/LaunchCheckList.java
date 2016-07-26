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

import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.CustomFonts.CustomEditText;
import com.example.asif.cuny.CustomFonts.CustomTextView;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.DataBaseHelper;
import com.example.asif.cuny.DataBase.DataDB;

import java.util.ArrayList;
import java.util.List;


public class LaunchCheckList extends ActionBarActivity {

    private String id;
    private ListView lv;
    private BAL bal;
    private Utils utils;
    private ListCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_check_list);
        setActionBar1();
        init();
        //bal.deleteAll("UserCheckListsResponseMaster");

    }

    private void init() {
        lv = (ListView) findViewById(R.id.list_past_checklist);
        bal = new BAL(getApplicationContext());
        utils = new Utils(getApplicationContext());
        setlist();
    }

    public void setlist(){
        try{
            DataBaseHelper databasehelper = new DataBaseHelper(getApplicationContext());
            databasehelper.createDataBase();
            databasehelper.openDataBase();
            final ArrayList<String> list = databasehelper.getpastCheckListData(utils.getdata("UserID"));
            adapter = new ListCustomAdapter(getApplicationContext(),R.layout.activity_launch_check_list,list);
            if(list.size()==0){
                lv.setVisibility(View.GONE);
            }
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(LaunchCheckList.this,CheckListMaster.class);
                    intent.putExtra("FromList",true);
                    try{
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                        dataBaseHelper.createDataBase();
                        dataBaseHelper.openDataBase();
                        Cursor data = dataBaseHelper.getDataFromDB("EquipmentCategoryTitle", list.get(i), "EquipmentCategoryMaster", true);
                        if(data.getCount()>0){
                            while (data.moveToNext()){
                                int id = data.getInt(0);
                                intent.putExtra("id",String.valueOf(id));
                                intent.putExtra("isupdates","yes");
                            }
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });
            adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void SelectCheckListClick(View view){
        try{
            Intent i = new Intent(LaunchCheckList.this,BrowseEquipment.class);
            i.putExtra("status1","status");
            startActivityForResult(i, ApplicationConstant.select_equipment_activity_requestcode);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LaunchClick(View view){
        Intent i = new Intent(LaunchCheckList.this,CheckListMaster.class);
        i.putExtra("id",id);
        i.putExtra("IsCheckList","launch");
        startActivity(i);
    }

    public void setActionBar1(){
       try{
           ActionBar actionBar = getSupportActionBar();
           actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
           actionBar.setDisplayShowCustomEnabled(true);
           actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
           View customview = getLayoutInflater().inflate(R.layout.launch_checklist_actionbar,null);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == ApplicationConstant.select_equipment_activity_requestcode){

                ((CustomEditText)findViewById(R.id.select_launch_chcklist)).setText(data.getStringExtra("Title"));
                 id = data.getIntExtra("catgid",0)+"";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setlist();
    }

    public class ListCustomAdapter extends ArrayAdapter<String> {
        List<String> value;
        private TextView list_text;
        private ImageView image_icon;
        private ArrayList<String> listResType = new ArrayList<>();
        int i=0;
        private ArrayList<String> worldpopulationlist;


        public ListCustomAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            value = objects;
            worldpopulationlist = new ArrayList<String>();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            try{
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.single_row1,null,false);
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
        getMenuInflater().inflate(R.menu.menu_launch_check_list, menu);
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
