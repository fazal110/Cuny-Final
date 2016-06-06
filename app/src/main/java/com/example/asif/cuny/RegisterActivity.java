package com.example.asif.cuny;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class RegisterActivity extends ActionBarActivity {

    EditText email,full_name,pass,re_pass;
    ImageView register,back;
    private String aJsonString;
    private Date date;
    private SimpleDateFormat dateFormat;
    private int activation_code;
    private Utils utils;
    private ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        registerClick();
        validate();

    }

    public void init(){
        email = (EditText) findViewById(R.id.emailet);
        full_name = (EditText) findViewById(R.id.full_name);
        pass = (EditText) findViewById(R.id.pass);
        re_pass = (EditText) findViewById(R.id.re_pass);
        register = (ImageView) findViewById(R.id.register1);
        back = (ImageView) findViewById(R.id.back);
        utils = new Utils(RegisterActivity.this);
        back();
    }

    public void back(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public boolean IsValid(){
        if(email.getText().toString().length()==0 || full_name.getText().toString().length()==0 || pass.getText().toString().length()==0
                || re_pass.getText().toString().length()==0){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
            builder1.setTitle("Warning!!");
            builder1.setMessage("All the field must be required to proceed further...");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            return false;
        }else{
            return true;
        }
    }

    public void validate(){
        if(email.getText().toString().length()==0)
            email.setError("Email is Required");
            if(full_name.getText().toString().length()==0)
                full_name.setError("Full Name is Required");
                if(pass.getText().toString().length()==0|pass.getText().toString().length()<4|
                        pass.getText().toString().length()>8)
                    pass.setError("Password must be minimum of 4 digit and maximum to 8 digit");
        if(re_pass.getText().toString().length()==0|re_pass.getText().toString().length()<4|
                re_pass.getText().toString().length()>8)
            re_pass.setError("Retype Password must be minimum of 4 digit and maximum to 8 digit");

                }

    public void CheckEmail(){
        //Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    showPB("Loading....");
                    HttpClient  httpclient = new DefaultHttpClient();

                    String link = "https://celeritas-solutions.com/cds/hivelet/getEmailCuny.php?EmailAddress="+email.getText().toString();
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();


                    httppost.setHeader("Content-type", "application/json");

                    InputStream inputStream = null;
                    String result = null;
                    try {
                        HttpResponse response = httpclient.execute(httppost);
                        Log.i("Responseeeeeee", response.toString());
                        HttpEntity entity = response.getEntity();

                        inputStream = entity.getContent();
                        // json is UTF-8 by default
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null)
                        {
                            sb.append(line + "\n");
                        }
                        result = sb.toString();
                    } catch (Exception e) {
                        // Oops
                        e.printStackTrace();
                    }
                    finally {
                        hidePB();
                        try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                    }
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jObject = jsonArray.getJSONObject(0);
                    aJsonString = jObject.getString("Number");
                   if(aJsonString.equalsIgnoreCase("0")) {
                       try {
                           showPB("Loading....");
                           HttpClient httpclient1 = new DefaultHttpClient();

                           dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                           date = new Date();
                           activation_code = randInt(10, 1000);
                           String link1 = "https://celeritas-solutions.com/cds/hivelet/registerUser.php?EmailAddress=" + email.getText().toString().trim() + "&UserName=" + full_name.getText().toString() +
                                   "&AgencyID=1&AddedDateTime=" + dateFormat.format(date) + "&UserCurrentPassword=" + pass.getText().toString().trim() + "&ActivationCode=" + activation_code;
                           link1 = link1.replaceAll(" ", "%20");
                           link1 = link1.replaceAll("\n","");
                           utils.savedata("pass",pass.getText().toString());
                           utils.savedata("activation",String.valueOf(activation_code));
                           HttpPost httppost1 = new HttpPost(link1);

                           ResponseHandler<String> responseHandler1 = new BasicResponseHandler();
                           final String response1 = httpclient1.execute(httppost1,
                                   responseHandler1);


                           Log.d("responsee", response1);
                           if (response1.equalsIgnoreCase("Records Added.Passwords Added.")) {

                               try {
                                   HttpClient httpclientemail = new DefaultHttpClient();
                                   String senderemail = email.getText().toString();


                                   String emailurl = "http://celeritas-solutions.com/cds/hivelet/emailJ.php?toemail=" + senderemail + "&messageemail=" +
                                           "Thank you for joining Cuny Here is an activation code." +
                                           " Note down and activate your account. Activation Code: " + activation_code;

                                   utils.savedata("activation", String.valueOf(activation_code));
                                   emailurl = emailurl.replaceAll(" ", "%20");


                                   HttpPost httppostemail = new HttpPost(emailurl);

                                   ResponseHandler<String> responseHandleremail = new BasicResponseHandler();
                                   final String response_email = httpclientemail.execute(httppostemail,
                                           responseHandleremail);

                                    hidePB();
                                   Log.d("responsee", response_email);
                               } catch (Exception e) {
                                   e.printStackTrace();
                                   hidePB();
                               }

                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       //Toast.makeText(getApplicationContext(), "Registration success, Activation code has been send to " +
                                         //      "spacified email, Check your email & activate your account", Toast.LENGTH_LONG).show();
                                       final Toast toast = Toast.makeText(getApplicationContext(), "Registration success, Activation code has been sent to " +
                                               "spacified email, Check your email & activate your account", Toast.LENGTH_LONG);
                                       toast.show();
                                       Handler handler = new Handler();
                                       handler.postDelayed(new Runnable() {
                                           @Override
                                           public void run() {
                                               toast.cancel();
                                           }
                                       }, 15000);
                                       startActivity(new Intent(RegisterActivity.this, ActivationActivity.class));

                                   }
                               });
                           }

                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hidePB();
                                Toast.makeText(getApplicationContext(), "Email Already Registered", Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void registerClick(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             boolean isvalid = IsValid();
                if(isvalid) {
                    CheckEmail();
                }
            }
        });
    }

    public static int randInt(int min, int max) {


        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    void showPB(final String message) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pb = new ProgressDialog(RegisterActivity.this);
                pb.setMessage(message);
                pb.show();
            }
        });

    }

    void hidePB() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (pb != null && pb.isShowing())
                    pb.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
