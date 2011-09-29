package com.sample.hor_pager;

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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedDescriptionActivity extends FragmentActivity{

	private final int MENU_ADD_TO_FAVORITES = 1;
	private ImageView movieThumb = null;
	private TextView movieDescription = null;
	private Button readReviewsButton = null;
	private TextView imdbRatingText = null;
	private RatingBar imdbRating = null;
	private ImageButton faceBookButton = null;
	private ImageButton twitterButton = null;

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
		final String title = getIntent().getExtras().getString("selectedItem");
		final String subTitle = "channel_name, Time";
		configureActionbarWith(getSupportActionBar(), title, subTitle);
		
		movieThumb = (ImageView) findViewById(R.id.movie_thumb);
		movieDescription = (TextView) findViewById(R.id.movie_description);
		readReviewsButton = (Button) findViewById(R.id.button_go_imdb);
		faceBookButton = (ImageButton) findViewById(R.id.go_facebook_button);
		twitterButton = (ImageButton) findViewById(R.id.go_twitter_button);
		
		//non_interactive fields
		imdbRatingText = (TextView) findViewById(R.id.imdb_rating_text);
		imdbRating = (RatingBar) findViewById(R.id.imdb_ratingBar);
		//set up IMDB rating got from web api, here
		createIMDBRatingIndicator(8.5f);
		
		movieThumb.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
		movieDescription.setText(R.string.sample_description);
		//button click listener
		readReviewsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMsg("I should go bring up the results for <" + title + "> from IMDB");
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD_TO_FAVORITES:
			showMsg("Add to favorites");
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//helper method to show toasts !
	private void showMsg(String msg) {
		Toast toast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast
				.getYOffset() / 2);
		toast.show();
	}
	
	private void configureActionbarWith(ActionBar actionBar, String title, String subTitle){
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg_dark_gray));
		actionBar.setTitle(title);
		actionBar.setSubtitle(subTitle);
	}
	
	private String formatImdbTextRatingWith(float rating){
		return "IMDB " + rating + "/10";
	}
	
	private void createIMDBRatingIndicator(float rating){
		imdbRatingText.setText(formatImdbTextRatingWith(rating));
		imdbRating.setRating(rating);
		
	}

}
