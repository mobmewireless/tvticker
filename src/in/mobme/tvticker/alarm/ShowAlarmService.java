package in.mobme.tvticker.alarm;

import in.mobme.tvticker.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ShowAlarmService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		String[] data = intent.getStringArrayExtra("notification_data");
		System.out.println(data);
		
		//Log.i("Notification", notificationData);
		String message = (String) data[1] + " starts soon on " + data[0];
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher, message, System.currentTimeMillis());
		
		notification.setLatestEventInfo(getBaseContext(), "TV Ticker", message, null);
		notificationManager.notify(1, notification);
		
		Log.i("Notification", "Notification done.");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
