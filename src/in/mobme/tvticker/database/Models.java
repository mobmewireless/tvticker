package in.mobme.tvticker.database;

/**
 * Model definition for TvTickerDataBase
 * 
 * @author vishnu
 * 
 */
public class Models {

	/* Primary key definitions for each table */
	private final static String KEY_ROW_ID = "_id";
	private final static String KEY_MEDIA_ROW_ID = "media_id";
	private final static String KEY_CHANNEL_ROW_ID = "channel_id";
	private final static String KEY_CATEGORY_ROW_ID = "category_id";
	private final static String KEY_IMDB_ROW_ID = "imdb_id";
	private final static String KEY_SERIES_ROW_ID = "series_id";

	public final static int IS_TRUE = 1;
	public final static int IS_FALSE = 0;

	/* Version Info table */
	class Version {
		/* table name */
		final static String TABLE_NAME = "version";
		/* columns */
		final static String ROW_ID = KEY_ROW_ID;
		final static String VERSION_NAME = "version_name";

		/* version_table create query */
		final static String VERSION_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME
				+ "("
				+ ROW_ID
				+ " integer primary key , "
				+ VERSION_NAME + " text default '')";

		/* version_table drop query */
		final static String VERSION_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

	}

	/* Media Info table */
	public class Mediainfo {
		/* table name */
		final static String TABLE_NAME = "media_info";
		/* columns */
		final static String ROW_ID = KEY_ROW_ID;
		final static String MEDIA_TITLE = "title";
		final static String MEDIA_DESCRIPTION = "description";
		final static String MEDIA_THUMB = "thumbnail_path";
		final static String MEDIA_IMDB_ID = KEY_IMDB_ROW_ID;
		final static String MEDIA_CAT_ID = KEY_CATEGORY_ROW_ID;
		final static String SERIES_ID = KEY_SERIES_ROW_ID;
		final static String MEDIA_DURATION = "duration";
		/* media_table create query */
		final static String MEDIA_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME + "(" + ROW_ID + " integer primary key , "
				+ MEDIA_TITLE + " text, " + MEDIA_DESCRIPTION + " text, "
				+ MEDIA_THUMB + " text, " + MEDIA_IMDB_ID + " integer, "
				+ MEDIA_CAT_ID + " integer, " + SERIES_ID + " integer, "
				+ MEDIA_DURATION + " text)";

		/* media_table drop query */
		final static String MEDIA_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

	}

	/* Channel Info table */
	public class ChannelsInfo {
		/* table name */
		public final static String TABLE_NAME = "channel_info";

		/* columns */

		public final static String ROW_ID = KEY_ROW_ID;
		public final static String CHANNEL_NAME = "channel_name";
		public final static String IS_FAVORITE_CHANNEL = "is_favorite";


		/* channel_table create query */
		final static String CHANNEL_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME
				+ "("
				+ ROW_ID
				+ " integer primary key , "
				+ IS_FAVORITE_CHANNEL
				+ " integer default 0, "
				+ CHANNEL_NAME
				+ " text)";

		/* channel_table drop query */
		final static String CHANNEL_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/* Channel-Media Info table */
	public class ChannelMediaInfo {
		/* table name */
		public final static String TABLE_NAME = "channel_media_info";

		/* columns */

		public final static String MEDIA_ID = KEY_MEDIA_ROW_ID;
		public final static String CHANNEL_ID = KEY_CHANNEL_ROW_ID;
		public final static String AIR_TIME = "show_time";
		public final static String END_TIME = "end_time";

		/* channel_media_table create query */
		final static String CHANNEL_MEDIA_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME
				+ "("
				+ MEDIA_ID
				+ " integer primary key, "
				+ CHANNEL_ID
				+ " integer, "
				+ END_TIME
				+ " text, "
				+ AIR_TIME
				+ " text)";

		/* channel_media_table drop query */
		final static String CHANNEL_MEDIA_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

	}

	/* Category Info table */
	public class CategoriesInfo {
		/* table name */
		public final static String TABLE_NAME = "category_info";

		/* columns */


		public final static String ROW_ID = KEY_ROW_ID;
		public final static String CATAGORY_TYPE = "category_type";
		
		/* category_table create query */
		final static String CATEGORY_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME + "(" + ROW_ID + " integer primary key , "

				+ CATAGORY_TYPE + " text)";

		/* category_table drop query */
		final static String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/* Imdb Info table */
	public class ImdbInfo {
		/* table name */
		public final static String TABLE_NAME = "imdb_info";

		/* columns */

		public final static String ROW_ID = KEY_ROW_ID;
		public final static String IMDB_RATING = "rating";
		public final static String IMDB_LINK = "link";

		/* imdb_table create query */
		final static String IMDB_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME + "(" + ROW_ID
				+ " integer primary key autoincrement, " + IMDB_RATING
				+ " real, " + IMDB_LINK + " text  unique)";

		/* imdb_table drop query */
		final static String IMDB_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/* Reminders Info table */
	public class Remindersinfo {
		/* table name */
		public final static String TABLE_NAME = "reminders_info";

		/* columns */
		public final static String ROW_ID = KEY_ROW_ID;
		public final static String MEDIA_ID = KEY_MEDIA_ROW_ID;
		public final static String REMINDER_ENABLED = "reminder_flag";
		public final static String IS_FAVORITE_FLAG = "is_favorite";

		/* reminders_table create query */
		final static String REMINDER_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME
				+ "("
				+ ROW_ID
				+ " integer primary key autoincrement, "
				+ MEDIA_ID
				+ " integer, "
				+ REMINDER_ENABLED
				+ " integer, "
				+ IS_FAVORITE_FLAG + " integer)";

		/* reminder_table drop query */
		final static String REMINDER_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/* Series Info table */
	public class SeriesInfo {
		/* table name */
		public final static String TABLE_NAME = "series_info";
		/* columns */

		public final static String ROW_ID = KEY_ROW_ID;
		public final static String SERIES_NAME = "series_name";

		/* series_table create query */
		final static String SERIES_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME + "(" + ROW_ID + " integer primary key , "
				+ SERIES_NAME + " text)";

		/* series_table drop query */
		final static String SERIES_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

	}

}
