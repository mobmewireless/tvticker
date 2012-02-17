package in.mobme.tvticker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BrowseAllShowsActivity extends FragmentActivity{

	private Button channelsButton;
	private Button categoriesButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_all_shows);
		configureActionbarWith(getSupportActionBar(), getResources().getString(R.string.browse_all_shows_title));
	
		channelsButton = (Button) findViewById(R.id.browse_channels_button);
		categoriesButton = (Button) findViewById(R.id.browse_categories_button);
		
		channelsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), AllChannelsActivity.class));
				//Toast.makeText(getBaseContext(), "Channels Button!", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void configureActionbarWith(ActionBar actionBar, String title){
		actionBar.setTitle(title);
	}

}
