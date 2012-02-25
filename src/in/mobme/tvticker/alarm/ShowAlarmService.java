package in.mobme.tvticker.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ShowAlarmService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.i("Notified", "Notification done.");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
