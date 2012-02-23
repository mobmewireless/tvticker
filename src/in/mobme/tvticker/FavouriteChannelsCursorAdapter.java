package in.mobme.tvticker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import in.mobme.tvticker.MyArrayAdapter.ViewHolder;
import in.mobme.tvticker.customwidget.WebImageView;
import in.mobme.tvticker.database.Models;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.RatingBar;
import android.widget.TextView;

public class FavouriteChannelsCursorAdapter extends CursorAdapter {
	private HashMap hm= new HashMap<Integer, Object>();
	Context thisContext = null;

	public FavouriteChannelsCursorAdapter(Context context, Cursor c,
			boolean autoRequery,HashMap<Integer, Object>hash) {

		super(context, c, autoRequery);
		this.hm = hash;
		thisContext = context;
	}

	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		protected CheckedTextView checked_text_view;
	}

	@Override
	public CheckedTextView newView(Context context, Cursor cursor, ViewGroup parent) {

		Log.i("TAG", "on new view");

		// LayoutInflater inflator = (LayoutInflater) context
		// .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		// View rowView = inflator
		// .inflate(R.layout.favourite_channels, null, true);
		//
		// return rowView;
		LayoutInflater inflater = LayoutInflater.from(context);
		CheckedTextView v = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_checked,
				parent, false);
		String name = cursor.getString(1);
		int Id = Integer.parseInt(cursor.getString(0));
		Log.i("hashmap",""+hm);
		Log.i("key",""+Id);
		int currentStatus = (Integer) hm.get(Id);
		v.setText(name);
		v.setTag(Id);
		v.setChecked((currentStatus==1));
		
		return v;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		// CheckedTextView checked_text_view = (CheckedTextView) view
		// .findViewById(R.id.cv_id);
		// checked_text_view.setText(cursor.getString(1));
		CheckedTextView textview = (CheckedTextView) view;
		String name = cursor.getString(1);
		int Id = Integer.parseInt(cursor.getString(0));
		int currentStatus = (Integer) hm.get(Id);
		textview.setText(name);
		textview.setTag(Id);
		textview.setChecked((currentStatus==1));
		
		
		
       
		
		
//		if (cursor.getString(2).equals("1"))
//			{
//			textview.setChecked(true);
//			Log.i("TAG", ""+textview.isChecked());
//			}
//		else
//			textview.setChecked(false);
		
	}

}
