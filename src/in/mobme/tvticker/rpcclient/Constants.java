package in.mobme.tvticker.rpcclient;


public class Constants {
	final static String TAG = "RPCClient";
	
	public class RPC {

		public final static String SERVICE_URI = "http://api.tvticker.in/service";

		final static String API_KEY = "tvticker";
		final static String PROGRAM_TAG = "program";
		final static String CHANNEL_TAG = "channel";
		final static String CATEGORY_TAG = "category";
		final static String VERSION_TAG = "version";
		final static String PROGRAMS = "programs";
		final static String CHANNELS = "channels";
		final static String CATEGORIES = "categorys";
		final static String VERSIONS = "versions";
		final static int CONNECTION_TIMEOUT = 2000;
		final static int SO_TIMEOUT = 5000;
		
		public class Media {
			final static String _ID = "id";
			final static String TITLE_TAG = "name";
			final static String CATEGORY_TAG = "category_id";
			final static String CHANNEL_TAG = "channel_id";
			final static String IMDB_INFO_TAG = "imdb_info";
			final static String IMDB_RATING_TAG = "rating";
			final static String SHOW_TIME_START_TAG = "air_time_start";
			final static String SHOW_TIME_END_TAG = "air_time_end";
			final static String VERSION_TAG = "version_id";
			final static String DESCRIPTION_TAG = "description";
			final static String DURATION_TAG = "run_time";
			final static String THUMBNAIL_TAG = "thumbnail_link";
			final static String SERIES_ID_TAG = "series_id";
			final static String THUMBNAIL_ID_TAG = "thumbnail_id";

			public final static String THUMBNAIL_PREFIX ="http://admin.tvticker.in/image/";

			public final static String THUMBNAIL_SUFFIX ="/profile";

		}

		public class Services {
			final static String PING = "ping";
			final static String PROGRAMS_FOR_FRAME = "programs_for_current_frame";
			final static String LIST_OF_CHANNELS = "channels";
			final static String LIST_OF_CATEGORIES = "categories";
			final static String UPDATE_TO_VERSION = "update_to_current_version";
			final static String CURRENT_DATA_VERSION = "current_version";
		}
		
		public class FrameType{
			public final static String FRAME_NOW = "now";
			public final static String FRAME_LATER = "later";
			public final static String FRAME_COMPLETE = "full";
		}
		
	}
}
