package com.example.asif.cuny;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asif.cuny.DataBase.ViewAlert;

import java.util.ArrayList;
import java.util.List;


public class ViewAlerts extends ActionBarActivity {

    private ListView lv;
    private List<ViewAlert> mArrayList = new ArrayList<ViewAlert>();
    private TextView list_text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alerts);
        setActionBar1();
        init();
        showlist();
    }

    private void init() {
        lv = (ListView) findViewById(R.id.list_viewalert);
    }

    public void showlist(){
        mArrayList.add(new ViewAlert("Maintenance schedule for month starting 1/10/2016 not completed yet","1/11/2016 10:30 AM"));
        mArrayList.add(new ViewAlert("Maintenance schedule for month starting 1/10/2016 not completed yet","1/12/2016 10:30 AM"));
        ListCustomAdapter adapter = new ListCustomAdapter(ViewAlerts.this,R.layout.activity_browse,mArrayList);
        lv.setAdapter(adapter);
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.viewalert_actionbar,null);
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

    public class ListCustomAdapter extends ArrayAdapter<ViewAlert> {
        List<ViewAlert> value;
        private TextView list_text;

        public ListCustomAdapter(Context context, int resource, List<ViewAlert> objects) {
            super(context, resource, objects);
            value = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try{
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.viewalert_single_row,null,false);
                    list_text = (TextView) convertView.findViewById(R.id.list_text);
                    list_text1 = (TextView) convertView.findViewById(R.id.list_text1);
                    list_text.setText(value.get(position).getViewalert());
                    list_text1.setText(value.get(position).getDatatime());
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_alerts, menu);
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
