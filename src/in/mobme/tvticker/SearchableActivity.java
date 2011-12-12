package in.mobme.tvticker;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class SearchableActivity extends Activity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_list_view);
        mListView = (ListView) findViewById(R.id.list);
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
		 Toast.makeText(getBaseContext(), "Action Search !", Toast.LENGTH_SHORT).show();
		 Log.i(Constants.TAG, "Query: " + query);
	 }

}
