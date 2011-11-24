package in.mobme.tvticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class SplashActivity extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 2000; // time to display the splash screen in ms

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		// thread for displaying the SplashScreen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (_active && (waited < _splashTime)) {
						//will need to do something usefull here. Right now just sleeping..
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					if (_active) {
						startActivity(new Intent(getBaseContext(),
								HomePageActivity.class));
					}
				}
			}
		};
		splashTread.start();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			_active = false;
			Log.v("TvTicker", "App launch cancelled. Exiting..");
		}
		return true;
	}

}
