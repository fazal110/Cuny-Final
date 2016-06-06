package com.example.asif.cuny;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asif.cuny.CheckBoxAdapter.ItemListAdapter1;
import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.CheckList;
import com.example.asif.cuny.DataBase.CheckListItem;
import com.example.asif.cuny.DataBase.DataBaseHelper;
import com.example.asif.cuny.DataBase.LaunchList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CheckListMaster extends ListActivity {

    private ListView lv;
    private Intent intent;
    private ArrayList<CheckList> list;
    private Utils utils;
    private ArrayList<String> list1;
    private String DatetimeResponse;
    private ArrayList<Boolean> ischecked = new ArrayList<>();
    private String itemchecked;
    private String id;
    private String UserId;
    private int CheckListItemid;
    private String Checklistid;
    private String uniquesId;
    private String enddateTime;
    private String status;
    int count;
    private BAL bal;
    private ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_master);
        init();
        setList();
        back();
    }

    private void back() {
        try{
            View view = findViewById(R.id.actionbar);
            ImageView back = (ImageView) view.findViewById(R.id.back1);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {
        lv = getListView();
        intent = getIntent();
        utils = new Utils(CheckListMaster.this);
        Date dNow = new Date( );
        SimpleDateFormat ft =
                new SimpleDateFormat ("dd M yyyy hh:mm a");
        DatetimeResponse = ft.format(dNow);
        bal = new BAL(getApplicationContext());

    }

    public void SaveClick(View view){
        //int count = Integer.valueOf(utils.getdata("count_checklist"));
        getcount1();
        bal.deleteAll("CheckList");
        bal.deleteAll("LaunchListCheckedPosition");
        for(int i=0;i<lv.getCount();i++){
            getdata(i);


        }
        int len = lv.getCount();
        SparseBooleanArray checked = lv.getCheckedItemPositions();
        for (int i = 0; i < len; i++) {
            if (checked.get(i)) {

                    LaunchList launchList = new LaunchList(i, Integer.valueOf(intent.getStringExtra("id")));
                    bal.InsertDatainLaunchList(launchList);

            }
        }
        utils.savedata("id1",intent.getStringExtra("id"));

        utils.savedata("count",String.valueOf(count));
        utils.editor.putBoolean("isFinished",true).commit();
        getdata1();

    }

    public void finishClick(View view){
        getcount1();
        bal.deleteAll("CheckList");
        bal.deleteAll("LaunchListCheckedPosition");
        for(int i=0;i<lv.getCount();i++){
            send_data();
        }

        int len = lv.getCount();
        SparseBooleanArray checked = lv.getCheckedItemPositions();
        for (int i = 0; i < len; i++) {
            if (checked.get(i)) {

                LaunchList launchList = new LaunchList(i, Integer.valueOf(intent.getStringExtra("id")));
                bal.InsertDatainLaunchList(launchList);
            }
        }
        utils.savedata("id1",intent.getStringExtra("id"));
        //utils.editor.putBoolean("isFinished",false).commit();
        send_data1();
    }

    public void getdata(int pos){
       try{
           UserId = utils.getdata("UserID");
           CheckListItemid = list.get(pos).getChecklistitemId();
           Checklistid = list.get(pos).getChecklistId();
           Date dNow = new Date();
           SimpleDateFormat ft =
                   new SimpleDateFormat ("ddMMyyyyhhmm");

           uniquesId = Checklistid + ft.format(dNow);
           boolean checkeditem = ischecked.get(pos);
           if(checkeditem){
               itemchecked = "Yes";
           }else itemchecked = "No";

           insert_data(UserId,Checklistid,String.valueOf(CheckListItemid),uniquesId,DatetimeResponse,itemchecked);

                CheckListItem item = new CheckListItem(Integer.valueOf(intent.getStringExtra("id")), CheckListItemid);
                bal.InsertDatainCheckList(item);


       }catch (Exception e){
           e.printStackTrace();
       }

    }

    public void getdata1(){
        try{
            UserId = utils.getdata("UserID");
            Checklistid = list.get(0).getChecklistId();
            Date dNow = new Date();
            SimpleDateFormat ft =
                    new SimpleDateFormat ("ddMMyyyyhhmm");

            SimpleDateFormat ft1 =
                    new SimpleDateFormat ("dd MM yyyy hh mm a");
            enddateTime = ft1.format(dNow);

            uniquesId = Checklistid + ft.format(dNow);
            status = "Save";

            insert_data1(Checklistid, UserId, DatetimeResponse, enddateTime, uniquesId, status);
            //finish();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void insert_data(String Userid, String Checklistid, String ChecklistItemId, String UniqueId,
                            String CheckListResponseDataTime, String ItemChecked){

            try{
                DataBaseHelper databaseHelper = new DataBaseHelper(CheckListMaster.this);
                databaseHelper.createDataBase();
                databaseHelper.openDataBase();
               boolean isInserted = databaseHelper.InsertUserCheckListResponse(Userid,Checklistid,ChecklistItemId
                        ,UniqueId,CheckListResponseDataTime,itemchecked);
                if(isInserted){
                 //Toast.makeText(getApplicationContext(), "Record Added Successfully", Toast.LENGTH_LONG).show();

                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insert_data1(String Checklistid, String Userid,  String startdate, String enddate, String UniqueId
            , String status){

        try{
            DataBaseHelper databaseHelper = new DataBaseHelper(CheckListMaster.this);
            databaseHelper.createDataBase();
            databaseHelper.openDataBase();
            boolean isInserted = databaseHelper.InsertUserCheckListsListing(Checklistid, Userid, startdate
                    , enddate, UniqueId, status);
            if(isInserted){
                Toast.makeText(getApplicationContext(), "Record Added Successfully", Toast.LENGTH_LONG);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setList(){
        try{
            list = new ArrayList<CheckList>();
            list1 = new ArrayList<String>();
            DataBaseHelper dataBaseHelper = new DataBaseHelper(CheckListMaster.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            if(intent.getStringExtra("id")!=null)
            id = intent.getStringExtra("id");


            Cursor data = dataBaseHelper.getDataFromDB("EquipmentID",id,"CheckListItemMaster",true);
            if(data.getCount()>0){
                while (data.moveToNext()){

                    list.add(new CheckList(data.getInt(0),data.getString(1),data.getString(4)));
                    list1.add(data.getString(4));
                }
            }
            if(intent.getStringExtra("IsCheckList")!=null)
            utils.savedata("IsCheckList","");
            else utils.savedata("IsCheckList","true");

            ItemListAdapter1 adapter = new ItemListAdapter1(CheckListMaster.this,list1);
            setListAdapter(adapter);
            adapter.notifyDataSetChanged();
            lv.setItemsCanFocus(false);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getcount1(){
        ischecked = new ArrayList<Boolean>();
        int count =0;
        for(int i=0;i<lv.getCount();i++){
            if(lv.isItemChecked(i)){
                count++;
                ischecked.add(true);
            }else
                ischecked.add(false);
        }

        utils.savedata("count_checklist",count+"");
        //savepos();
    }

    public void send_data(){
        Thread send_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //showPB("Loading...");
                    HttpClient httpclient = new DefaultHttpClient();

                    String link = "https://celeritas-solutions.com/cds/hivelet/addUserCheckResponses.php?UserID="+UserId+
                            "&CheckListID="+Checklistid+"&CheckListItemChecked="+itemchecked+"&CheckListResponseDateTime="+
                            DatetimeResponse+"&UniqueID="+uniquesId;
                    link = link.replace(" ","%20");
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    Log.i("SharedFeedback", "Response : " + response);

                    CheckListItem item = new CheckListItem(Integer.valueOf(intent.getStringExtra("id")), CheckListItemid);
                    bal.InsertDatainCheckList(item);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        send_thread.start();
    }

    public void send_data1(){
        Thread thread_send_data = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    showPB("Loading...");
                    HttpClient httpclient1 = new DefaultHttpClient();
                    status = "finish";
                    String link1 = "https://celeritas-solutions.com/cds/hivelet/addUserCheckResponses.php?UserID="+UserId+
                            "&CheckListID="+Checklistid+"&CheckListStartedDateTime="+DatetimeResponse+
                            "&CheckListCompletedDateTime="+enddateTime+"&UniqueID="+uniquesId;
                    link1 = link1.replace(" ","%20");
                    HttpPost httppost1 = new HttpPost(link1);

                    ResponseHandler<String> responseHandler1 = new BasicResponseHandler();
                    final String response = httpclient1.execute(httppost1,
                            responseHandler1);

                    Log.i("SharedFeedback", "Response : " + response);
                    hidePB();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(),"Record has been submitted successfully",Toast.LENGTH_LONG).show();
                            //finish();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread_send_data.start();
    }

    void showPB(final String message) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pb = new ProgressDialog(CheckListMaster.this);
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
        getMenuInflater().inflate(R.menu.menu_check_list_master, menu);
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
