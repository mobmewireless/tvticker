package in.mobme.tvticker;

import in.mobme.tvticker.helpers.DataLoader;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class SplashActivity extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 1000; // time to display the splash screen in ms
	protected boolean isDisabled = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		if (isDisabled) {
			finish();
			startActivity(new Intent(getBaseContext(), HomePageActivity.class));
		} else {
			// thread for displaying the SplashScreen
			Thread splashTread = new Thread() {
				@Override
				public void run() {
					try {
						// Wait for _splashTime
						int waited = 0;
						while (_active && (waited < _splashTime)) {
							// will need to do something useful here. Right now
							// just sleeping..
							sleep(100);

							if (_active) {
								waited += 100;
							}
						}
						// Check connection here..

					} catch (InterruptedException e) {
						// do nothing
						Log.e(Constants.TAG, e.toString());
					} finally {
						finish();
						if (_active) {
							DataLoader dataloader = new DataLoader(
									getBaseContext());
							try {
								dataloader.starUpdation();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							startActivity(new Intent(getBaseContext(),
									HomePageActivity.class));
							//DataMocker dataMocker = new DataMocker(
							//		getBaseContext());
							//dataMocker.startMocking();
						}

					}
				}
			};
			splashTread.start();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			_active = false;
			Log.v(Constants.TAG, "App launch cancelled. Exiting..");
		}
		return true;
	}

}
