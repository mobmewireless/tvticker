package in.mobme.tvticker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class EditShowRemindersActivity extends Activity{

	private ListView listView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.default_listview);
		listView = (ListView)findViewById(android.R.id.list);
		
	}


}
