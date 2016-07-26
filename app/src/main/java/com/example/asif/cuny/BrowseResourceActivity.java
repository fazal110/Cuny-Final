package com.example.asif.cuny;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.asif.cuny.Constants.ApplicationConstant;
import com.example.asif.cuny.CustomFonts.CustomTextView;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.DataBase.DBConnect;
import com.example.asif.cuny.DataBase.DataBaseHelper;
import com.example.asif.cuny.DataBase.DataDB;
import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BrowseResourceActivity extends ActionBarActivity {

    private BAL bal;
    private DBConnect db;
    private ArrayList<String> mArrayList = new ArrayList<>();
    private ListView lv;
    EditText filter;
    private ProgressDialog pb;
    private DataDB been1;
    private Utils utils;
    Intent intent;
    private ImageView filterbtn;
    private ListCustomAdapter adapter;
    private ArrayList<Integer> pos_array;
    private ArrayList<String> filterList  = new ArrayList<String>();
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_resource);
        init();
        setActionBar1();
        //firstTimefunc();
        getdata_title();
        ShowActivityList();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void init() {
        filter = (EditText) findViewById(R.id.filteret);
        db = new DBConnect(BrowseResourceActivity.this);
        lv = (ListView) findViewById(R.id.list_browse_resource);
        bal = new BAL(BrowseResourceActivity.this);
        filterbtn = (ImageView) findViewById(R.id.filterbtn);
        lv.setTextFilterEnabled(true);
        utils = new Utils(BrowseResourceActivity.this);
        intent = getIntent();


    }

    public void FilterClick(View view){
        pos_array = new ArrayList<Integer>();
        String charseq = filter.getText().toString();
        for(int i=0;i<mArrayList.size();i++) {
            if(charseq.equals("")) {
                lv.setVisibility(View.VISIBLE);
                ((CustomTextView)findViewById(R.id.alertshow)).setVisibility(View.GONE);
                filterList.clear();
                adapter = new ListCustomAdapter(BrowseResourceActivity.this,R.layout.activity_browse_resource,mArrayList);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return;
            }
            if (mArrayList.get(i).contains(charseq)) {
                ((CustomTextView)findViewById(R.id.alertshow)).setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
                filterList.add(mArrayList.get(i));
                pos_array.add(i);
            }

            if (!mArrayList.get(i).contains(charseq) && filterList.size()==0) {
                lv.setVisibility(View.GONE);
                ((CustomTextView)findViewById(R.id.alertshow)).setVisibility(View.VISIBLE);
            }

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }

        adapter = new ListCustomAdapter(BrowseResourceActivity.this,R.layout.activity_browse_resource,filterList);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }






    public void setActionBar1(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b3f95")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customview = getLayoutInflater().inflate(R.layout.browseresourceactivity_actionbar, null);
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


    public void firstTimefunc(){
        SharedPreferences settings = getSharedPreferences("pref", 0);
        boolean firstStart = settings.getBoolean("firstStart", true);

        if(firstStart) {
            getdata_title();

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstStart", false);
            editor.commit();
        }
    }

    public void ShowActivityList(){
        try{
            //DataDB data_db = new DataDB();
            final int classifid = intent.getIntExtra("classifid",0);
            final int equipid = intent.getIntExtra("catgid",0);
            final String id = intent.getStringExtra("id");
            final String status = intent.getStringExtra("status");
            final String title = intent.getStringExtra("keyword");
            final String equip = intent.getStringExtra("equip");
            final String activity = intent.getStringExtra("activity");
            final String restype = intent.getStringExtra("restype");
            utils.savedata("classifid", String.valueOf(classifid));
            if(id!=null){
                if (id.equals("equipment")) {
                    mArrayList = bal.getAllContentTitleboiler(equipid);
                }
            }
            else if(title!=null && restype!=null){
                int id1 = intent.getIntExtra("id", 0);
                ArrayList<DataDB> alldata = bal.getAllContentData(title, String.valueOf(id1));
                mArrayList = new ArrayList<>();
                for(DataDB data : alldata) {
                    mArrayList.add(data.getTitle());
                }
            }
            else if(equip!=null && restype!=null){
                int id1 = intent.getIntExtra("id", 0);
                int res_id = intent.getIntExtra("id_res", 0);
                ArrayList<DataDB> alldata = bal.getAllContentData1(String.valueOf(id1), String.valueOf(res_id));
                mArrayList = new ArrayList<>();
                for(DataDB data : alldata) {
                    mArrayList.add(data.getTitle());
                }
            }
            else if(activity!=null && restype!=null){
                int id1 = intent.getIntExtra("id", 0);
                int res_id = intent.getIntExtra("id_res", 0);
                ArrayList<DataDB> alldata = bal.getAllContentData(id1, String.valueOf(res_id));
                mArrayList = new ArrayList<>();
                for(DataDB data : alldata) {
                    mArrayList.add(data.getTitle());
                }
            }
            else if(title!=null && restype==null){
                mArrayList = new ArrayList<>();
                keyword = intent.getStringExtra("keyword");
                ArrayList<DataDB> list = bal.getAllContentData(keyword);
                for(int i=0;i<list.size();i++){
                    lv.setVisibility(View.VISIBLE);
                    ((CustomTextView)findViewById(R.id.alertshow)).setVisibility(View.GONE);
                    mArrayList.add(list.get(i).getTitle());
                }
                if(mArrayList.size()==0){
                    lv.setVisibility(View.GONE);
                    ((CustomTextView)findViewById(R.id.alertshow)).setVisibility(View.VISIBLE);
                }

            }else if(equip!=null && restype==null) {
                int id1 = intent.getIntExtra("id", 0);
                ArrayList<DataDB> alldata = bal.getAllContentData1(String.valueOf(id1));
                for(int i=0;i<alldata.size();i++){
                    mArrayList.add(alldata.get(i).getTitle());
                }
            }
            else if(activity!=null){
                int id1 = intent.getIntExtra("id", 0);
                ArrayList<DataDB> alldata = bal.getAllContentData(id1);
                for(int i=0;i<alldata.size();i++){
                    mArrayList.add(alldata.get(i).getTitle());
                }
            }
            else if(restype!=null){
                int id1 = intent.getIntExtra("id", 0);
                mArrayList = bal.getAllContentDataRes(String.valueOf(id1),true);

            }else if(intent.getStringExtra("InstalledIDs")!=null){
                try{
                    DataBaseHelper databasehelper = new DataBaseHelper(BrowseResourceActivity.this);
                    databasehelper.openDataBase();
                    mArrayList = databasehelper.getBuildingdata(intent.getStringExtra("InstalledIDs"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            else {
                mArrayList = bal.getAllContentTitle(classifid);
            }



            adapter = new ListCustomAdapter(BrowseResourceActivity.this,R.layout.activity_browse_resource,mArrayList);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(id!=null) {
                        if (id.equals("equipment")) {
                            if(filterList.size()>0){
                                ArrayList<DataDB> alldata = bal.getAllContentDataBoiler(equipid);
                                setdata(pos_array.get(i),alldata);
                            }else {
                                ArrayList<DataDB> alldata = bal.getAllContentDataBoiler(equipid);
                                setdata(i, alldata);
                            }
                        }
                    }else if(status!=null){
                        if(title!=null && restype==null) {
                            ArrayList<DataDB> alldata = bal.getAllContentData(title);
                            setdata(i, alldata);
                        }else if(equip!=null && restype==null){
                            int id = intent.getIntExtra("id",0);
                            ArrayList<DataDB> alldata = bal.getAllContentData1(String.valueOf(id));
                            setdata(i, alldata);
                        }
                        else if(activity!=null && restype==null){
                            int id = intent.getIntExtra("id",0);
                            ArrayList<DataDB> alldata = bal.getAllContentData(id);
                            setdata(i, alldata);
                        }
                        else if(restype!=null && title==null && equip==null && activity==null){
                            int id = intent.getIntExtra("id", 0);
                            ArrayList<DataDB> alldata = bal.getAllContentDataResDB(String.valueOf(id));
                            setdata(i, alldata);

                        }
                        else if(title!=null && restype!=null){
                            int id = intent.getIntExtra("id", 0);
                            ArrayList<DataDB> alldata = bal.getAllContentData(title, String.valueOf(id));
                            setdata(i, alldata);

                        }
                        else if(equip!=null && restype!=null){
                            int id1 = intent.getIntExtra("id", 0);
                            int res_id = intent.getIntExtra("id_res",0);
                            ArrayList<DataDB> alldata = bal.getAllContentData1(String.valueOf(id1),String.valueOf(res_id));
                            setdata(i,alldata);
                        }
                        else if(activity!=null && restype!=null){
                            int id1 = intent.getIntExtra("id", 0);
                            int res_id = intent.getIntExtra("id_res",0);
                            ArrayList<DataDB> alldata = bal.getAllContentData(id1,String.valueOf(res_id));
                            setdata(i,alldata);
                        }
                    }
                    else {

                        if(filterList.size()>0){
                            ArrayList<DataDB> alldata = bal.getAllContentData(classifid);
                            setdata(pos_array.get(i),alldata);
                        }else {
                            if(intent.getStringExtra("InstalledIDs")!=null){
                                try {
                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                                    dataBaseHelper.openDataBase();
                                    //int id = dataBaseHelper.getBuildingPos(mArrayList.get(i));
                                    //String clasifid = dataBaseHelper.getBuildingClassification(mArrayList.get(i));
                                    ArrayList<DataDB> alldata = bal.getAllContentData(mArrayList.get(i));
                                    int id = 0;
                                    setdata(id, alldata);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }else {
                                ArrayList<DataDB> alldata = bal.getAllContentData(classifid);
                                setdata(i, alldata);
                            }
                        }
                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setdata(int i,ArrayList<DataDB> alldate){
        switch (alldate.get(i).getResType()) {
            case "1": {
                String url = alldate.get(i).getContentLocation();
                Intent intent = new Intent(BrowseResourceActivity.this, PDFActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                break;
            }
            case "2": {
                String url = alldate.get(i).getContentLocation();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setDataAndType(Uri.parse(url), "video/*");
                startActivityForResult(intent, ApplicationConstant.video_constant);
                break;
            }
            case "3": {
                String data = alldate.get(i).getContentLocation();
                String title = alldate.get(i).getTitle();
                Intent intent = new Intent(BrowseResourceActivity.this, PostActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("post", data);
                startActivity(intent);
                break;
            }
            case "4": {

                break;
            }
            case "5": {
                String url = alldate.get(i).getContentLocation();
                Intent intent = new Intent(BrowseResourceActivity.this, ImageActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                break;
            }
        }
    }



    public void getdata_title(){
        final ArrayList<String> listTitle = new ArrayList<String>();
        final Thread thread_getData = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient  httpclient = new DefaultHttpClient();
                    showPB("Loading....");
                    String link = "http://freecs13.hostei.com/celeritas-solutions/convertTojson.php";


                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);


                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray Jarray = jsonObject.getJSONArray("ContentTitle");
                    JSONArray JarraycontentDes = jsonObject.getJSONArray("ContentDescription");
                    JSONArray Jarrayrestype = jsonObject.getJSONArray("ResourceType");
                    JSONArray Jarraycontsize = jsonObject.getJSONArray("ContentSize");
                    JSONArray JarrayequipmentCategory = jsonObject.getJSONArray("EquipmentCategory");
                    JSONArray Jarrayclassif = jsonObject.getJSONArray("Classification");
                    JSONArray Jarrayloc = jsonObject.getJSONArray("ContentLocation");
                    JSONArray JarrayupdateDatetime = jsonObject.getJSONArray("ContentUploadedDateTime");
                    JSONArray Jarrayuserid = jsonObject.getJSONArray("ContentUploadedByUserID");
                    JSONArray JarraylastupdatedDate = jsonObject.getJSONArray("LastUpdateDateTime");


                    been1 = new DataDB();
                    for(int i=0;i<Jarray.length();i++){

                        String title = (String) Jarray.get(i);
                        String contDes = JarraycontentDes.getString(i);
                        String restype = Jarrayrestype.getString(i);
                        String conSize = Jarraycontsize.getString(i);
                        String equip_Cat = JarrayequipmentCategory.getString(i);
                        String classif = Jarrayclassif.getString(i);
                        String location = Jarrayloc.getString(i);
                        String date = JarrayupdateDatetime.getString(i);
                        String userId = Jarrayuserid.getString(i);
                        String lastupdateddate = JarraylastupdatedDate.getString(i);
                        been1.setTitle(title);
                        been1.setDescription(contDes);
                        been1.setResType(restype);
                        been1.setSize(conSize);
                        been1.setEquipcategory(equip_Cat);
                        been1.setClassif(classif);
                        been1.setContentLocation(location);
                        been1.setUploadDatetime(date);
                        been1.setUserId(userId);
                        been1.setLastUpdateDateTime(lastupdateddate);

                        bal.InsertDatainContentMaster(been1);

                    }
                    hidePB();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int classifid = intent.getIntExtra("classifid",0);
                            mArrayList = bal.getAllContentTitle(classifid);
                            adapter = new ListCustomAdapter(BrowseResourceActivity.this,R.layout.activity_browse_resource,mArrayList);
                            lv.setAdapter(adapter);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_getData.start();
    }



    void showPB(final String message) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pb = new ProgressDialog(BrowseResourceActivity.this);
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

    public class ListCustomAdapter extends ArrayAdapter<String> {
        List<String> value;
        private TextView list_text;
        private ImageView image_icon;
        private ArrayList<String> listResType = new ArrayList<>();
        int i=0;
        private ArrayList<String> worldpopulationlist;


        public ListCustomAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            value = objects;
            worldpopulationlist = new ArrayList<String>();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            try{
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.list_browseres_single_row,null,false);
                    list_text = (TextView) convertView.findViewById(R.id.text_list_item);
                    list_text.setText(value.get(position));
                String id1 = intent.getStringExtra("id");
                String title = intent.getStringExtra("keyword");
                String equip = intent.getStringExtra("equip");
                String activity = intent.getStringExtra("activity");
                String restype = intent.getStringExtra("restype");
                image_icon = (ImageView) convertView.findViewById(R.id.iconimg);
                if(id1!=null) {
                    if(id1.equals("equipment")) {
                        int id = intent.getIntExtra("catgid", 0);
                        listResType = bal.getAllResTypeboiler(id);

                    }
                }

                else if(title!=null && restype==null){

                        String keyword = intent.getStringExtra("keyword");
                        listResType = bal.getAllResType(keyword);

                }else if(equip!=null && restype==null){
                    int equipid = intent.getIntExtra("id", 0);
                    listResType = bal.getAllResTypeboiler(equipid);
                }else if(activity!=null && restype==null){
                    int activityid = intent.getIntExtra("id", 0);
                    listResType = bal.getAllResType(activityid);
                }
                else if(restype!=null && title==null){
                    int id = intent.getIntExtra("id", 0);
                    listResType = bal.getAllContentDataRes(String.valueOf(id),false);

                }

                else if(title!=null && restype!=null){
                    int id = intent.getIntExtra("id", 0);
                    listResType = new ArrayList<>();
                    ArrayList<DataDB> alldata = bal.getAllContentData(title, String.valueOf(id));
                    for(DataDB data : alldata){
                        listResType.add(data.getResType());
                    }

                }
                else if(equip!=null && restype!=null){
                    int id2 = intent.getIntExtra("id", 0);
                    int res_id = intent.getIntExtra("id_res",0);
                    ArrayList<DataDB> alldata = bal.getAllContentData1(String.valueOf(id2),String.valueOf(res_id));
                    mArrayList = new ArrayList<>();
                    for(DataDB data : alldata) {
                        listResType.add(data.getResType());
                    }
                }
                else if(activity!=null && restype!=null){
                    int id2 = intent.getIntExtra("id", 0);
                    int res_id = intent.getIntExtra("id_res",0);
                    ArrayList<DataDB> alldata = bal.getAllContentData(id2,String.valueOf(res_id));
                    mArrayList = new ArrayList<>();
                    for(DataDB data : alldata) {
                        listResType.add(data.getResType());
                    }
                }
                else {
                    int id = intent.getIntExtra("classifid", 0);
                    listResType = bal.getAllResType(id);

                }
                if(filterList.size()>0){
                    int pos = pos_array.get(position);
                    getdata(listResType,pos , image_icon);
                }else
                getdata(listResType, position, image_icon);

            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }


    }

    public void getdata(ArrayList<String> listResType,int position,ImageView image_icon){

        switch (listResType.get(position)){
            case "1":{
                image_icon.setImageResource(R.drawable.pdf);
                break;
            }
            case "2":{
                image_icon.setImageResource(R.drawable.video);
                break;
            }
            case "3":{
                image_icon.setImageResource(R.drawable.post);
                break;
            }
            case "4":{
                image_icon.setImageResource(R.drawable.url);
                break;
            }
            case "5":{
                image_icon.setImageResource(R.drawable.image);
                break;
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_resource, menu);
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
