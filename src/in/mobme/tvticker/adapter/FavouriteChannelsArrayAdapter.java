package in.mobme.tvticker.adapter;

import in.mobme.tvticker.R;
import in.mobme.tvticker.data_model.FavouriteChannelModel;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

public class FavouriteChannelsArrayAdapter extends ArrayAdapter<FavouriteChannelModel> {

	Context context = null;
	List<FavouriteChannelModel> list = null;
	int rowViewResourceId;
	
	public FavouriteChannelsArrayAdapter(Context context,
			int textViewResourceId, List<FavouriteChannelModel> list) {
		super(context, textViewResourceId, list);
		this.context = context;
		this.list = list;
		this.rowViewResourceId = textViewResourceId;
	}

	static class ViewHolder {
		protected CheckedTextView checkedText;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(context);
			view = inflator.inflate(rowViewResourceId, null);
			final ViewHolder viewHolder = new ViewHolder();

			viewHolder.checkedText = (CheckedTextView) view.findViewById(R.id.cv_id);
			
			viewHolder.checkedText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					toggle(v);
					FavouriteChannelModel element = (FavouriteChannelModel) viewHolder.checkedText.getTag();
					Log.i("DEbug", "cur_status" + viewHolder.checkedText.isChecked());
					element.setSelected(viewHolder.checkedText.isChecked());
					Log.i("DEbug", "clicked me !");
					
				}
			});
			view.setTag(viewHolder);
			viewHolder.checkedText.setTag(list.get(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).checkedText.setTag(list.get(position));
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.checkedText.setText(list.get(position).getName());
		holder.checkedText.setChecked(list.get(position).isSelected());
		return view;
	}
	
	public void toggle(View v) {
		CheckedTextView cView = (CheckedTextView) v.findViewById(R.id.cv_id);
		Log.i("DEbug", "toggle !");
		cView.toggle();
	}
	
}