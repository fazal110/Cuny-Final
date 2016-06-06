package com.example.asif.cuny;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.cuny.DataBase.BAL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class EMICourseRegister extends ActionBarActivity {

    private Intent intent;
    private TextView title_text;
    private Utils utils;
    private BAL bal;
    EditText agency,Phno,comment,fullname,email;
    private SimpleDateFormat dateFormat;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emicourse_register);
        init();
        setActionBar1();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void init() {
        intent = getIntent();
        utils = new Utils(EMICourseRegister.this);
        title_text = (TextView) findViewById(R.id.title_txt);
        fullname = (EditText) findViewById(R.id.fullname);
        String fname = utils.getdata("UserName");
        fullname.setText(fname);
        String email1 = utils.getdata("Email");
        email = (EditText) findViewById(R.id.email);
        email.setText(email1);
        agency = (EditText) findViewById(R.id.agency);
        Phno = (EditText) findViewById(R.id.phoneno);
        comment = (EditText) findViewById(R.id._comment);
        title_text.setText(intent.getStringExtra("title"));
        bal = new BAL(EMICourseRegister.this);
    }

    public void SubmitClick(View view){
        if(fullname.getText().toString().equals("") || Phno.getText().toString().equals("") ||
                email.getText().toString().equals("") || agency.getText().toString().equals("") ||
                title_text.getText().toString().equals("") || comment.getText().toString().equals("")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(EMICourseRegister.this);
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
        submit();
    }

    public void submit(){
        Thread thread_submit = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    int userid = Integer.valueOf(utils.getdata("UserID"));
                    int courseid = intent.getIntExtra("courseid",0);
                    String fullname = utils.getdata("UserName");
                    String agenci = agency.getText().toString();
                    String phone = Phno.getText().toString();
                    String email = utils.getdata("Email");
                    String comm = comment.getText().toString();
                    dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                    date = new Date();
                    String link = "https://celeritas-solutions.com/cds/hivelet/registerForEMICourse.php?UserID="+ userid+
                            "&CourseID="+courseid+"&UserFullName="+fullname+"&UserEmailAddress="+email+"&UserAgency=" +agenci+
                            "&UserPhone="+phone+"&UserComments="+comm+"&UserRegisterationDateTime="+dateFormat.format(date);
                    link = link.replace(" ","%20");
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    Log.d("response",response);

                    if(response.equalsIgnoreCase("Records Added.")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Record Added Success",Toast.LENGTH_LONG).show();

                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Record Not Added, Please Try Again",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    startActivity(new Intent(EMICourseRegister.this,EMICourseRegister.class));
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_submit.start();
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.emi_register_actionbar, null);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emicourse_register, menu);
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
