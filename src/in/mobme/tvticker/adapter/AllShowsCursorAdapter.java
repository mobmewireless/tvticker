package in.mobme.tvticker.adapter;

import in.mobme.tvticker.R;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AllShowsCursorAdapter extends CursorAdapter {

	private Activity activity;
	
	public AllShowsCursorAdapter(Context context, Cursor c, Activity activity) {
		super(context, c);
		this.activity = activity;
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View rowView = activity.getLayoutInflater().inflate(R.layout.channel_row, null, true);
		
		return rowView;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		populateViewItem((TextView) view, cursor); 
	}
	
	private void populateViewItem(TextView textView, Cursor cursor) {
		textView.setText(cursor.getString(1));
		textView.setTag(cursor.getString(0)); //tag stores the channel id
	}

}
