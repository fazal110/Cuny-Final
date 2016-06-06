package com.example.asif.cuny;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.cuny.DataBase.AskExpertModel;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.DataBeen;

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


public class AskExpertActivity extends ActionBarActivity {

    private ArrayList<AskExpertModel> list;
    private ListView lv;
    private BAL dboperations;
    private EditText ques;
    private DataBeen been;
    private Utils utils;
    private SimpleDateFormat dateFormat;
    private Date date;
    ArrayList<String> title = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_expert);
        init();
        setActionBar1();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void init(){
        lv = (ListView) findViewById(R.id.question_list);
        list = new ArrayList<AskExpertModel>();
        dboperations = new BAL(AskExpertActivity.this);
        ques = (EditText) findViewById(R.id.question_et);
        been = new DataBeen();
        utils = new Utils(AskExpertActivity.this);
        getdatafromserver();
    }

    public void setActionBar1(){
        try{
            ActionBar actionBar = getSupportActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            View customview = getLayoutInflater().inflate(R.layout.ask_expert_actionbar,null);
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

    public void SubmitClick(View view){
        try{
            if(ques.getText().toString().equals("")){
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AskExpertActivity.this);
                builder1.setTitle("Warning!!");
                builder1.setMessage("All the field must be filled to further proceed....");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
            submit();
        }catch (Exception e){e.printStackTrace();}

    }

    public void submit(){
        Thread thread_submit = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                    date = new Date();
                    String link = "https://celeritas-solutions.com/cds/hivelet/addAskTheExpert.php?UserID="
                            + utils.getdata("UserID") + "&RequestDateTime=" + dateFormat.format(date) +
                            "&RequestText=" + ques.getText().toString();
                    link = link.replace(" ", "%20");
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    Log.i("", "Response : " + response);
                    if (response.equalsIgnoreCase("Records Added.")) {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Thanks for using Cuny, You have submitted your questions", Toast.LENGTH_LONG).show();
                                ques.setText("");
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Soory, Failed to submit", Toast.LENGTH_LONG).show();
                            }
                        });

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_submit.start();
    }

    public void getdatafromserver(){
        Thread thread_fetchdata = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    String userId = utils.getdata("UserID");
                    String link = "https://celeritas-solutions.com/cds/hivelet/getResponseFromExpert.php?UserID="+userId;
                    link = link.replace(" ", "%20");
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    Log.i("", "Response : " + response);

                    JSONArray jsonarray = new JSONArray(response);
                    for(int i=0;i<jsonarray.length();i++){
                        JSONObject jsonobj = jsonarray.getJSONObject(i);
                        String ques = jsonobj.getString("RequestText");
                        String desc = jsonobj.getString("ResponseText");
                        list.add(new AskExpertModel(ques,desc));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowActivityList();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_fetchdata.start();
    }

    public void ShowActivityList() {
        ListCustomAdapter adapter = new ListCustomAdapter(AskExpertActivity.this, R.layout.activity_browse, list);
        lv.setAdapter(adapter);
    }

    public class ListCustomAdapter extends ArrayAdapter<AskExpertModel> {

        private TextView list_title;
        private TextView list_desc;
        List<AskExpertModel> listdata;

        public ListCustomAdapter(Context context, int resource,List<AskExpertModel> objects1) {
            super(context, resource, objects1);
            listdata = objects1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try{
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.askexpert_list_row,null,false);
                    list_title = (TextView) convertView.findViewById(R.id.title_lst);
                    list_desc = (TextView) convertView.findViewById(R.id.description_lst);
                    list_title.setText(listdata.get(position).getQues());
                    list_desc.setText(listdata.get(position).getDesc());
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ask_expert, menu);
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
