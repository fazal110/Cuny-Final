package com.example.asif.cuny;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.cuny.DataBase.DataBaseHelper;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class EquipmentLibraryRequestActivity extends ActionBarActivity {


    private TextView text_title;
    private Intent intent;
    private EditText phno;
    private EditText email;
    private EditText company;
    private EditText quant;
    private SimpleDateFormat dateFormat;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_library_request);
        init();
        setActionBar1();
    }

    private void init() {
        text_title = (TextView) findViewById(R.id.title_txt);
        intent = getIntent();
        text_title.setText(intent.getStringExtra("title"));
        phno = (EditText) findViewById(R.id.phonelib);
        email = (EditText) findViewById(R.id.emailLib);
        company = (EditText) findViewById(R.id.companyLib);
        quant = (EditText) findViewById(R.id.quantitylib);
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.equipreq_actionbar, null);
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

    public void ReviewPDF(View view){
        try{
            DataBaseHelper dataBaseHelper = new DataBaseHelper(EquipmentLibraryRequestActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            Cursor cursor = dataBaseHelper.getDataFromDB("","","EquipmentLibraryMaster",false);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String text_title_header = text_title.getText().toString();
                    if(text_title_header.equalsIgnoreCase(cursor.getString(2))){
                        String url = cursor.getString(10);
                        Intent i = new Intent(EquipmentLibraryRequestActivity.this,PDFActivity.class);
                        i.putExtra("url",url);
                        startActivity(i);
                    }
                }
            }
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    public void SubmitRequestClick(View view){
        if(text_title.getText().toString().equals("") || phno.getText().toString().equals("") ||
                email.getText().toString().equals("") || company.getText().toString().equals("") || quant.getText().toString().equals("")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(EquipmentLibraryRequestActivity.this);
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
        }else
        submit_request();
    }

    public void submit_request(){
        Thread thread_submitdata = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    String grpid = intent.getStringExtra("grpid");
                    int assetid = intent.getIntExtra("assetid",0);
                    int userid = RegisterActivity.randInt(10,1000);
                    String ph_no = phno.getText().toString();
                    String e_mail = email.getText().toString();
                    int quantity = Integer.valueOf(quant.getText().toString());
                    dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
                    date = new Date();
                    String company_req = company.getText().toString();
                    String link = "https://celeritas-solutions.com/cds/hivelet/addEquipmentLibraryRequest.php?EquipmentAssetID="+assetid +
                            "&EquipmentGroupID="+grpid+"&RequestorUserID="+userid+"&RequestorPhone="+ph_no+"&RequestorEmailAddress="+e_mail+"&RequestorCompany="+company_req+
                            "&RequestorQuantityNeeded="+quantity+"&RequestAddedDateTime="+dateFormat.format(date);

                   link = link.replace(" ","%20");
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    Log.i("Response", "Response : " + response);

                    if(response.equalsIgnoreCase("Records Added.")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            final Toast toast =  Toast.makeText(getApplicationContext(),"Record Submited Succesfully",Toast.LENGTH_LONG);
                                toast.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        toast.cancel();
                                    }
                                },15000);
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Toast toast =  Toast.makeText(getApplicationContext(),"Sorry, Record Not Submited",Toast.LENGTH_LONG);
                                toast.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        toast.cancel();
                                    }
                                },15000);
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_submitdata.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipment_library_request, menu);
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
