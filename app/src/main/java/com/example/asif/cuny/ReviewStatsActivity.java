package com.example.asif.cuny;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.cuny.CustomFonts.CustomTextView;
import com.example.asif.cuny.DataBase.DataBaseHelper;
import com.example.asif.cuny.DataBase.ReviewItem;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ReviewStatsActivity extends ListActivity {

    private ListView lv;
    private Intent intent;
    private String title;
    private String buildingid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_stats);
        init();
    }

    private void init() {
        lv = getListView();
        intent = getIntent();
        title = intent.getStringExtra("Buildingname");
        //title = title.replace(" ","s/");
        setlist();
        back();
    }

    private void back() {
        View view = findViewById(R.id.reviewstats_actionbar);
        ImageView back = (ImageView) view.findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setlist() {
        try{
            DataBaseHelper databasehelper = new DataBaseHelper(ReviewStatsActivity.this);
            databasehelper.createDataBase();
            databasehelper.openDataBase();
            Cursor data = databasehelper.getDataFromDB("BuildingName", title, "DCASBuildingMaster", true);
            if(data.getCount()>0){
                while (data.moveToNext()){
                    buildingid = data.getString(1);

                }
            }

            getdata(buildingid);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getdata(final String buildingId){
        Thread get_reviewstats = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HttpClient  httpclient1 = new DefaultHttpClient();
                    //showPB("Loading....");
                    String link = "http://celeritas-solutions.com/cds/hivelet/GetStatsDataAndroid.php?BuildingID="+buildingId;
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient1.execute(httppost,
                            responseHandler);

                    JSONArray jsonarray = new JSONArray(response);
                    JSONObject jsonobject = jsonarray.getJSONObject(0);

                    String energy_usage = jsonobject.getString("TotalEnergyUsageMMBTU");
                    String energy_star = jsonobject.getString("ENERGYSTARScore");
                    String site_eui = jsonobject.getString("SiteEUI");
                    String nat_mad = jsonobject.getString("NatMedSiteEUIkBtuPerSqFt");
                    String total_ghg = jsonobject.getString("TotalGHGIntensity");

                    final ArrayList<ReviewItem> list = new ArrayList<>();
                    list.add(new ReviewItem("TotalEnergyUsage(MMBTU)",energy_usage));
                    list.add(new ReviewItem("ENERGYSTARScore",energy_star));
                    list.add(new ReviewItem("SiteEUI(kBtuPerSqFt)",site_eui));
                    list.add(new ReviewItem("NatMedSiteEUI(kBtuPerSqFt)",nat_mad));
                    list.add(new ReviewItem("TotalGHGIntensity(kgPerSqFt)",total_ghg));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListCustomAdapter adapter = new ListCustomAdapter(ReviewStatsActivity.this,R.layout.activity_review_stats,list);
                            setListAdapter(adapter);
                        }
                    });
                    Log.i("res", "Response : " + response);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        get_reviewstats.start();
    }


    public class ListCustomAdapter extends ArrayAdapter<ReviewItem> {
        List<ReviewItem> value;
        private TextView list_text,value1;

        public ListCustomAdapter(Context context, int resource, List<ReviewItem> objects) {
            super(context, resource, objects);
            value = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try{
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.review_stats_single_row,null,false);
                    list_text = (TextView) convertView.findViewById(R.id.text_review);
                    value1 = (TextView) convertView.findViewById(R.id.value);

                    list_text.setText(value.get(position).getText());
                    value1.setText(value.get(position).getValue());
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review_stats, menu);
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
