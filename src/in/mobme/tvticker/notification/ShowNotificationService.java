package in.mobme.tvticker.notification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.mobme.tvticker.Constants;
import in.mobme.tvticker.R;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class ShowNotificationService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
						
		String[] data = intent.getStringArrayExtra(Constants.ALARM_INTENT_DATA_TAG);
		// NOTE: data = { channel_name, show_name, show_time }
		long mediaId = intent.getLongExtra(Constants.ALARM_INTENT_MEDIA_ID_TAG, 0);
		
		TvTickerDBAdapter dbAdapter = new TvTickerDBAdapter(getBaseContext());
		dbAdapter.open();
		boolean showReminder = dbAdapter.IsFavoriteEnabledFor(mediaId);
		dbAdapter.close();
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isRemindersEnabled = sharedPrefs.getBoolean(Constants.PREF_ENABLE_REMINDERS, true);
		
		if (showReminder && isRemindersEnabled) {
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
			
			if (sharedPrefs.getBoolean(Constants.PREF_SOUND_ENABLED, true)) {
				notification.defaults |= Notification.DEFAULT_SOUND;
			}
			
			if (sharedPrefs.getBoolean(Constants.PREF_VIBRATION_ENABLED, false)) {
				notification.defaults |= Notification.DEFAULT_VIBRATE;	
			}
			
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			
			notification.setLatestEventInfo(getBaseContext(), "TV Ticker", message, PendingIntent.getActivity(getBaseContext(), 0, new Intent(), 0));
			notificationManager.notify(data.hashCode(), notification);
			
			Log.i("Notification", "Notification done.");
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
