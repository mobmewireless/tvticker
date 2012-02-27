package in.mobme.tvticker.notification;

import in.mobme.tvticker.Constants;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class NotificationFactory {

	public static void createNotification(Activity parentActivity, Date time, String[] data, long mediaId) {
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(parentActivity);
		int firstReminder = Integer.parseInt(sharedPrefs.getString(Constants.PREF_FIRST_REMINDER, "30"));
		int secondReminder = Integer.parseInt(sharedPrefs.getString(Constants.PREF_SECOND_REMINDER, "0"));
		
		if (firstReminder != 0) {
			Calendar calender = Calendar.getInstance();
			calender.setTimeInMillis(time.getTime());
			calender.add(Calendar.MINUTE, -(firstReminder));
			
			createSystemAlert(parentActivity, calender.getTimeInMillis(), time, data, mediaId);
			
			if (secondReminder != 0) {
				calender.add(Calendar.MINUTE, firstReminder);
				calender.add(Calendar.MINUTE, -(secondReminder));

				createSystemAlert(parentActivity, calender.getTimeInMillis(), time, data, mediaId);
			}
			
		}
	}
	
	private static void createSystemAlert(Activity parentActivity, long alertTime, Date time, String[] data, long mediaId) {
		Intent myIntent = new Intent(parentActivity, ShowNotificationService.class);
				
		myIntent.putExtra(Constants.ALARM_INTENT_DATA_TAG, data);
		myIntent.putExtra(Constants.ALARM_INTENT_MEDIA_ID_TAG, mediaId);
				
		PendingIntent pendingIntent = PendingIntent.getService(parentActivity, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) parentActivity.getSystemService(Context.ALARM_SERVICE);
			
		alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, pendingIntent);
		
		Toast.makeText(parentActivity, "Set for " + alertTime, Toast.LENGTH_SHORT).show();
		
	}
	
	
}
