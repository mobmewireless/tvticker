package in.mobme.tvticker;

import in.mobme.tvticker.R;
import in.mobme.tvticker.adapter.MyArrayAdapter;
import in.mobme.tvticker.data_model.Media;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AllShowsResultListActivity extends FragmentActivity {

	private ArrayList<Media> mediaList;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.default_listview);
		
		ListView listView = (ListView) findViewById(android.R.id.list);

		mediaList = (ArrayList<Media>) getIntent().getExtras().get(Constants.MEDIA_LIST_TAG);

		listView.setAdapter(new MyArrayAdapter(this, R.layout.rowlayout, mediaList, true));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
				Media selectedMedia = mediaList.get(position);
				Intent detailedViewIntent = new Intent(getBaseContext(), DetailedDescriptionActivity.class);
				
				Bundle b = new Bundle();
				b.putSerializable(Constants.MEDIA_OBJECT, selectedMedia);
				detailedViewIntent.putExtras(b);
				
				startActivity(detailedViewIntent);
			}
		
		});
	}
}
