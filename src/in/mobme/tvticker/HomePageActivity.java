package in.mobme.tvticker;

import in.mobme.tvticker.customwidget.ViewPagerIndicator;
import in.mobme.tvticker.customwidget.ViewPagerIndicator.PageInfoProvider;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class HomePageActivity extends FragmentActivity implements
		PageInfoProvider {

	private ViewPager awesomePager;
	private ViewPagerAdapter awesomeAdapter;
	private ViewPagerIndicator indicator = null;
	private int indicator_position = ViewPagerAdapter.NOW_POSITION;
	private static final String[] titles = new String[] { "Favorites", "Now",
			"Later Today", };

	private static final int SETTINGS_MENU_ITEM = Menu.FIRST;
	private static final int SEARCH_MENU_ITEM = SETTINGS_MENU_ITEM + 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SETTINGS_MENU_ITEM, 0, "settings").setIcon(
				R.drawable.ic_action_settings).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add(0, SEARCH_MENU_ITEM, 0, "").setIcon(
				R.drawable.ic_action_search).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getSupportActionBar().setTitle(
				getResources().getString(R.string.home_page_title));
		awesomeAdapter = new ViewPagerAdapter(R.layout.default_listview,
				titles, this);
		awesomePager = (ViewPager) findViewById(R.id.awesomepager);
		indicator = (ViewPagerIndicator) findViewById(R.id.indicator);

		awesomePager.setAdapter(awesomeAdapter);
		awesomePager.setOnPageChangeListener(indicator);
		awesomePager.setCurrentItem(indicator_position);

		// zylinc way
		indicator.init(indicator_position, awesomeAdapter.getCount(), this);
		Resources res = getResources();
		Drawable prev = res.getDrawable(R.drawable.indicator_prev_arrow);
		Drawable next = res.getDrawable(R.drawable.indicator_next_arrow);

		// Set images for previous and next arrows.
		indicator.setArrows(prev, next);
		indicator.setOnClickListener(new OnIndicatorClickListener());

	}

	// need to remember indicator position, if app is on pause
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putInt("indicator_pointer", indicator
				.getCurrentPosition());
		super.onSaveInstanceState(savedInstanceState);
	}

	// restore indicator position, if on resume
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		indicator_position = savedInstanceState.getInt("indicator_pointer");
		indicator.setCurrentPostion(indicator_position);

	}

	// handles indicator click events !
	private class OnIndicatorClickListener implements
			ViewPagerIndicator.OnClickListener {
		@Override
		public void onCurrentClicked(View v) {
			showMsg("On same page !");
		}

		@Override
		public void onNextClicked(View v) {
			awesomePager.setCurrentItem(Math.min(awesomeAdapter.getCount() - 1,
					indicator.getCurrentPosition() + 1));
		}

		@Override
		public void onPreviousClicked(View v) {
			awesomePager.setCurrentItem(Math.max(0, indicator
					.getCurrentPosition() - 1));
		}

	}

	// Action Bar menu item click events
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SEARCH_MENU_ITEM:
			onSearchRequested();
			showMsg("Ok");
			break;
		case SETTINGS_MENU_ITEM:
			showSettingsPage();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showSettingsPage() {
		Intent settingsIntent = new Intent(getBaseContext(),
				SettingsPreferenceActivity.class);
		startActivity(settingsIntent);
	}

	// helper method to show toasts !
	private void showMsg(String msg) {
		Toast toast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast
				.getYOffset() / 2);
		toast.show();
	}

	@Override
	public String getTitle(int pos) {
		return titles[pos];
	}

}