package in.mobme.tvticker.alarm;

import in.mobme.tvticker.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ShowAlarmService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		String[] displayText = intent.getStringArrayExtra("alert_display_text");
		String message = "TVTicker: " + displayText[1] + " is now starting on channel " + displayText[0];
			
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher, message, System.currentTimeMillis());
		
		notification.setLatestEventInfo(getBaseContext(), "TV Ticker", message, null);
		notificationManager.notify(1, notification);
		
		//Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
		Log.i("Alarm", message);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
