package in.mobme.tvticker;

import in.mobme.tvticker.customwidget.WebImageView;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> implements Filterable {

	private Activity context = null;
	private List<String> names = null;
	private int rowLayoutId;

	public MyArrayAdapter(Activity context, int textViewResourceId,
			List<String> list2) {
		super(context, textViewResourceId, list2);
		this.context = context;
		this.names = list2;
		this.rowLayoutId = textViewResourceId;
	}

	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		protected WebImageView imageView;
		protected TextView movieTitle;
		protected TextView channelID;
		protected TextView categoryTag;
		protected RatingBar imdbRatingBar;
		protected TextView show_timing;
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
			holder.movieTitle = (TextView) rowView.findViewById(R.id.label);
			holder.imageView = (WebImageView) rowView
					.findViewById(R.id.left_icon);
			holder.channelID = (TextView) rowView.findViewById(R.id.channel_id);
			holder.categoryTag = (TextView) rowView
					.findViewById(R.id.category_tag);
			holder.imdbRatingBar = (RatingBar) rowView
					.findViewById(R.id.imdb_rating_bar_main);
			holder.show_timing = (TextView) rowView
					.findViewById(R.id.show_time);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.movieTitle.setText(names.get(position));
		holder.imageView
				.setImageWithURL(
						context,
						"http://www.movieposterdb.com/posters/09_11/2007/440963/l_440963_40b30439.jpg",
						R.drawable.ic_placehoder);
		holder.imdbRatingBar.setRating(sanitizeRatingValue(9f));
		return rowView;
	}

	private float sanitizeRatingValue(float floatValue) {
		return (floatValue * 3) / 10;
	}

}
