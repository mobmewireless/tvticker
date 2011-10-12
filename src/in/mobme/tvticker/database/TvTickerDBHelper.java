package in.mobme.tvticker.database;

import in.mobme.tvticker.database.Models.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TvTickerDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "TvTickerDataBaseTest";
	private static final int DATABASE_VERSION = 1;
	private Mediainfo mediaInfo;
	private ChannelsInfo channelsInfo;
	private CategoriesInfo categoryInfo;
	private ImdbInfo imdbInfo;
	private FavoritesInfo favoritesInfo;

	public TvTickerDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		getTableHelpers(new Models());
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		createTables(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(TvTickerDBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		dropTables(database);
		onCreate(database);
	}
	
	private void createTables(SQLiteDatabase database){
		database.execSQL(mediaInfo.MEDIA_TABLE_CREATE);
		database.execSQL(channelsInfo.CHANNEL_TABLE_CREATE);
		database.execSQL(categoryInfo.CATEGORY_TABLE_CREATE);
		database.execSQL(imdbInfo.IMDB_TABLE_CREATE);
		database.execSQL(favoritesInfo.FAVORITES_TABLE_CREATE);
	}
	
	private void dropTables( SQLiteDatabase database){
		database.execSQL(mediaInfo.MEDIA_TABLE_DROP);
		database.execSQL(channelsInfo.CHANNEL_TABLE_DROP);
		database.execSQL(categoryInfo.CATEGORY_TABLE_DROP);
		database.execSQL(imdbInfo.IMDB_TABLE_DROP);
		database.execSQL(favoritesInfo.FAVORITES_TABLE_DROP);
	}
	
	private void  getTableHelpers(Models model){
		mediaInfo = model.getMediaTableHelper();
		channelsInfo = model.getChannelTableHelper();
		categoryInfo = model.getCatagoriesTableHelper();
		imdbInfo = model.getImdbTableHelper();
		favoritesInfo = model.getFavoritesTableHelper();
	}

}