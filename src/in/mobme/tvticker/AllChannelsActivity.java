package in.mobme.tvticker;

import java.util.ArrayList;

import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AllChannelsActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.default_listview);
		
		TvTickerDBAdapter dbAdapter = new TvTickerDBAdapter(getBaseContext());
		dbAdapter.open();
		
		listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(new AllShowsCursorAdapter(getBaseContext(), dbAdapter.fetchAllChannels(), this));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				Intent intent = new Intent(getBaseContext(), BrowseChannelShowsActivity.class);
				
				TvTickerDBAdapter dbAdapter = new TvTickerDBAdapter(getBaseContext());
				dbAdapter.open();

				ArrayList<Media> mediaList = dbAdapter.fetchAllShowsForChannel((String) view.getTag());
				dbAdapter.close();
				
				intent.putExtra("in.mobme.tvticker.media_list", mediaList);
				
				startActivity(intent);
				
			}
		});
		
		
		dbAdapter.close();
	}

}
