package in.mobme.tvticker.activity;

import in.mobme.tvticker.Constants;
import in.mobme.tvticker.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AllShowsMainPageActivity extends FragmentActivity{

	private Button channelsButton;
	private Button categoriesButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_all_shows);
		configureActionbarWith(getSupportActionBar(), getResources().getString(R.string.browse_all_shows_title));
	
		channelsButton = (Button) findViewById(R.id.browse_channels_button);
		categoriesButton = (Button) findViewById(R.id.browse_categories_button);
		
		channelsButton.setOnClickListener(new ButtonOnClickListener(Constants.CHANNEL_FILTER));
		categoriesButton.setOnClickListener(new ButtonOnClickListener(Constants.CATEGORY_FILTER));
		
	}
	
	private void configureActionbarWith(ActionBar actionBar, String title){
		actionBar.setTitle(title);
	}
	
	private class ButtonOnClickListener implements OnClickListener {

		int filterType;
		
		public ButtonOnClickListener(int filterType) {
			this.filterType = filterType;
		}
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getBaseContext(), AllShowsFilterListActivity.class);
			intent.putExtra(Constants.FILTER_TAG, filterType);
			startActivity(intent);
		}
		
	}
	
	

}


