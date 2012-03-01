package in.mobme.tvticker;

public class Constants {
	public final static String TAG = "TVTicker";
	public final static String MEDIA_OBJECT = "media_object";

	public final static String MEDIA_LIST_TAG = "in.mobme.tvticker.media_list";
	
	public final static String FILTER_TAG = "in.mobme.tvticker.filter"; 	
	public final static int CHANNEL_FILTER = 0;
	public final static int CATEGORY_FILTER = 1;
	public final static String MODE = "start_mode";

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
	
public class RPC {
		
		public final static String SERVICE_URI = "http://api.tvticker.in/service";

		public final static String API_KEY = "tvticker";
		public final static String PROGRAM_TAG = "program";
		public final static String CHANNEL_TAG = "channel";
		public final static String CATEGORY_TAG = "category";
		public final static String VERSION_TAG = "version";
		public final static String PROGRAMS = "programs";
		public final static String CHANNELS = "channels";
		public final static String CATEGORIES = "categorys";
		public final static String VERSIONS = "versions";
		public final static int CONNECTION_TIMEOUT = 2000;
		public final static int SO_TIMEOUT = 5000;
		
		public class Media {
			public final static String _ID = "id";
			public final static String TITLE_TAG = "name";
			public final static String CATEGORY_TAG = "category_id";
			public final static String CHANNEL_TAG = "channel_id";
			public final static String IMDB_INFO_TAG = "imdb_info";
			public final static String IMDB_RATING_TAG = "rating";
			public final static String SHOW_TIME_START_TAG = "air_time_start";
			public final static String SHOW_TIME_END_TAG = "air_time_end";
			public final static String VERSION_TAG = "version_id";
			public final static String DESCRIPTION_TAG = "description";
			public final static String DURATION_TAG = "run_time";
			public final static String THUMBNAIL_TAG = "thumbnail_link";
			public final static String SERIES_ID_TAG = "series_id";
			public final static String THUMBNAIL_ID_TAG = "thumbnail_id";
			public final static String THUMBNAIL_PREFIX ="http://admin.tvticker.in/image/";
			public final static String THUMBNAIL_LARGE ="/profile";
			public final static String THUMBNAIL_SMALL="/icon60";
			public final static String IMDB_PREFIX  = "http://www.imdb.com/";

		}

		public class Services {
			public final static String PING = "ping";
			public final static String PROGRAMS_FOR_FRAME = "programs_for_current_frame";
			public final static String LIST_OF_CHANNELS = "channels";
			public final static String LIST_OF_CATEGORIES = "categories";
			public final static String UPDATE_TO_VERSION = "update_to_current_version_except_programs";
			public final static String UPDATE_MEDIA = "update_programs_to_current_version";
			public final static String CURRENT_DATA_VERSION = "update_programs_to_current_version";
			public final static String CURRENT_VERSION = "current_version";
			public final static int CACHE_FOR_DAYS =7;
		}
		
		public class FrameType{
			public final static String FRAME_NOW = "now";
			public final static String FRAME_LATER = "later";
			public final static String FRAME_COMPLETE = "full";
		}
		
	}
	

}
