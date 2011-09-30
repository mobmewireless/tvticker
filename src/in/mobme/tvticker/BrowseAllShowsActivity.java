package in.mobme.tvticker;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;

public class BrowseAllShowsActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_all_shows);
		configureActionbarWith(getSupportActionBar(), getResources().getString(R.string.browse_all_shows_title));
	}
	
	private void configureActionbarWith(ActionBar actionBar, String title){
		actionBar.setTitle(title);
	}

}
