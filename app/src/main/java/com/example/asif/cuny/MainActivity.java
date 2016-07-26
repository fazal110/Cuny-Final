package com.example.asif.cuny;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asif.cuny.CustomFonts.Checkbox;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "Cuny";
    ImageView register,login;
    EditText email,pass;
    private Utils utils;
    CheckBox remember_me;
    private ProgressDialog pb;
    private boolean isconnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loginclick();
        registerClick();
        getUserEmailPass();
    }

    private void getUserEmailPass() {
        if(utils.getdata("Email")!=null && utils.getdata("password")!=null) {
            remember_me.setChecked(true);
            email.setText(utils.getdata("Email"));
            pass.setText(utils.getdata("password"));
        }else
            remember_me.setChecked(false);
    }

    public void ForgotPasswordClick(View view){
        startActivity(new Intent(MainActivity.this,ForgotPassword.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserEmailPass();
    }

    public void init(){

        View mainview = findViewById(R.id.mainlayout);
        mainview.getBackground().setAlpha(235);
        View view = findViewById(R.id.layout);
        login = (ImageView) view.findViewById(R.id.login1);
        register = (ImageView) view.findViewById(R.id.register);
        email = (EditText) findViewById(R.id.emailet);
        pass = (EditText) findViewById(R.id.pass);
        remember_me = (Checkbox) findViewById(R.id.remember_me);
        utils = new Utils(this);
        isconnected = utils.isConnected(this);

    }

    public boolean IsValid(){
        if(email.getText().length()!=0 || pass.getText().length()!=0){
            return true;
        }
        return false;
    }

    public void loginclick(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
                if(isconnected && IsValid()) {

                    Login();
                }
                else {
                    if(!IsValid()){
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("Warning!!");
                        builder1.setMessage("Email & Password Required");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setTitle("Warning!!");
                    builder1.setMessage("Check Your Internet Connection");
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



            }
        });
    }

    public void Login(){
        Thread thread_ligin = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                  HttpClient  httpclient = new DefaultHttpClient();
                    showPB("Loading....");
                    String link = "http://celeritas-solutions.com/cds/hivelet/getLoginCunyTest.php?UserEmailAddress="
                            +email.getText().toString()+"&UserPassword="+pass.getText().toString();
                   HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    Log.i(TAG, "Response : " + response);
                   if(!response.equalsIgnoreCase("Login Failed")) {
                       JSONArray jsonarray = new JSONArray(response);
                       JSONObject jsonObject = jsonarray.getJSONObject(0);
                       if(remember_me.isChecked()) {
                           utils.savedata("UserID", jsonObject.getString("UserID"));
                           utils.savedata("UserName", jsonObject.getString("UserName"));
                           utils.savedata("Email", jsonObject.getString("EmailAddress"));
                           utils.savedata("password",pass.getText().toString());
                       }else {
                           utils.savedata("Email", "");
                           utils.savedata("password","");

                       }
                       startActivity(new Intent(MainActivity.this,HomeActivity.class));

                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                            // final  toast =  Toast.makeText(getApplicationContext(),"Login Success, Please wait you are redirecting",Toast.LENGTH_LONG).show();
                               final Toast toast = Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG);
                               toast.show();
                               Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       toast.cancel();
                                   }
                               }, 15000);

                           }
                       });

                   }
                    else {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(getApplicationContext(),"Login Failed, Email & Password is Incorrect",Toast.LENGTH_LONG).show();
                           }
                       });

                   }

                    hidePB();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_ligin.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearet();
    }

    void showPB(final String message) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pb = new ProgressDialog(MainActivity.this);
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

    private void clearet() {
        email.setText("");
        pass.setText("");
    }

    public void registerClick(){
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(MainActivity.this,RegisterActivity.class);
               startActivity(i);
           }
       });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
