package in.mobme.tvticker;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String>{

	private Activity context = null;
	private String[] names = null;
	private int rowLayoutId;
	public MyArrayAdapter(Activity context, int textViewResourceId, String[] names) {
		super(context, textViewResourceId, names);
		this.context = context;
		this.names = names;
		this.rowLayoutId =textViewResourceId;
	}

	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		protected ImageView imageView;
		protected TextView textView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		// ViewHolder will buffer the assess to the individual fields of the row
		// layout
		ViewHolder holder;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(rowLayoutId, null, true);
			holder = new ViewHolder();
			holder.textView = (TextView) rowView.findViewById(R.id.label);
			holder.imageView = (ImageView) rowView.findViewById(R.id.left_icon);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.textView.setText(names[position]);
		
		return rowView;
	}

}
