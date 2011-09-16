package com.sample.hor_pager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.Toast;

import com.jakewharton.android.viewpagerindicator.TitlePageIndicator;

public class AwsomePagerActivity extends FragmentActivity {
	private ViewPager awesomePager;
	// private static int NUM_AWESOME_VIEWS = 3;
	private Context cxt;
	private AwesomePagerAdapter awesomeAdapter;
	private TitlePageIndicator indicator = null;
	private static final String[] titles = new String[] { "Page 1", "Page 2",
			"Page 3" };
	private static final int SEARCH_MENU_ITEM = Menu.FIRST;
	private static final int SETTINGS_MENU_ITEM = SEARCH_MENU_ITEM + 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add(0, SEARCH_MENU_ITEM, 0, "").setIcon(
		// R.drawable.ic_action_search).setShowAsAction(
		// MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, SETTINGS_MENU_ITEM, 0, "b").setIcon(
				R.drawable.ic_action_settings).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ActionBarSherlock !!
		// requestWindowFeature(Window.FEATURE_ACTION_BAR_ITEM_TEXT);
		setContentView(R.layout.main);
		cxt = this;
		// Load partially transparent black background
		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.ab_bg_dark_gray));
		awesomeAdapter = new AwesomePagerAdapter(R.layout.listview1,
				R.layout.rowlayout, titles, cxt);
		awesomePager = (ViewPager) findViewById(R.id.awesomepager);
		indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		awesomePager.setAdapter(awesomeAdapter);
		indicator.setBackgroundResource(R.drawable.indicator_background);
		indicator.setTextColor(Color.GRAY);
		indicator.setFooterIndicatorPadding(7.0f);
		indicator.setTextSize(10f);
		indicator.setSelectedTextSize(11.5f);
		indicator.setSelectedColor(Color.WHITE);
		indicator.setFooterIndicatorColor(Color.WHITE);
		indicator.setFooterColor(Color.WHITE);
		indicator.setFooterIndicatorHeight(5.0f);
		indicator.setViewPager(awesomePager);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SEARCH_MENU_ITEM:
			showMsg("Ok");
			break;
		case SETTINGS_MENU_ITEM:
			showMsg("Save");
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showMsg(String msg) {
		Toast toast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast
				.getYOffset() / 2);
		toast.show();
	}

}