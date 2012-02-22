package in.mobme.tvticker;

import in.mobme.tvticker.MyArrayAdapter.ViewHolder;
import in.mobme.tvticker.customwidget.WebImageView;
import android.app.Activity;
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

	Context thisContext = null;

	public FavouriteChannelsCursorAdapter(Context context, Cursor c,
			boolean autoRequery) {

		super(context, c, autoRequery);
		thisContext = context;
	}

	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		protected CheckedTextView checked_text_view;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		Log.i("TAG", "on new view");

		// LayoutInflater inflator = (LayoutInflater) context
		// .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		// View rowView = inflator
		// .inflate(R.layout.favourite_channels, null, true);
		//
		// return rowView;
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(android.R.layout.simple_list_item_checked,
				parent, false);
		return v;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		// CheckedTextView checked_text_view = (CheckedTextView) view
		// .findViewById(R.id.cv_id);
		// checked_text_view.setText(cursor.getString(1));
		CheckedTextView textview = (CheckedTextView) view;
		textview.setText(cursor.getString(1));
		textview.setTag(cursor.getString(0));
//		if (cursor.getString(2).equals("1"))
//			{
//			textview.setChecked(true);
//			Log.i("TAG", ""+textview.isChecked());
//			}
//		else
//			textview.setChecked(false);
		
	}

}
