package in.mobme.tvticker;

import in.mobme.tvticker.database.TvTickerDBAdapter;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class AllChannelsActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.default_listview);

		listView = (ListView) findViewById(android.R.id.list);
		TvTickerDBAdapter dbAdapter = new TvTickerDBAdapter(getBaseContext());
		dbAdapter.open();
		listView.setAdapter(new CursorAdapter(getBaseContext(), dbAdapter
				.fetchAllChannels()) {

			@Override
			public View newView(Context context, Cursor cursor, ViewGroup parent) {
				// TextView textItem =
				LayoutInflater inflater = getLayoutInflater();
				View rowView = inflater.inflate(R.layout.channel_row, null,
						true);
				TextView tv = (TextView) rowView.findViewById(R.id.list_item);
				tv.setText(cursor.getString(1));

				return rowView;
			}

			@Override
			public void bindView(View view, Context context, Cursor cursor) {
				// TODO Auto-generated method stub

			}
		});

		dbAdapter.close();
	}

}
