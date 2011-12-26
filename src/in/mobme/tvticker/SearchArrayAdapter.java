package in.mobme.tvticker;

import in.mobme.tvticker.data_model.SearchableMedia;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchArrayAdapter extends ArrayAdapter<SearchableMedia> {
	private List<SearchableMedia> mediaList = null;
	private int rowLayoutId;
	private Activity context;

	public SearchArrayAdapter(Activity context, int textViewResourceId,
			List<SearchableMedia> resultList) {
		super(context, textViewResourceId, resultList);
		this.context = context;
		this.mediaList = resultList;
		this.rowLayoutId = textViewResourceId;
	}

	static class ViewHolder {
		protected TextView title;
		protected TextView subTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(rowLayoutId, null, true);
			holder = new ViewHolder();
			holder.title = (TextView) rowView
					.findViewById(R.id.search_media_title);
			holder.subTitle = (TextView) rowView
					.findViewById(R.id.search_media_sub);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		
		SearchableMedia media = mediaList.get(position);
		holder.title.setText(media.getMedia_title());
		holder.subTitle.setText(resolveMediaTypeFor(media.getType()));
		return rowView;
	}
	
	private String resolveMediaTypeFor(int type){
		return (type == SearchableMedia.TYPE_MOVIE) ? "Movie" : "Channel"; 
	}

}
