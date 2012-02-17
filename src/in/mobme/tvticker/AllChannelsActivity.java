package in.mobme.tvticker;

import in.mobme.tvticker.database.TvTickerDBAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
			}
		});
		
		
		dbAdapter.close();
	}

}
