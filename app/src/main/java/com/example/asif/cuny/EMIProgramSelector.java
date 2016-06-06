package com.example.asif.cuny;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;


public class EMIProgramSelector extends ActionBarActivity {

    private ListView lv;
    private Intent searchrepointent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emiprogram_selector);
        init();
        setActionBar1();
        showlist();
    }

    private void init() {
        lv = (ListView) findViewById(R.id.list_emi);
        searchrepointent = getIntent();
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.restype_selector_actionbar, null);
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

    public void showlist(){
        final ArrayList<String> mArrayList = new ArrayList<String>();
        mArrayList.add("BOC |");
        mArrayList.add("BOC ||");
        ListCustomAdapter adapter = new ListCustomAdapter(EMIProgramSelector.this,R.layout.activity_emiprogram_selector,mArrayList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(searchrepointent.getStringExtra("status")!=null){
                    Intent intentback=new Intent();
                    intentback.putExtra("title",mArrayList.get(i));
                    setResult(ApplicationConstant.select_equipment_restype_requestcode,intentback);
                    finish();
                }
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_emiprogram_selector, menu);
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
