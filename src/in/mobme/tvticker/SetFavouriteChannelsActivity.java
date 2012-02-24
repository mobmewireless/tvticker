package in.mobme.tvticker;

import java.util.ArrayList;
import java.util.HashMap;

import in.mobme.tvticker.database.TvTickerDBAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

public class SetFavouriteChannelsActivity extends Activity {
	private HashMap hm= new HashMap<Integer, Object>();
	private HashMap changes= new HashMap<Integer, Object>();
	private ArrayList<HashMap<String, Object>> list_items = new ArrayList<HashMap<String, Object>>();
	// store state
	private ArrayList<HashMap<Integer, Boolean>> isChecked = new ArrayList<HashMap<Integer, Boolean>>();
	private final String ID = "_id";
	private final String NAME = "name";
	private final String IS_FAV = "is_favorite";

	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.default_listview);

		TvTickerDBAdapter dbAdapter = new TvTickerDBAdapter(getBaseContext());
		dbAdapter.open();
		Cursor cursor = dbAdapter.fetchAllChannels();
		if (cursor.getCount() == 0) {
			Log.i("Cursor Null", "CURSOR IS NULL");
			dbAdapter.close();
		} else if (cursor.getCount() > 0) {
			for (cursor.move(0); cursor.moveToNext(); cursor.isAfterLast()) {
				String name = cursor.getString(cursor.getColumnIndex("channel_name"));
				int id = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex("_id")));
				int is_fav =  Integer.parseInt(cursor.getString(cursor
						.getColumnIndex("is_favorite")));
				hm.put(id, is_fav);
			}

			ListView listView = (ListView) findViewById(android.R.id.list);
			listView.setAdapter(new FavouriteChannelsCursorAdapter(
					getBaseContext(), dbAdapter.fetchAllChannels(), true,hm));
			listView.setItemsCanFocus(false);
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			dbAdapter.close();
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					CheckedTextView textview = (CheckedTextView) view;
					
					int tag = (Integer) textview.getTag() ;
					Log.i("id",""+tag);
					int currentStatus = (Integer) hm.get(tag);
					Log.i("status",(currentStatus==1)?"unchecking":"checking");
					hm.put(tag, (currentStatus==1)?0:1);
					changes.put(tag, (currentStatus==1)?0:1);
				//	textview.setChecked((currentStatus==0));
					Log.i("INFO", "items" + hm);
				}
			});
			
		}

	}

	@Override
	public void onBackPressed() {
		TvTickerDBAdapter dbAdapter = new TvTickerDBAdapter(getBaseContext());
		dbAdapter.open();
		dbAdapter.setFavoriteChannels(changes);
		dbAdapter.close();
		super.onBackPressed();
	}
	
}
