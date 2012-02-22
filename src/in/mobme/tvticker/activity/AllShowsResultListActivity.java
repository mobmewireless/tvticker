package in.mobme.tvticker.activity;

import in.mobme.tvticker.Constants;
import in.mobme.tvticker.MyArrayAdapter;
import in.mobme.tvticker.R;
import in.mobme.tvticker.data_model.Media;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

public class AllShowsResultListActivity extends FragmentActivity {

	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.default_listview);
		
		listView = (ListView) findViewById(android.R.id.list);

		ArrayList<Media> mediaList = (ArrayList<Media>) getIntent().getExtras().get(Constants.MEDIA_LIST_TAG);

		listView.setAdapter(new MyArrayAdapter(this, R.layout.rowlayout, mediaList, true));
	}
}
