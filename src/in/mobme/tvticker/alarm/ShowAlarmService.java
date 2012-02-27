package in.mobme.tvticker.alarm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.mobme.tvticker.Constants;
import in.mobme.tvticker.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ShowAlarmService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
						
		String[] data = intent.getStringArrayExtra(Constants.ALARM_INTENT_DATA_TAG);
		// NOTE: data = { channel_name, show_name, show_time }
		
		String message = (String) data[1] + " starts soon on " + data[0];
		
		DateFormat formatter = new SimpleDateFormat(Constants.ALARM_INTENT_DATE_FORMAT);
		Date date = new Date(); 
		try {
			date = (Date) formatter.parse(data[2]);
		} catch (ParseException e) {
			Log.w("Notification", "Date format exception in unparceled date : " + data[2]);
		}
				
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher, message, date.getTime());
		
		notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notification.setLatestEventInfo(getBaseContext(), "TV Ticker", message, PendingIntent.getActivity(getBaseContext(), 0, new Intent(), 0));
		notificationManager.notify(data.hashCode(), notification);
		
		Log.i("Notification", "Notification done.");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
