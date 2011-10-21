package in.mobme.tvticker.database;

/**
 * Model definition for TvTickerDataBase
 * 
 * @author vishnu
 * 
 */
public class Models {

	/* Primary key definitions for each table */
	private final static String KEY_MEDIA_ROW_ID = "m_id";
	private final static String KEY_CHANNEL_ROW_ID = "ch_id";
	private final static String KEY_CATEGORY_ROW_ID = "cat_id";
	private final static String KEY_IMDB_ROW_ID = "imdb_id";
	private final static String KEY_FAV_ROW_ID = "fav_id";

	/* Media Info table */
	class Mediainfo {
		/* table name */
		final static String MEDIA_TABLE_NAME = "media_info";

		/* columns */
		final static String MEDIA_ID = KEY_MEDIA_ROW_ID;
		final static String MEDIA_TITLE = "title";
		final static String MEDIA_DESCRIPTION = "description";
		final static String MEDIA_SHOW_TIME = "show_time";
		final static String MEDIA_THUMB = "thumbnail_path";
		final static String MEDIA_IMDB_ID = KEY_IMDB_ROW_ID;

		/* media_table create query */
		final static String MEDIA_TABLE_CREATE = "create table " + MEDIA_TABLE_NAME
				+ "(" + MEDIA_ID + " integer primary key autoincrement, "
				+ MEDIA_TITLE + " text, " + MEDIA_DESCRIPTION + " text, "
				+ MEDIA_SHOW_TIME + " text, " + MEDIA_THUMB + " text, "
				+ MEDIA_IMDB_ID + " integer )";

		/* media_table drop query */
		final static String MEDIA_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ MEDIA_TABLE_NAME;

	}


	/* Channel Info table */
	class ChannelsInfo {
		/* table name */
		final static String CHANNEL_TABLE_NAME = "channel_info";

		/* columns */
		final static String CHANNEL_ID = KEY_CHANNEL_ROW_ID;
		final static String CHANNEL_NAME = "channel_name";

		/* channel_table create query */
		final static String CHANNEL_TABLE_CREATE = "create table "
				+ CHANNEL_TABLE_NAME + "(" + CHANNEL_ID
				+ " integer primary key autoincrement, " + CHANNEL_NAME
				+ " text )";

		/* channel_table drop query */
		final static String CHANNEL_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ CHANNEL_TABLE_NAME;
	}
	
	
	class ChannelMediaRelation{
		/* table name */
		final static String CHANNEL_TABLE_NAME = "channel_media_info";

		/* columns */
		final static String CHANNEL_ID = KEY_CHANNEL_ROW_ID;
		final static String CHANNEL_NAME = "channel_name";
	}
	
	/* Category Info table */
	class CategoriesInfo {
		/* table name */
		final static String CATEGORY_TABLE_NAME = "category_info";

		/* columns */
		final static String CATAGORY_ID = KEY_CATEGORY_ROW_ID;
		final static String CATAGORY_TYPE = "category_type";

		/* category_table create query */
		final static String CATEGORY_TABLE_CREATE = "create table "
				+ CATEGORY_TABLE_NAME + "(" + CATAGORY_ID
				+ " integer primary key autoincrement, " + CATAGORY_TYPE
				+ " text )";

		/* category_table drop query */
		final static String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ CATEGORY_TABLE_NAME;
	}


	/* Imdb Info table */
	class ImdbInfo {
		/* table name */
		final static String IMDB_TABLE_NAME = "imdb_info";

		/* columns */
		final static String IMDB_ID = KEY_IMDB_ROW_ID;
		final static String IMDB_RATING = "rating";
		final static String IMDB_LINK = "link";

		/* imdb_table create query */
		final static String IMDB_TABLE_CREATE = "create table " + IMDB_TABLE_NAME
				+ "(" + IMDB_ID + " integer primary key autoincrement, "
				+ IMDB_RATING + " real, " + IMDB_LINK + " text )";

		/* imdb_table drop query */
		final static String IMDB_TABLE_DROP = "DROP TABLE IF EXISTS " + IMDB_TABLE_NAME;
	}

	/* Favorites table */
	class FavoritesInfo {
		/* table name */
		final static String FAVORITES_TABLE_NAME = "favorite_info";

		/* columns */
		final static String FAV_ID = KEY_FAV_ROW_ID;
		final static String FAV_MEDIA_ID = KEY_MEDIA_ROW_ID;

		/* favorites_table create query */
		final static String FAVORITES_TABLE_CREATE = "create table "
				+ FAVORITES_TABLE_NAME + "(" + FAV_ID
				+ " integer primary key autoincrement, " + FAV_MEDIA_ID
				+ " text )";

		/* favorites_table drop query */
		final static String FAVORITES_TABLE_DROP = "DROP TABLE IF EXISTS "
				+ FAVORITES_TABLE_CREATE;

	}


}
