package in.mobme.tvticker;

import in.mobme.tvticker.data_model.Media;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.ListView;

public class BrowseChannelShowsActivity extends FragmentActivity {

	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.default_listview);
		
		//LayoutInflater layoutInflater = getLayoutInflater();
		//listView = (ListView) layoutInflater.inflate(R.layout.rowlayout, null);
		listView = (ListView) findViewById(android.R.id.list);

		ArrayList<Media> mediaList = (ArrayList<Media>) getIntent().getExtras().get("in.mobme.tvticker.media_list");
		System.out.println(mediaList);
		listView.setAdapter(new MyArrayAdapter(this, R.layout.rowlayout, mediaList, true));
	}
}
