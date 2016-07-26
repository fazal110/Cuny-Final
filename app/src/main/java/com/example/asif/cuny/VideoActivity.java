package com.example.asif.cuny;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.asif.cuny.DataBase.BAL;


public class VideoActivity extends Activity {

    private VideoView video;
    private MediaController media_Controller;
    private DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
        Intent i = getIntent();
        String url = i.getStringExtra("url");
        playvideo(url);
    }

    private void init() {
        video = (VideoView) findViewById(R.id.surface_view);

    }

    public void BackClick(View view){
        finish();
    }
    
    private void playvideo(String url){
        media_Controller = new MediaController(this);
        //dm = new DisplayMetrics();
        //this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //int height = dm.heightPixels;
        //int width = dm.widthPixels;
        //video.setMinimumWidth(width);
        //video.setMinimumHeight(height);
        video.setMediaController(media_Controller);
        video.setVideoPath(url);
        video.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video, menu);
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
