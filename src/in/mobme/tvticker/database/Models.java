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
		final static String REMOTE_VERSION_ID = "remote_version_id";

		/* version_table create query */
		final static String VERSION_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME
				+ "("
				+ ROW_ID
				+ " integer primary key autoincrement, "
				+ REMOTE_VERSION_ID
				+ " integer," + VERSION_NAME + " text default '')";

		/* version_table drop query */
		final static String VERSION_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

	}

	/* Media Info table */
	class Mediainfo {
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
		final static String REMOTE_MEDIA_ID = "remote_media_id";
		/* media_table create query */
		final static String MEDIA_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME + "(" + ROW_ID
				+ " integer primary key autoincrement, " + MEDIA_TITLE
				+ " text, " + MEDIA_DESCRIPTION + " text, " + MEDIA_THUMB
				+ " text, " + MEDIA_IMDB_ID + " integer, " + MEDIA_CAT_ID
				+ " integer, " + SERIES_ID + " integer, " + REMOTE_MEDIA_ID
				+ " integer," + MEDIA_DURATION + " text)";

		/* media_table drop query */
		final static String MEDIA_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

	}

	/* Channel Info table */
	class ChannelsInfo {
		/* table name */
		final static String TABLE_NAME = "channel_info";

		/* columns */
		final static String ROW_ID = KEY_ROW_ID;
		final static String CHANNEL_NAME = "channel_name";
		final static String IS_FAVORITE_CHANNEL = "is_favorite";
		final static String REMOTE_CHANNEL_ID = "remote_channel_id";

		/* channel_table create query */
		final static String CHANNEL_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME
				+ "("
				+ ROW_ID
				+ " integer primary key autoincrement, "
				+ IS_FAVORITE_CHANNEL
				+ " integer default 0, "
				+ CHANNEL_NAME
				+ " text "
				+ REMOTE_CHANNEL_ID + " integer)";

		/* channel_table drop query */
		final static String CHANNEL_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/* Channel-Media Info table */
	class ChannelMediaInfo {
		/* table name */
		final static String TABLE_NAME = "channel_media_info";

		/* columns */
		final static String ROW_ID = KEY_ROW_ID;
		final static String MEDIA_ID = KEY_MEDIA_ROW_ID;
		final static String CHANNEL_ID = KEY_CHANNEL_ROW_ID;
		final static String AIR_TIME = "show_time";
		final static String END_TIME = "end_time";

		/* channel_media_table create query */
		final static String CHANNEL_MEDIA_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME
				+ "("
				+ ROW_ID
				+ " integer primary key autoincrement, "
				+ MEDIA_ID
				+ " integer, "
				+ CHANNEL_ID
				+ " integer, "
				+ END_TIME
				+ " text, " + AIR_TIME + " text)";

		/* channel_media_table drop query */
		final static String CHANNEL_MEDIA_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

	}

	/* Category Info table */
	class CategoriesInfo {
		/* table name */
		final static String TABLE_NAME = "category_info";

		/* columns */
		final static String ROW_ID = KEY_ROW_ID;
		final static String CATAGORY_TYPE = "category_type";
		final static String REMOTE_CATEGORY_ID = "remote_category_id";

		/* category_table create query */
		final static String CATEGORY_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME
				+ "("
				+ ROW_ID
				+ " integer primary key autoincrement, "

				+ CATAGORY_TYPE + " text " + REMOTE_CATEGORY_ID + " integer)";

		/* category_table drop query */
		final static String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/* Imdb Info table */
	class ImdbInfo {
		/* table name */
		final static String TABLE_NAME = "imdb_info";

		/* columns */
		final static String ROW_ID = KEY_ROW_ID;
		final static String IMDB_RATING = "rating";
		final static String IMDB_LINK = "link";

		/* imdb_table create query */
		final static String IMDB_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME + "(" + ROW_ID
				+ " integer primary key autoincrement, " + IMDB_RATING
				+ " real, " + IMDB_LINK + " text )";

		/* imdb_table drop query */
		final static String IMDB_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/* Reminders Info table */
	class Remindersinfo {
		/* table name */
		final static String TABLE_NAME = "reminders_info";

		/* columns */
		final static String ROW_ID = KEY_ROW_ID;
		final static String MEDIA_ID = KEY_MEDIA_ROW_ID;
		final static String REMINDER_ENABLED = "reminder_flag";
		final static String IS_FAVORITE_FLAG = "is_favorite";

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
	class SeriesInfo {
		/* table name */
		final static String TABLE_NAME = "series_info";
		/* columns */
		final static String ROW_ID = KEY_ROW_ID;
		final static String SERIES_NAME = KEY_MEDIA_ROW_ID;

		/* series_table create query */
		final static String SERIES_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME + "(" + ROW_ID
				+ " integer primary key autoincrement, " + SERIES_NAME
				+ " text)";

		/* series_table drop query */
		final static String SERIES_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

	}

}
