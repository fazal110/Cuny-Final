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

import java.util.ArrayList;
import java.util.List;


public class AdminActivity extends ActionBarActivity {

    private ArrayList<String> list;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();
        setActionBar1();
        setList();
    }

    public void init(){
        lv = (ListView) findViewById(R.id.list_admin);
    }

    public void HomeClick(View view){
        Intent i = new Intent(AdminActivity.this,HomeActivity.class);
        i.putExtra("status","fromAdmin");
        startActivity(i);
        finish();
    }

    public void BuildingClick(View view){
        Intent i = new Intent(AdminActivity.this,BuildingActivity.class);
        startActivity(i);
        finish();
    }

    public void ResourceClick(View view){
        Intent i = new Intent(AdminActivity.this,ResourceActivity.class);
        startActivity(i);
        finish();
    }

    public void setActionBar1(){
       try{
           ActionBar actionBar = getSupportActionBar();
           actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
           actionBar.setDisplayShowCustomEnabled(true);
           actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
           View customview = getLayoutInflater().inflate(R.layout.admin_actionbar,null);
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

    public void setList(){
        list = new ArrayList<String>();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_list_item_1,list);
        ListCustomAdapter adapter = new ListCustomAdapter(AdminActivity.this,R.layout.activity_list,list);
        lv.setAdapter(adapter);
        list.add("EMI Course Registration");
        list.add("Share Feedback");
        list.add("Change Password");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                switch (pos){
                    case 0:{
                        startActivity(new Intent(AdminActivity.this,EMICourseRegistrationActivity.class));
                        break;
                    }
                    case 1:{
                        startActivity(new Intent(AdminActivity.this,ShareFeedBackActivity.class));
                        break;
                    }
                    case 2:{
                        startActivity(new Intent(AdminActivity.this,ChangePasswordActivity.class));
                        break;
                    }
                }
            }
        });
        adapter.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.menu_admin, menu);
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
