package com.example.asif.cuny;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class ActivationActivity extends ActionBarActivity {

    EditText act,email;
    ImageView activ;
    private String activation_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        init();
        Activate();
    }

    public void init(){
        View view = findViewById(R.id.layout_act);
        view.getBackground().setAlpha(235);
        email = (EditText) findViewById(R.id.emailet3);
        act = (EditText) findViewById(R.id.activat_code);
        activ = (ImageView) findViewById(R.id.activation);


    }

    public void Activate(){
        try {
            activ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils utils = new Utils(ActivationActivity.this);
                    String activartioncode = utils.getdata("activation");
                    if (act.getText().toString().equals(activartioncode)) {
                        Toast.makeText(getApplicationContext(), "You have been activate your account", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ActivationActivity.this, MainActivity.class));
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activation, menu);
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
