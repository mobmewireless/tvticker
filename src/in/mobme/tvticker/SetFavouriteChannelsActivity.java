package in.mobme.tvticker;

import in.mobme.tvticker.database.Models.ChannelsInfo;
import in.mobme.tvticker.database.TvTickerDBAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class SetFavouriteChannelsActivity extends Activity {
	private HashMap<Integer, Object> changes = new HashMap<Integer, Object>();
	TvTickerDBAdapter dbAdapter = null;
	ListView listView;
	List<FavouriteChannelModel> list = new ArrayList<FavouriteChannelModel>();

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		dbAdapter = new TvTickerDBAdapter(getBaseContext());

		setContentView(R.layout.default_listview);
		listView = (ListView) findViewById(android.R.id.list);
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		list = getModel();
		listView.setAdapter(new FavouriteChannelsArrayAdapter(this,
				R.layout.favourite_channels, list));

	}

	private List<FavouriteChannelModel> getModel() {
		dbAdapter.open();
		Cursor cursor = dbAdapter.fetchAllChannels();
		List<FavouriteChannelModel> list = new ArrayList<FavouriteChannelModel>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			long id = cursor
					.getLong(cursor.getColumnIndex(ChannelsInfo.ROW_ID));
			String channelName = cursor.getString(cursor
					.getColumnIndex(ChannelsInfo.CHANNEL_NAME));
			boolean isSelected = getSelected(cursor.getInt(cursor
					.getColumnIndex(ChannelsInfo.IS_FAVORITE_CHANNEL)));
			list.add(get(id, channelName, isSelected));
			cursor.moveToNext();

		}
		cursor.close();
		dbAdapter.close();
		return list;
	}
	
	

	private FavouriteChannelModel get(long id, String tag, boolean selected) {
		return new FavouriteChannelModel(id, tag, selected);
	}

	private boolean getSelected(int value) {
		return value == 1 ? true : false;
	}

	@Override
	public void onBackPressed() {
		Toast.makeText(this, "Saving..", Toast.LENGTH_SHORT).show();
		dbAdapter.open();
		dbAdapter.setFavoriteChannels(list);
		dbAdapter.close();
		super.onBackPressed();
	}

}
