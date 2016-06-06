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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.asif.cuny.Constants.ApplicationConstant;


public class SearchRepositoryActivity extends ActionBarActivity {

    private EditText select_equip;
    private EditText activity_equip;
    private EditText restype_equip;
    private EditText keyword;
    private int catid;
    private int catid_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_repository);
        setActionBar1();
        init();
        EditTextClicked();
    }

    private void init() {
        select_equip = (EditText) findViewById(R.id.select_equip_cat);
        activity_equip = (EditText) findViewById(R.id.select_activity_cat);
        restype_equip = (EditText) findViewById(R.id.select_res_type);
        keyword = (EditText) findViewById(R.id.keyword);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void EditTextClicked(){
        select_equip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchRepositoryActivity.this,BrowseEquipment.class);
                i.putExtra("status","SearchRepo");

                startActivityForResult(i, ApplicationConstant.select_equipment_category_requestcode);
            }
        });

        activity_equip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SearchRepositoryActivity.this,BrowseActivity.class);
                i.putExtra("status","SearchRepo");
                startActivityForResult(i, ApplicationConstant.select_equipment_activity_requestcode);
            }
        });

        /*emi_equip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchRepositoryActivity.this,EMIProgramSelector.class);
                i.putExtra("status","SearchRepo");
                startActivityForResult(i, ApplicationConstant.select_equipment_emi_requestcode);
            }
        });*/

        restype_equip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchRepositoryActivity.this,ResourceTypeSelector.class);
                i.putExtra("status","SearchRepo");
                startActivityForResult(i, ApplicationConstant.select_equipment_restype_requestcode);
            }
        });
    }

    public void findClick(View view){
        if(select_equip.getText().toString().equals("") && activity_equip.getText().toString().equals("") &&
                  restype_equip.getText().toString().equals("") &&
                keyword.getText().toString().equals("")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(SearchRepositoryActivity.this);
            builder1.setTitle("Warning!!");
            builder1.setMessage("At least 1 field must be filled to further proceed....");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }else {
            String key = keyword.getText().toString();
            String equip = select_equip.getText().toString();
            String activity = activity_equip.getText().toString();
            String restype = restype_equip.getText().toString();
            Intent i = new Intent(SearchRepositoryActivity.this, BrowseResourceActivity.class);
            i.putExtra("status", "find");
            if (!key.equals("")) {
                i.putExtra("keyword", key);
            }
            if (!equip.equals("")) {
                i.putExtra("equip", equip);
                i.putExtra("id", catid);
            }
            if (!activity.equals("")) {
                i.putExtra("activity", activity);
                i.putExtra("id", catid);
            }
            if (!restype.equals("")) {
                i.putExtra("restype", restype);
                i.putExtra("id", catid);
            }

            if (!key.equals("") && !restype.equals("")) {
                i.putExtra("keyword", key);
                i.putExtra("status", "2");
                i.putExtra("restype", restype);
                i.putExtra("id", catid);
            }

            if (!equip.equals("") && !restype.equals("")) {
                i.putExtra("equip", equip);
                i.putExtra("id", catid);
                i.putExtra("status", "2");
                i.putExtra("restype", restype);
                i.putExtra("id_res", catid_res);
            }

            if (!activity.equals("") && !restype.equals("")) {
                i.putExtra("activity", activity);
                i.putExtra("id", catid);
                i.putExtra("restype", restype);
                i.putExtra("id_res", catid);
            }

            startActivity(i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ApplicationConstant.select_equipment_category_requestcode:{
                if(data!=null) {
                    String title = data.getStringExtra("title");
                    catid = data.getIntExtra("catgid",0);
                    select_equip.setText(title);
                    break;
                }
            }

            case ApplicationConstant.select_equipment_activity_requestcode:{
                if(data!=null) {
                    String title = data.getStringExtra("title");
                    catid = data.getIntExtra("id",0);
                    activity_equip.setText(title);
                }
                break;
            }

            case ApplicationConstant.select_equipment_restype_requestcode:{
                if(data!=null) {
                    String title = data.getStringExtra("title");
                    restype_equip.setText(title);
                    catid = data.getIntExtra("restype",0);
                    catid_res = catid;
                    break;
                }
            }

            case ApplicationConstant.select_equipment_emi_requestcode:{
                if(data!=null) {
                    String title = data.getStringExtra("title");
                    //emi_equip.setText(title);
                    break;
                }
            }
        }

    }

    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.searchrepo_actionbar, null);
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
        getMenuInflater().inflate(R.menu.menu_search_repository, menu);
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
