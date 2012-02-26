package in.mobme.tvticker;

import in.mobme.tvticker.alarm.NotificationObject;
import in.mobme.tvticker.alarm.ShowAlarmService;
import in.mobme.tvticker.customwidget.WebImageView;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import in.mobme.tvticker.helpers.DataLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

	private final int MENU_ADD_TO_FAVORITES = 1;
	private final int MENU_SHARE = 2;

	private WebImageView movieThumb = null;
	private TextView movieDescription = null;
	private TextView movieTimeText = null;
	private TextView movieChannelText = null;
	private Button readReviewsButton = null;
	// private Button imdbRatingButton = null;
	private Button setReminderButton = null;
	private boolean fav_status = false;
	private int cMenuDrawable;

	private Media media = null;
	TvTickerDBAdapter dataAdapter;

	String title;
	String subTitle;
	String imdbURL;
	String channel;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(Constants.TAG, "" + fav_status);
		cMenuDrawable = fav_status ? R.drawable.ic_action_fav_on
				: R.drawable.ic_action_fav_off;
		menu.add(0, MENU_ADD_TO_FAVORITES, 0, "fav").setIcon(cMenuDrawable)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, MENU_SHARE, 1, "share").setIcon(R.drawable.ic_action_share)
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
		fav_status = dataAdapter.IsFavoriteEnabledFor(media.getId());
		channel = dataAdapter.getChannelNameFor(media.getChannel());
		// subTitle = dataAdapter.getCategoryTypeFor(media.getCategoryType());
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
		setReminderButton = (Button) findViewById(R.id.button_set_reminder);

		// // non_interactive fields
		// imdbRatingButton = (Button)
		// findViewById(R.id.rating_non_interactive_button);

		// media object to UI components
		// imdbRatingButton.setText(getFormattedImdbTextRatingFor(media
		// .getImdbRating()));
		movieThumb.setImageWithURL(this, DataLoader.formattedThumbUrl(media
				.getMediaThumb()),
				this.getResources().getDrawable(R.drawable.ic_placehoder));
		movieDescription.setText(media.getMediaDescription());
		movieTimeText.setText(media.getShowTime());
		movieChannelText.setText(channel);

		// button click listeners
		readReviewsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				if (imdbURL == null) {
					Toast.makeText(getBaseContext(), "Reviews not available !", Toast.LENGTH_SHORT).show();
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

		setReminderButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// SharedPreferences preferences =
				// PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				Intent myIntent = new Intent(DetailedDescriptionActivity.this,
						ShowAlarmService.class);

				DateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				try {
					date = (Date) formatter.parse(media.getShowTime());
				} catch (ParseException e) {
					Log.i("Error", "Date format exception");
				}

				Calendar calender = Calendar.getInstance();
				calender.setTimeInMillis(date.getTime());
				calender.add(Calendar.MINUTE, -15);

				HashMap<String, Object> notificationData = new HashMap<String, Object>();

				notificationData.put("channel_name", channel);
				notificationData.put("show_name", media.getMediaTitle());
				notificationData.put("show_time", date);

				NotificationObject notification = new NotificationObject();
				notification.setChannelName(channel);
				notification.setShowName(media.getMediaTitle());
				notification.setShowTime(date);

				String[] data = new String[] { channel, media.getMediaTitle(),
						date.toLocaleString() };

				myIntent.putExtra("notification_data", data);

				PendingIntent pendingIntent = PendingIntent.getService(
						DetailedDescriptionActivity.this, 0, myIntent, 0);
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

				alarmManager.set(AlarmManager.RTC_WAKEUP,
						calender.getTimeInMillis(), pendingIntent);

				Toast.makeText(DetailedDescriptionActivity.this,
						"Reminder is set", Toast.LENGTH_SHORT).show();

			}
		});

	}

	// Action bar menu item click listener.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD_TO_FAVORITES:
			// take curStatus from db, this is trivial !
			dataAdapter.open();
			fav_status = !dataAdapter.IsFavoriteEnabledFor(media.getId());
			if (fav_status) {
				cMenuDrawable = R.drawable.ic_action_fav_on;
				dataAdapter.setIsFavorite(media.getId(), fav_status);
			} else {
				cMenuDrawable = R.drawable.ic_action_fav_off;
				dataAdapter.removeIsFavFor(media.getId());
			}
			dataAdapter.close();
			item.setIcon(cMenuDrawable);
			// not recommended !
			ViewPagerAdapter.staticAdapterObj.refreshFavAdapter(media);
			break;
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
		ratingString.append("Imdb Rating: ").append(
				(rating == 0) ? "not available" : rating + "/10");
		return ratingString.toString();
	}

}
