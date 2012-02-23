package in.mobme.tvticker;

import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.data_model.SearchableMedia;
import in.mobme.tvticker.database.TvTickerDBAdapter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchableActivity extends Activity {

	private ListView mListView;
	private TextView mTextView;
	private TvTickerDBAdapter dbAdapter;
	private ArrayList<SearchableMedia> searchResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbAdapter = new TvTickerDBAdapter(this);
		setContentView(R.layout.search_list_view);
		mListView = (ListView) findViewById(R.id.list);
		mTextView = (TextView) findViewById(R.id.search_text);
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);
		}
	}

	private void doMySearch(String query) {
		Log.i(Constants.TAG, "Query: " + query);
		dbAdapter.open();
		searchResult = dbAdapter.getMoviesLike(query);
		dbAdapter.close();
		if(searchResult == null){
			mTextView.setText(getString(R.string.no_results,
					new Object[] { query }));
		}else if (searchResult.size() <= 0 ) {
			// There are no results
			mTextView.setText(getString(R.string.no_results,
					new Object[] { query }));
		}else{
			// Display the number of results
            int count = searchResult.size();
            String countString = getResources().getQuantityString(R.plurals.search_results,
                                    count, new Object[] {count, query});
            mTextView.setText(countString);
            mListView.setAdapter(new SearchArrayAdapter(this,
    				R.layout.search_list_row_layout, searchResult));
    		mListView.setOnItemClickListener(new OnItemClickListener() {
    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
    					long arg3) {
    				dbAdapter.open();
    				Media selectedMedia = dbAdapter.fetchShowsInfoFor(searchResult.get(pos).getMedia_id());
    				dbAdapter.close();
    				Intent detailedViewIntent = new Intent(getBaseContext(),
    						DetailedDescriptionActivity.class);
    				Bundle b = new Bundle();
    				b.putSerializable(Constants.MEDIA_OBJECT, selectedMedia);
    				detailedViewIntent.putExtras(b);
    				startActivity(detailedViewIntent);

    			}
    		});

		}
		
		
	}

}
