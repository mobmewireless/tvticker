package in.mobme.tvticker;

import in.mobme.tvticker.customwidget.WebImageView;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import in.mobme.tvticker.helpers.DataLoader;
import in.mobme.tvticker.notification.NotificationFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedDescriptionActivity extends FragmentActivity {

	private final int MENU_SHARE = 2;

	private WebImageView movieThumb = null;
	private TextView movieDescription = null;
	private TextView movieTimeText = null;
	private TextView movieChannelText = null;
	private Button readReviewsButton = null;
	// private Button imdbRatingButton = null;
	private Button setFavouriteButton = null;
	private boolean isFavourited = false;
	
	private Media media = null;
	TvTickerDBAdapter dataAdapter;

	String title;
	String subTitle;
	String imdbURL;
	String channel;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//menu.add(0, MENU_ADD_TO_FAVORITES, 0, "fav")
		//		.setIcon(getFavoriteDrawable(fav_status))
		//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, MENU_SHARE, 0, "share").setIcon(R.drawable.ic_action_share)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		media = (Media) this.getIntent().getExtras()
				.getSerializable(Constants.MEDIA_OBJECT);
		dataAdapter = new TvTickerDBAdapter(this);
		dataAdapter.open();
		isFavourited = dataAdapter.IsFavoriteEnabledFor(media.getId());

			
		channel = dataAdapter.getChannelNameFor(media.getChannel());
		dataAdapter.close();
		setContentView(R.layout.detailed_description);

		title = media.getMediaTitle();
		subTitle = getFormattedImdbTextRatingFor(media.getImdbRating());
		imdbURL = media.getImdbLink();

		// configure action bar - private method
		configureActionbarWith(getSupportActionBar(), title, subTitle);

		movieThumb = (WebImageView) findViewById(R.id.movie_thumb);
		movieDescription = (TextView) findViewById(R.id.movie_description);
		movieTimeText = (TextView) findViewById(R.id.textViewTime);
		movieChannelText = (TextView) findViewById(R.id.textViewChannel);

		readReviewsButton = (Button) findViewById(R.id.button_go_imdb);
		setFavouriteButton = (Button) findViewById(R.id.button_set_favourite);
		
		updateFavouritesButton();

		movieThumb.setImageWithURL(this, DataLoader.formattedThumbUrl(media
				.getMediaThumb(), DataLoader.TYPE_LARGE),
				this.getResources().getDrawable(R.drawable.ic_placehoder));
		movieDescription.setText(media.getMediaDescription());
		movieTimeText.setText(media.getShowTime());
		movieChannelText.setText(channel);

		// button click listeners
		readReviewsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (imdbURL == null) {
					Toast.makeText(getBaseContext(), "Reviews not available !",
							Toast.LENGTH_SHORT).show();
				} else {
					Log.i("Loading Browser", imdbURL);
					Uri uriUrl = Uri.parse(getString(R.string.imdb_review_url,
							new Object[] { imdbURL }));
					Intent launchBrowser = new Intent(Intent.ACTION_VIEW,
							uriUrl);
					startActivity(launchBrowser);
				}
			}
		});

		setFavouriteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dataAdapter.open();				
				if (!isFavourited) {
					// Adding to favourites
					dataAdapter.setIsFavorite(media.getId(), true);
					isFavourited = true;
					
					// Adding a reminder.
					DateFormat formatter = new SimpleDateFormat(Constants.ALARM_INTENT_DATE_FORMAT);
					Date date = new Date(); 
					try {
						date = (Date) formatter.parse(media.getShowTime());
					} catch (ParseException e) {
						Log.i("Error", "Date format exception");
					}
					
					String[] data = new String[] { channel, media.getMediaTitle(), formatter.format(date) };
									
					NotificationFactory.createNotification(DetailedDescriptionActivity.this, date, data, media.getId());
				} else {
					// Remove from favourites
					dataAdapter.setIsFavorite(media.getId(), false);
					isFavourited = false;
				}
				
				ViewPagerAdapter.staticAdapterObj.refreshFavAdapter(media);
				updateFavouritesButton();
				dataAdapter.close();
			}
		});
	}

	private void updateFavouritesButton() {
		if (isFavourited) {
			setFavouriteButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_action_fav_on), null, null, null);
			setFavouriteButton.setText(R.string.remove_favourites);
		} else {
			setFavouriteButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_action_fav_off), null, null, null);
			setFavouriteButton.setText(R.string.add_to_favourites);
		}		
	}
	
	// Action bar menu item click listener.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_SHARE:
				share("TvTicker", "I'm watching " + title + " on " + channel);
		}
		return super.onOptionsItemSelected(item);
	}

	private void configureActionbarWith(ActionBar actionBar, String title,
			String subTitle) {
		actionBar.setTitle(title);
		actionBar.setSubtitle(subTitle);
	}

	public void share(String subject, String text) {
		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(intent, getString(R.string.share)));
	}

	// helpers for Imdb Rating.
	private String getFormattedImdbTextRatingFor(float rating) {
		StringBuilder ratingString = new StringBuilder();
		
		if(rating != 0) {
			ratingString.append("Imdb Rating: ").append(rating + "/10");
		}
		return ratingString.toString();
	}

}
