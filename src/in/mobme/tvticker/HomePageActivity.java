package in.mobme.tvticker;

import in.mobme.customwidget.ViewPagerIndicator;
import in.mobme.customwidget.ViewPagerIndicator.PageInfoProvider;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
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
	private Context cxt;
	private ViewPagerAdapter awesomeAdapter;
	private ViewPagerIndicator indicator = null;
	private static final String[] titles = new String[] { "Now", "Later Today",
			"Favorites" };

	private static final int SETTINGS_MENU_ITEM = Menu.FIRST;
	private static final int SEARCH_MENU_ITEM = SETTINGS_MENU_ITEM + 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SETTINGS_MENU_ITEM, 0, "settings").setIcon(
				R.drawable.ic_action_settings).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS);
		// menu.add(0, SEARCH_MENU_ITEM, 0, "").setIcon(
		// R.drawable.ic_action_search).setShowAsAction(
		// MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		cxt = this;
		// Load ActionBar
		configureActionbarWith(getSupportActionBar(), getResources().getString(R.string.home_page_title));
		awesomeAdapter = new ViewPagerAdapter(R.layout.default_listview,
				R.layout.rowlayout, titles, cxt);
		awesomePager = (ViewPager) findViewById(R.id.awesomepager);
		indicator = (ViewPagerIndicator) findViewById(R.id.indicator);

		awesomePager.setAdapter(awesomeAdapter);
		awesomePager.setOnPageChangeListener(indicator);

		// zylinc way
		indicator.init(0, awesomeAdapter.getCount(), this);
		Resources res = getResources();
		Drawable prev = res.getDrawable(R.drawable.indicator_prev_arrow);
		Drawable next = res.getDrawable(R.drawable.indicator_next_arrow);

		// Set images for previous and next arrows.
		indicator.setArrows(prev, next);
		indicator.setOnClickListener(new OnIndicatorClickListener());
	}

	// handle indicator click events !
	private class OnIndicatorClickListener implements
			ViewPagerIndicator.OnClickListener {
		@Override
		public void onCurrentClicked(View v) {
			// TODO Auto-generated method stub
			showMsg("On same page !");
		}

		@Override
		public void onNextClicked(View v) {
			// TODO Auto-generated method stub
			awesomePager.setCurrentItem(Math.min(awesomeAdapter.getCount() - 1,
					indicator.getCurrentPosition() + 1));
		}

		@Override
		public void onPreviousClicked(View v) {
			// TODO Auto-generated method stub
			awesomePager.setCurrentItem(Math.max(0, indicator
					.getCurrentPosition() - 1));
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SEARCH_MENU_ITEM:
			showMsg("Ok");
			break;
		case SETTINGS_MENU_ITEM:
			showMsg("Settings icon clicked");
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void configureActionbarWith(ActionBar actionBar, String title) {
		actionBar.setTitle(title);
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
		// TODO Auto-generated method stub
		return titles[pos];
	}

}