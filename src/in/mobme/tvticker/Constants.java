package in.mobme.tvticker;

public class Constants {
	public final static String TAG = "TVTicker";
	public final static String MEDIA_OBJECT = "media_object";

	public final static String MEDIA_LIST_TAG = "in.mobme.tvticker.media_list";
	
	public final static String FILTER_TAG = "in.mobme.tvticker.filter"; 	
	public final static int CHANNEL_FILTER = 0;
	public final static int CATEGORY_FILTER = 1;

	public final static String ALARM_INTENT_DATA_TAG = "in.mobme.tvticker.alarm_intent";
	public final static String ALARM_INTENT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String ALARM_INTENT_MEDIA_ID_TAG = "in.mobme.tvticker.alarm_intent_media_id";
	
	public static final String PREF_FIRST_REMINDER = "first_reminder_pref";
	public static final String PREF_SECOND_REMINDER = "second_reminder_pref";
	public static final String PREF_SOUND_ENABLED = "enable_notification_sound";
	public static final String PREF_VIBRATION_ENABLED = "enable_notification_vibrate";
	public static final String PREF_ENABLE_REMINDERS = "enable_reminders";
	
	public class ViewPager{
		public final static String INDICATOR_POINTER = "indicator_pointer";
		public final static int FAVORITES_POSITION = 0;
		public final static int NOW_POSITION = 1;
		public final static int LATER_TODAY_POSITION = 2;
		public final static String FAVORITES_LABEL = "Favorites";
		public final static String NOW_LABEL = "Now";
		public final static String LATER_TODAY_LABEL = "Later Today";
	}
	

}
