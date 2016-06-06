/**
 * 
 */
package com.example.asif.cuny.CheckBoxAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asif.cuny.CustomFonts.CustomTextView;
import com.example.asif.cuny.R;

import java.util.List;


/**
 * Adapter that allows us to render a list of items
 * 
 * @author marvinlabs
 */
public class ItemListAdapter extends ArrayAdapter<Item> {

	private int pos;

	/**
	 * Constructor from a list of items
	 */
	public ItemListAdapter(Context context, List<Item> items) {
		super(context, 0, items);
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// The item we want to get the view for
		// --
		final Item item = getItem(position);


		// Re-use the view if possible
		// --
		ViewHolder holder;
		if (convertView == null) {
			convertView = li.inflate(R.layout.emicourse_single_row, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(R.id.holder, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.holder);
		}

		// Set some view properties
		holder.title.setText(item.getTitle());
		holder.dates.setText(item.getDate());
		holder.schedule.setText(item.getSchedule());
		holder.loc.setText(item.getLoc());
		// Restore the checked state properly
		final ListView lv = (ListView) parent;
		holder.layout.setChecked(lv.isItemChecked(position));

		return convertView;
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	public int getPos() {
		return pos;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	private LayoutInflater li;

	private static class ViewHolder {
		public ViewHolder(View root) {
			title = (CustomTextView) root.findViewById(R.id.title_emi);
			dates = (CustomTextView) root.findViewById(R.id.dates_emi);
			schedule = (CustomTextView) root.findViewById(R.id.shedule_emi);
			loc = (CustomTextView) root.findViewById(R.id.location_emi);
			layout = (CheckableRelativeLayout) root.findViewById(R.id.layout);
		}

		public CustomTextView title;
		public CustomTextView dates;
		public CustomTextView schedule;
		public CustomTextView loc;
		public CheckableRelativeLayout layout;
	}
}
