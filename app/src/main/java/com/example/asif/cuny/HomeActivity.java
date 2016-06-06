package com.example.asif.cuny;

import android.app.KeyguardManager;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.asif.cuny.DataBase.DBConnect;
import com.example.asif.cuny.DataBase.DataDB;
import com.example.asif.cuny.HomePress.HomeWatcher;
import com.example.asif.cuny.HomePress.OnHomePressListener;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends ActionBarActivity {

    private ArrayList<String> list;
    ListView lv;
    private ProgressDialog progress;
    private Utils utils;
    private boolean isfirst_time;
    private boolean islock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
        setActionBar1();
        setList();
        onHomePress();

    }

    private void onHomePress() {
        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressListener() {
            @Override
            public void onHomePressed() {
                // do something here...
                getIntent().removeExtra("status");
            }
            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();
    }

    public void AdminClick(View view){
        Intent i = new Intent(HomeActivity.this,AdminActivity.class);
        startActivity(i);
        finish();
    }

    public void BuildingClick(View view){
        Intent i = new Intent(HomeActivity.this,BuildingActivity.class);
        startActivity(i);
        finish();
    }

    public void ResourceClick(View view){
        Intent i = new Intent(HomeActivity.this,ResourceActivity.class);
        startActivity(i);
        finish();
    }

    public void init(){
        lv = (ListView) findViewById(R.id.home_list);
        utils = new Utils(getApplicationContext());
        if(getIntent().getStringExtra("status")==null) {
            Login();
        }


    }

    public void setList(){
        list = new ArrayList<String>();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_list_item_1,list);
        ListCustomAdapter adapter = new ListCustomAdapter(HomeActivity.this,R.layout.activity_list,list);
        lv.setAdapter(adapter);
        list.add("Browse By Activity");
        list.add("Browse By Equipment");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        startActivity(new Intent(HomeActivity.this, BrowseActivity.class));
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(HomeActivity.this, BrowseEquipment.class));
                        break;
                    }

                }
            }
        });

        adapter.notifyDataSetChanged();
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.tool_bar, null);
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


    public class ListCustomAdapter extends ArrayAdapter<String>{
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

    public void Login(){
        Thread thread_ligin = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    //showPB();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv.setEnabled(false);
                            final Toast toast = Toast.makeText(getApplicationContext(), "Please wait, Data is updating...", Toast.LENGTH_LONG);
                            toast.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 60000);
                        }
                    });

                    String link = "http://celeritas-solutions.com/cds/hivelet/isNewContent.php";
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    BAL bal = new BAL(getApplicationContext());
                    bal.deleteAll(DBConnect.TABLE_NAME2);
                    Log.i("Res", "Response : " + response);
                    JSONArray jsonarray = new JSONArray(response);
                    for(int i=0;i<jsonarray.length();i++){
                        JSONObject jsonObject = jsonarray.getJSONObject(i);
                        bal.InsertDatainContentMaster(new DataDB(jsonObject.getString("ContentTitle"),jsonObject.getString("ContentDescription"),
                                jsonObject.getString("ResourceType"),jsonObject.getString("ContentSize"),jsonObject.getString("EquipmentCategory"),
                                jsonObject.getString("Classification"),jsonObject.getString("ContentLocation"),jsonObject.getString("ContentUploadedDateTime"),
                                jsonObject.getString("ContentUploadedByUserID"),jsonObject.getString("LastUpdateDateTime")));
                    }
                    //hidePB();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv.setEnabled(true);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_ligin.start();
    }

    private void hidePB() {
        if(progress.isShowing()){
            progress.dismiss();
            utils.savedata("isdismis","y");
        }
    }

    private void showPB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress = new ProgressDialog(HomeActivity.this);
                progress.setMessage("Updating Data...");
                progress.setCancelable(false);
                progress.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getStringExtra("status")==null)
        Login();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);



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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("islock",islock);
    }
}
