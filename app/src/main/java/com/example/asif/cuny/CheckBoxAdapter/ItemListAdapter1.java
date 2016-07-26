package com.example.asif.cuny.CheckBoxAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asif.cuny.CustomFonts.CustomTextView;
import com.example.asif.cuny.DataBase.BAL;
import com.example.asif.cuny.R;
import com.example.asif.cuny.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fazal on 27-Mar-16.
 */
public class ItemListAdapter1 extends ArrayAdapter<String> {

        /**
         * Constructor from a list of items
         */
        Utils utils = new Utils(getContext());
        BAL bal = new BAL(getContext());
        public ItemListAdapter1(Context context, List<String> items) {
            super(context, 0, items);
            li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // The item we want to get the view for
            // --
            final String item = getItem(position);


            // Re-use the view if possible
            // --
            ViewHolder holder;
            if (convertView == null) {
                convertView = li.inflate(R.layout.installed_equip_single_row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(R.id.holder, holder);
            } else {
                holder = (ViewHolder) convertView.getTag(R.id.holder);
            }

            // Set some view properties
            holder.title.setText(item);

            // Restore the checked state properly
            final ListView lv = (ListView) parent;



            if(!utils.getdata("IsCheckList").equals("")){

                   List<Integer> list = bal.getPos(utils.getdata("id1"));
                   if (position < list.size()) {
                       int pos = list.get(position);
                       lv.setItemChecked(pos, true);
                   }

            }
            else if(utils.getdata("status_").equals("")) {

                List<Integer> model = bal.getPosition(utils.getdata("build_name1"));
                //int count = utils.getdata("count");
                if(position<model.size()) {
                    int pos = model.get(position);
                    lv.setItemChecked(pos, true);
                }
            }
            /*if(utils.getdata("status_").equals("")) {
                if (utils.getdata("pos" + position).equals(String.valueOf(position))) {
                    lv.setItemChecked(position, true);
                }
            }*/


            holder.layout.setChecked(lv.isItemChecked(position));
            SharedPreferences pref = getContext().getSharedPreferences("pref",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();


            return convertView;
        }



        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
        if(utils.preferences.getBoolean("isFinished",false)==true){
            return true;
        }
        return false;
    }

    private LayoutInflater li;

        private static class ViewHolder {
            public ViewHolder(View root) {
                title = (CustomTextView) root.findViewById(R.id.list_text);
                layout = (CheckableRelativeLayout) root.findViewById(R.id.layout);
            }

            public CustomTextView title;
            public CheckableRelativeLayout layout;
        }
    }
