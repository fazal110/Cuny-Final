package com.example.asif.cuny;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.cuny.Constants.ApplicationConstant;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ResourceActivity extends ActionBarActivity {

    private ListView lv;
    private ArrayList<String> list;
    private Utils utils;
    private String TAG = "Resource";
    private Date date;
    private SimpleDateFormat dateFormat;
    private EditText topic,comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        init();
        setActionBar1();
        setList();

    }

    public void init(){
        lv = (ListView) findViewById(R.id.list_resource);

    }



    public void AdminClick(View view){
        Intent i = new Intent(ResourceActivity.this,AdminActivity.class);
        startActivity(i);
        finish();
    }

    public void BuildingClick(View view){
        Intent i = new Intent(ResourceActivity.this,BuildingActivity.class);
        startActivity(i);
        finish();
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.resource1_actionbar, null);
        //ImageView back = (ImageView) customview.findViewById(R.id.back1);
        actionBar.setCustomView(customview,
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.NO_GRAVITY
                )
        );
    }

    public void HomeClick(View view){
        Intent i = new Intent(ResourceActivity.this,HomeActivity.class);
        i.putExtra("status","fromResource");
        startActivity(i);
        finish();
    }

    public void setList(){
        list = new ArrayList<String>();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_list_item_1,list);
        ListCustomAdapter adapter = new ListCustomAdapter(ResourceActivity.this,R.layout.activity_list,list);
        lv.setAdapter(adapter);
        list.add("Search Repository");
        list.add("Ask The Expert");
        list.add("Equipment Library Request");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                switch (pos){
                    case 0:{
                        Intent intent = new Intent(ResourceActivity.this,SearchRepositoryActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:{
                        startActivity(new Intent(ResourceActivity.this,AskExpertActivity.class));
                        break;
                    }
                    case 2:{
                        startActivity(new Intent(ResourceActivity.this,EquipmentLibraryActivity.class));
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
        getMenuInflater().inflate(R.menu.menu_resource, menu);
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
