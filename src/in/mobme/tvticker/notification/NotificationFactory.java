package in.mobme.tvticker.notification;

import in.mobme.tvticker.Constants;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationFactory {

	public static void createNotification(Activity parentActivity, Date time, String[] data, long mediaId) {
		Intent myIntent = new Intent(parentActivity, ShowNotificationService.class);
		
		Calendar calender = Calendar.getInstance();
		calender.setTimeInMillis(time.getTime());
		calender.add(Calendar.MINUTE, -15);
			
		myIntent.putExtra(Constants.ALARM_INTENT_DATA_TAG, data);
		myIntent.putExtra(Constants.ALARM_INTENT_MEDIA_ID_TAG, mediaId);
		
		PendingIntent pendingIntent = PendingIntent.getService(parentActivity, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) parentActivity.getSystemService(Context.ALARM_SERVICE);
			
		alarmManager.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);
		
		Toast.makeText(parentActivity, "Reminder is set", Toast.LENGTH_SHORT).show();
	}
	
	
}
