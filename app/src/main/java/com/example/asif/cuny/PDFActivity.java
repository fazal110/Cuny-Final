package com.example.asif.cuny;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.CustomFonts.CustomTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class PDFActivity extends Activity {

    private WebView wv;
    private ImageView donebtn,emailbtn;
    LinearLayout layout;
    private CustomTextView pdftext;
    private String url;
    private File pdfattachedfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        init();
        showpdf();
    }

    private void init() {
        donebtn = (ImageView) findViewById(R.id.donebtn);
        emailbtn = (ImageView) findViewById(R.id.emailbtn);
        layout = (LinearLayout) findViewById(R.id.layout_header);
    }

    public void layoutClick(View view){
        layout.setBackground(new ColorDrawable(Color.parseColor("#000000")));
        emailbtn.setVisibility(View.VISIBLE);
        donebtn.setVisibility(View.VISIBLE);
    }

    public void DoneClick(View view){
        finish();
    }

    public void EmailClick(View view){
       try{
           writePdfFile();
           Intent intent = new Intent(Intent.ACTION_SEND);
           intent.putExtra(Intent.EXTRA_SUBJECT,"DOCUMENT");
           intent.putExtra(Intent.EXTRA_TEXT,"Please, Find the attached document...");
           intent.setType("text/plain");
           intent.setType("application/pdf");
           intent.setType("message/rfc822");
           intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pdfattachedfiles));
           startActivityForResult(Intent.createChooser(intent, "Send Email"), ApplicationConstant.Content_pdf_constant);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public void WebViewClick(View view){
        emailbtn.setVisibility(View.GONE);
        donebtn.setVisibility(View.GONE);
        pdftext.setVisibility(View.VISIBLE);
    }


    private void showpdf() {
        try {
            Intent i = getIntent();
            url = i.getStringExtra("url");
            String urlupdated = url+".pdf&overridemobile=true";
            wv = (WebView)findViewById(R.id.pdfview);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setPluginState(WebSettings.PluginState.ON);


            wv.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                    view.loadUrl(url);
                    return true;
                }
            });
            wv.loadUrl("https://docs.google.com/viewer?url="+url);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void writePdfFile(){
        Thread thread_writePdf = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File rootSdDirectory = Environment.getExternalStorageDirectory();

                    pdfattachedfiles = new File(rootSdDirectory, "attachment.pdf");
                    if (pdfattachedfiles.exists()) {
                        pdfattachedfiles.delete();
                    }
                    pdfattachedfiles.createNewFile();

                    FileOutputStream fos = new FileOutputStream(pdfattachedfiles);

                    URL url1 = new URL("http://freecs13.hostei.com/celeritas-solutions/"+url+".pdf");
                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);
                    connection.connect();
                    InputStream in = connection.getInputStream();

                    byte[] buffer = new byte[1024];
                    int size = 0;
                    while ((size = in.read(buffer)) > 0) {
                        fos.write(buffer, 0, size);
                    }
                    fos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread_writePdf.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pd, menu);
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
