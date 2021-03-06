package in.mobme.tvticker.adapter;

import in.mobme.tvticker.R;
import in.mobme.tvticker.customwidget.WebImageView;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import in.mobme.tvticker.helpers.DataLoader;
import in.mobme.tvticker.helpers.DateTimeHelper;
import in.mobme.tvticker.helpers.TVMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<Media> {

	private Activity context = null;
	private List<Media> mediaList = null;
	private int rowLayoutId;
	private Drawable placeHolder = null;
	private boolean showThumb = true;

	public MyArrayAdapter(Activity context, int textViewResourceId,
			List<Media> list2, boolean displayThumb) {
		super(context, textViewResourceId, list2);
		this.context = context;
		this.mediaList = list2;
		this.rowLayoutId = textViewResourceId;
		placeHolder = context.getResources().getDrawable(
				R.drawable.ic_placehoder);
		this.showThumb = displayThumb;
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
		TvTickerDBAdapter dataAdapter;
		String category;
		String channel;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		dataAdapter = new TvTickerDBAdapter(context);
		dataAdapter.open();
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(rowLayoutId, null, true);

			holder = new ViewHolder();
			holder.movieTitle = (TextView) rowView.findViewById(R.id.label);
			if (showThumb) {
				holder.imageView = (WebImageView) rowView
						.findViewById(R.id.left_icon);
				holder.imageView.setVisibility(View.VISIBLE);
			}
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

		Media media = mediaList.get(position);

		holder.movieTitle.setText(media.getMediaTitle());
		if (showThumb) {
			holder.imageView.setImageWithURL(context, DataLoader.formattedThumbUrl(media.getMediaThumb(), DataLoader.TYPE_LARGE),
					placeHolder);
		}
		channel = dataAdapter.getChannelNameFor(media.getChannel());
		category = dataAdapter.getCategoryTypeFor(media.getCategoryType());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date show_time_start = format.parse(media.getShowTime());
			Date show_time_end = format.parse(media.getShowEndTime());
			DateTimeHelper dateHelper = new DateTimeHelper();
			TVMessage messageAndFormat = dateHelper.getFormattedMessage(
					show_time_start, show_time_end);
			holder.show_timing.setTypeface(null, messageAndFormat.getStyle());
			holder.show_timing.setText(messageAndFormat.getMessage());
			//holder.show_timing.setBackgroundResource(messageAndFormat.getColor());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			holder.show_timing.setText(media.getShowTime());
			e.printStackTrace();
		}

		dataAdapter.close();
		holder.channelID.setText(channel);
		holder.categoryTag.setText(category);
		
		// Set the rating only if available
		float rating = media.getImdbRating();
		if (rating == 0) {
			holder.imdbRatingBar.setVisibility(View.INVISIBLE);
		} else {
			holder.imdbRatingBar.setVisibility(View.VISIBLE);
			holder.imdbRatingBar.setRating(sanitizeRatingValue(rating));
		}
		return rowView;
	}


	private float sanitizeRatingValue(float floatValue) {
		return (floatValue * 3) / 10;
	}


	@Override
	public void addAll(Collection<? extends Media> collection) {
		mediaList.addAll(collection);
	}

}
