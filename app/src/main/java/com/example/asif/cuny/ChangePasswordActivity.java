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
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ChangePasswordActivity extends ActionBarActivity {

    EditText oldpass,new_pass,retype_pass;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        setActionBar1();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void init() {
        oldpass = (EditText) findViewById(R.id.old_pass);
        new_pass = (EditText) findViewById(R.id._newpassword_et);
        retype_pass = (EditText) findViewById(R.id.retype_pass);
        utils = new Utils(ChangePasswordActivity.this);
    }


    public void SubmitClick(View view){
        if(oldpass.getText().toString().equals("") || new_pass.getText().toString().equals("") || retype_pass.getText().toString().equals("")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ChangePasswordActivity.this);
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
        Change_Password();
    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.changepass_actionbar, null);
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

    public void Change_Password(){
        Thread thread_share_feedback = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    String userid = utils.getdata("Email");
                    String old_pass = oldpass.getText().toString().trim();
                    final String newpass = new_pass.getText().toString().trim();
                    String link = "https://celeritas-solutions.com/cds/hivelet/changePasswordCuny.php?UserID="+userid+
                            "&UserCurrentPassword="+old_pass+"&UserCurrentPasswordUp="+newpass;
                    link = link.replace(" ","%20");
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    Log.i("SharedFeedback", "Response : " + response);
                    if(!response.equalsIgnoreCase("Updated records 0")) {
                        utils.savedata("password",newpass);
                        utils.savedata("pass",newpass);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Your Password has been successfully changed", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ChangePasswordActivity.this, MainActivity.class));
                                finish();
                            }
                        });

                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Your Current Password is Wrong,Check Your Internet Connection & Then Try Again",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_share_feedback.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
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
