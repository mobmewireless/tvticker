package in.mobme.tvticker;

import in.mobme.tvticker.adapter.FavouriteChannelsArrayAdapter;
import in.mobme.tvticker.adapter.ViewPagerAdapter;
import in.mobme.tvticker.data_model.FavouriteChannelModel;
import in.mobme.tvticker.database.Models.ChannelsInfo;
import in.mobme.tvticker.database.TvTickerDBAdapter;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;

public class SetFavouriteChannelsActivity extends FragmentActivity {
	private TvTickerDBAdapter dbAdapter = null;
	private ListView listView;
	private List<FavouriteChannelModel> list = new ArrayList<FavouriteChannelModel>();
	private static final int SELECT_ALL_MENU_ITEM = Menu.FIRST;
	public static int selectedCount = 0;
	private int cursorCount = 0;
	private boolean allChannelsSelected = false;
	private FavouriteChannelsArrayAdapter adapter;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SELECT_ALL_MENU_ITEM, 0, "select_all")
				.setIcon(getSelectAllDrawable(checkChannelCounts()))
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		dbAdapter = new TvTickerDBAdapter(getBaseContext());
		dbAdapter.open();
		setContentView(R.layout.default_listview);
		getSupportActionBar().setTitle(
				getResources().getString(R.string.fav_channel_page_title));
		listView = (ListView) findViewById(android.R.id.list);
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		list = getModel();
		adapter = new FavouriteChannelsArrayAdapter(this,
				R.layout.favourite_channels, list, dbAdapter);
		listView.setAdapter(adapter);
	}

	private int getSelectAllDrawable(boolean isTrue) {
		return isTrue ? R.drawable.btn_check_buttonless_on
				: R.drawable.btn_check_buttonless_off;
	}

	private boolean checkChannelCounts() {
		if (selectedCount == cursorCount) {
			allChannelsSelected = true;
			return true;
		} else {
			allChannelsSelected = false;
			return false;
		}
	}

	// Action bar menu item click listener.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SELECT_ALL_MENU_ITEM:
			allChannelsSelected = !allChannelsSelected;
			item.setIcon(getSelectAllDrawable(allChannelsSelected));
			dbAdapter.setAllChannelsAsFavorites(allChannelsSelected);
			selectAllInList(allChannelsSelected);
		}
		return super.onOptionsItemSelected(item);
	}

	// toggles select all option
	private void selectAllInList(boolean isSelected) {
		for (int i = 0; i < listView.getCount(); i++) {
			list.get(i).setSelected(isSelected);
		}
		selectedCount = (!isSelected) ? 0 : cursorCount;
		adapter.notifyDataSetChanged();
	}

	private List<FavouriteChannelModel> getModel() {
		selectedCount = 0;
		Cursor cursor = dbAdapter.fetchAllChannels();
		cursorCount = cursor.getCount();
		List<FavouriteChannelModel> list = new ArrayList<FavouriteChannelModel>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			long id = cursor
					.getLong(cursor.getColumnIndex(ChannelsInfo.ROW_ID));
			String channelName = cursor.getString(cursor
					.getColumnIndex(ChannelsInfo.CHANNEL_NAME));
			boolean isSelected = getSelected(cursor.getInt(cursor
					.getColumnIndex(ChannelsInfo.IS_FAVORITE_CHANNEL)));
			if (isSelected) {
				selectedCount += 1;
			}
			list.add(get(id, channelName, isSelected));
			cursor.moveToNext();

		}
		cursor.close();

		return list;
	}

	private FavouriteChannelModel get(long id, String tag, boolean selected) {
		return new FavouriteChannelModel(id, tag, selected);
	}

	private boolean getSelected(int value) {
		return value == 1 ? true : false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (selectedCount == 0) {
				Toast.makeText(getBaseContext(), "Please select atleast one channel !", Toast.LENGTH_SHORT).show();
				return false;
			} 
		}
		Toast.makeText(getBaseContext(), "Applying changes..", Toast.LENGTH_SHORT).show();
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		dbAdapter.close();
		ViewPagerAdapter.staticAdapterObj.notifyDataSetChanged();
		super.onDestroy();
	}

}
