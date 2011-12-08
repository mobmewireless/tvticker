package in.mobme.tvticker;

import in.mobme.tvticker.customwidget.WebImageView;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedDescriptionActivity extends FragmentActivity {

	private final int MENU_ADD_TO_FAVORITES = 1;
	private WebImageView movieThumb = null;
	private TextView movieDescription = null;
	private Button readReviewsButton = null;
	private ImageButton faceBookButton = null;
	private ImageButton twitterButton = null;
	private Button imdbRatingButton = null;
	private Media media = null;
	TvTickerDBAdapter dataAdapter;
	
	String title;
	String subTitle;
	String imdbURL;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ADD_TO_FAVORITES, 0, "fav").setIcon(
				R.drawable.ic_action_fav).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailed_description);
		dataAdapter = new TvTickerDBAdapter(this);
		media = (Media) this.getIntent().getExtras().getSerializable(Constants.MEDIA_OBJECT);
		title = media.getMediaTitle();
		subTitle = "channel_name, " + media.getShowTime();
		imdbURL = media.getImdbLink();

		// configure action bar - private method
		configureActionbarWith(getSupportActionBar(), title, subTitle);

		movieThumb = (WebImageView) findViewById(R.id.movie_thumb);
		movieDescription = (TextView) findViewById(R.id.movie_description);
		readReviewsButton = (Button) findViewById(R.id.button_go_imdb);

		faceBookButton = (ImageButton) findViewById(R.id.go_facebook_button);
		twitterButton = (ImageButton) findViewById(R.id.go_twitter_button);
		// non_interactive fields
		imdbRatingButton = (Button) findViewById(R.id.rating_non_interactive_button);

		// set up IMDB rating got from web api, here
		imdbRatingButton.setText(getFormattedImdbTextRatingFor(media.getImdbRating()));

		movieThumb.setImageWithURL(this, media.getMediaThumb());
		movieDescription.setText(media.getMediaDescription());

		// adjust sliding drawer's handle opacity
		adjustAlphaOf(R.id.handle, 220);

		// button click listeners
		readReviewsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ask permission from user before loading the url in browser,
				// need to implement this
				Uri uriUrl = Uri.parse(imdbURL);
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);
			}
		});

		faceBookButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMsg("facebook");
			}
		});
		twitterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMsg("twitter");
			}
		});
	}

	// Action bar menu item click listener.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD_TO_FAVORITES:
			showMsg(media.getId() + "Added to favorites");
			dataAdapter.open();
			dataAdapter.setIsFavorite(media.getId(), true, true);
			dataAdapter.close();
			//not recommended !
			ViewPagerAdapter.staticAdapterObj.refreshFavAdapter(media);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// helper method to show toasts !
	private void showMsg(String msg) {
		Toast toast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast
				.getYOffset() / 2);
		toast.show();
	}

	private void configureActionbarWith(ActionBar actionBar, String title,
			String subTitle) {
		actionBar.setTitle(title);
		actionBar.setSubtitle(subTitle);
	}

	// helpers for Imdb Rating bar
	private String getFormattedImdbTextRatingFor(float rating) {
		return "" + rating;
	}

	private void adjustAlphaOf(int resId, int alpha) {
		this.findViewById(resId).getBackground().setAlpha(alpha);
	}

}
