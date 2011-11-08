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
		final static String IS_FAVORITE = "is_favorite?";

		final static int TRUE = 1;
		final static int FALSE = 0;

		/* media_table create query */
		final static String MEDIA_TABLE_CREATE = "create table if not exists " + TABLE_NAME
				+ "(" + ROW_ID + " integer primary key autoincrement, "
				+ MEDIA_TITLE + " text, " + MEDIA_DESCRIPTION + " text, "
				+ MEDIA_THUMB + " text, " + MEDIA_IMDB_ID + " integer, "
				+ IS_FAVORITE + " integer, " + MEDIA_CAT_ID + " integer)";

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

		/* channel_table create query */
		final static String CHANNEL_TABLE_CREATE = "create table if not exists " + TABLE_NAME
				+ "(" + ROW_ID + " integer primary key autoincrement, "
				+ CHANNEL_NAME + " text )";

		/* channel_table drop query */
		final static String CHANNEL_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

	/* Channel-Media Info table */
	class ChannelMediaInfo {
		/* table name */
		final static String TABLE_NAME = "channel_media_info";

		/* columns */
		final static String ROW_ID = KEY_MEDIA_ROW_ID;
		final static String MEDIA_ID = KEY_ROW_ID;
		final static String CHANNEL_ID = KEY_CHANNEL_ROW_ID;
		final static String AIR_TIME = "show_time";

		/* channel_media_table create query */
		final static String CHANNEL_MEDIA_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME + "(" + ROW_ID
				+ " integer primary key autoincrement, " + MEDIA_ID
				+ " integer, " + CHANNEL_ID + " integer, " + AIR_TIME
				+ " text)";

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

		/* category_table create query */
		final static String CATEGORY_TABLE_CREATE = "create table if not exists "
				+ TABLE_NAME + "(" + ROW_ID
				+ " integer primary key autoincrement, " + CATAGORY_TYPE
				+ " text )";

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
		final static String IMDB_TABLE_CREATE = "create table if not exists " + TABLE_NAME
				+ "(" + ROW_ID + " integer primary key autoincrement, "
				+ IMDB_RATING + " real, " + IMDB_LINK + " text )";

		/* imdb_table drop query */
		final static String IMDB_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
	}

}
