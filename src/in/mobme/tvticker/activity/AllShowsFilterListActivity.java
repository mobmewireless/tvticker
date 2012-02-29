package in.mobme.tvticker.activity;

import in.mobme.tvticker.Constants;
import in.mobme.tvticker.R;
import in.mobme.tvticker.adapter.AllShowsCursorAdapter;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AllShowsFilterListActivity extends Activity {

	private ListView listView;

	TvTickerDBAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.default_listview);
		listView = (ListView) findViewById(android.R.id.list);

		dbAdapter = new TvTickerDBAdapter(this);
		dbAdapter.open();
		Cursor dataCursor;
		if (getIntent().getExtras().getInt(Constants.FILTER_TAG) == Constants.CHANNEL_FILTER) {
			dataCursor = dbAdapter.fetchAllChannels();
		} else {
			dataCursor = dbAdapter.fetchAllCategories();
		}
		startManagingCursor(dataCursor);
		AllShowsCursorAdapter dataAdapter = new AllShowsCursorAdapter(
				getBaseContext(), dataCursor, this);
		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(getBaseContext(),
						AllShowsResultListActivity.class);

				ArrayList<Media> mediaList;
				if (getIntent().getExtras().getInt(Constants.FILTER_TAG) == Constants.CHANNEL_FILTER) {
					mediaList = dbAdapter.fetchAllShowsForChannel((String) view
							.getTag());
				} else {
					mediaList = dbAdapter
							.fetchAllShowsForCategory((String) view.getTag());
				}

				intent.putExtra(Constants.MEDIA_LIST_TAG, mediaList);
				startActivity(intent);

			}
		});
	}

	@Override
	protected void onDestroy() {
		dbAdapter.close();
		super.onDestroy();
	}
	
	

}
