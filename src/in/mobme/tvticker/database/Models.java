package in.mobme.tvticker.database;

/**
 * Model definition for TvTickerDataBase
 * 
 * @author vishnu
 * 
 */
public class Models {

	/* Primary key definitions for each table */
	private final String KEY_MEDIA_ROW_ID = "_id";
	private final String KEY_CHANNEL_ROW_ID = "ch_id";
	private final String KEY_CATEGORY_ROW_ID = "cat_id";
	private final String KEY_IMDB_ROW_ID = "imdb_id";
	private final String KEY_FAV_ROW_ID = "fav_id";

	/* Media Info table */
	class Mediainfo {
		/* table name */
		final String MEDIA_TABLE_NAME = "media_info";

		/* columns */
		final String MEDIA_ID = KEY_MEDIA_ROW_ID;
		final String MEDIA_TITLE = "title";
		final String MEDIA_DESCRIPTION = "description";
		final String MEDIA_SHOW_TIME = "show_time";
		final String MEDIA_THUMB = "thumbnail_path";
		final String MEDIA_CHANNEL_ID = KEY_CHANNEL_ROW_ID;
		final String MEDIA_CATEGORY_ID = KEY_CATEGORY_ROW_ID;
		final String MEDIA_IMDB_ID = KEY_IMDB_ROW_ID;

		/* media_table create query */
		final String MEDIA_TABLE_CREATE = "create table " + MEDIA_TABLE_NAME
				+ "(" + MEDIA_ID + " integer primary key autoincrement, "
				+ MEDIA_TITLE + " text, " + MEDIA_DESCRIPTION + " text, "
				+ MEDIA_SHOW_TIME + " text, " + MEDIA_THUMB + " text, "
				+ MEDIA_CHANNEL_ID + " integer, " + MEDIA_CATEGORY_ID
				+ " integer, " + MEDIA_IMDB_ID + " integer )";

		/* media_table drop query */
		final String MEDIA_TABLE_DROP = "DROP TABLE IF EXISTS"
				+ MEDIA_TABLE_NAME;

	}

	public Mediainfo getMediaTableHelper() {
		return new Mediainfo();
	}

	/* Channel Info table */
	class ChannelsInfo {
		/* table name */
		final String CHANNEL_TABLE_NAME = "channel_info";

		/* columns */
		final String CHANNEL_ID = KEY_CHANNEL_ROW_ID;
		final String CHANNEL_NAME = "channel_name";

		/* channel_table create query */
		final String CHANNEL_TABLE_CREATE = "create table "
				+ CHANNEL_TABLE_NAME + "(" + CHANNEL_ID
				+ " integer primary key autoincrement, " + CHANNEL_NAME
				+ " text )";

		/* channel_table drop query */
		final String CHANNEL_TABLE_DROP = "DROP TABLE IF EXISTS"
				+ CHANNEL_TABLE_NAME;
	}

	public ChannelsInfo getChannelTableHelper() {
		return new ChannelsInfo();
	}

	/* Category Info table */
	class CategoriesInfo {
		/* table name */
		final String CATEGORY_TABLE_NAME = "category_info";

		/* columns */
		final String CATAGORY_ID = KEY_CATEGORY_ROW_ID;
		final String CATAGORY_TYPE = "category_type";

		/* category_table create query */
		final String CATEGORY_TABLE_CREATE = "create table "
				+ CATEGORY_TABLE_NAME + "(" + CATAGORY_ID
				+ " integer primary key autoincrement, " + CATAGORY_TYPE
				+ " text )";

		/* category_table drop query */
		final String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS"
				+ CATEGORY_TABLE_NAME;
	}

	public CategoriesInfo getCatagoriesTableHelper() {
		return new CategoriesInfo();
	}

	/* Imdb Info table */
	class ImdbInfo {
		/* table name */
		final String IMDB_TABLE_NAME = "imdb_info";

		/* columns */
		final String IMDB_ID = KEY_IMDB_ROW_ID;
		final String IMDB_RATING = "rating";
		final String IMDB_LINK = "link";

		/* imdb_table create query */
		final String IMDB_TABLE_CREATE = "create table " + IMDB_TABLE_NAME
				+ "(" + IMDB_ID + " integer primary key autoincrement, "
				+ IMDB_RATING + " real, " + IMDB_LINK + " text )";

		/* imdb_table drop query */
		final String IMDB_TABLE_DROP = "DROP TABLE IF EXISTS" + IMDB_TABLE_NAME;
	}

	public ImdbInfo getImdbTableHelper() {
		return new ImdbInfo();
	}

	/* Favorites table */
	class FavoritesInfo {
		/* table name */
		final String FAVORITES_TABLE_NAME = "favorite_info";

		/* columns */
		final String FAV_ID = KEY_FAV_ROW_ID;
		final String FAV_MEDIA_ID = KEY_MEDIA_ROW_ID;

		/* favorites_table create query */
		final String FAVORITES_TABLE_CREATE = "create table "
				+ FAVORITES_TABLE_NAME + "(" + FAV_ID
				+ " integer primary key autoincrement, " + FAV_MEDIA_ID
				+ " text )";

		/* favorites_table drop query */
		final String FAVORITES_TABLE_DROP = "DROP TABLE IF EXISTS"
				+ FAVORITES_TABLE_CREATE;

	}

	public FavoritesInfo getFavoritesTableHelper() {
		return new FavoritesInfo();
	}

}
