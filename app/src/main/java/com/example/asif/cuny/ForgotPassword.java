package com.example.asif.cuny;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.asif.cuny.CustomFonts.CustomEditText;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URLEncoder;


public class ForgotPassword extends ActionBarActivity {

    private CustomEditText email_text;
    private ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    private void init() {
        email_text = (CustomEditText) findViewById(R.id.emailet);

    }

    public void SubmitClick(View view){
        getUserPassword();
    }

    public void getUserPassword(){
        Thread getpass_thread = new Thread(new Runnable() {
            @Override
            public void run() {
              try{
                  showPB("Loading....");
                  HttpClient httpclient = new DefaultHttpClient();
                  //showPB("Loading....");
                  String link = "http://celeritas-solutions.com/cds/hivelet/getPasswordCunyForgot.php?UserEmailAddress="
                          +email_text.getText().toString();
                  HttpPost httppost = new HttpPost(link);

                  ResponseHandler<String> responseHandler = new BasicResponseHandler();
                  final String response = httpclient.execute(httppost,
                          responseHandler);
                  String res = response;
                  res = response.replace("\n","");
                  SendPassword(res);
                  Log.i("Response", "Response : " + response);

              }catch (Exception e){
                  e.printStackTrace();
              }
            }
        });
        getpass_thread.start();
    }

    public void SendPassword(final String password){
        Thread email_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HttpClient httpclient = new DefaultHttpClient();

                    String msg = "<html><body>Please view the requested login credentials below‎.<br>" +
                            "Username: " +email_text.getText().toString()+
                            "<br /> Password: "+password+"</body></html>";
                    msg = msg.replace("<br />","%0A");

                    msg = msg.replace(" ","%20");
                    //msg = URLEncoder.encode(msg,"UTF-8");
                    String link = "http://celeritas-solutions.com/cds/hivelet/emailF.php?toemail="
                            +email_text.getText().toString()+"&messageemail="+ Html.fromHtml(msg);
                    link = link.replace(" ","%20");
                    link = link.replace("\n","");
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);
                    hidePB();
                    Log.i("Response", "Response : " + response);
                    if(response.equalsIgnoreCase("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotPassword.this);
                                builder1.setTitle("Success!!");
                                builder1.setMessage("Password has been send to your email...");
                                builder1.setCancelable(true);
                                builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                        });
                    }else{
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotPassword.this);
                        builder1.setTitle("Warning!!");
                        builder1.setMessage("Sorry,Wrong Email Address, Check your email and try again...");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        email_thread.start();
    }

    void showPB(final String message) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pb = new ProgressDialog(ForgotPassword.this);
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
        getMenuInflater().inflate(R.menu.menu_forgot_password, menu);
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
