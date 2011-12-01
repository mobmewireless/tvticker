package in.mobme.tvticker.database;

import in.mobme.tvticker.database.Models.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TvTickerDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "TvTickerDataBaseTest";
	private static final int DATABASE_VERSION = 1;


	public TvTickerDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		createTablesIn(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(TvTickerDBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		dropTablesFrom(database);
		onCreate(database);
	}
	
	private void createTablesIn(SQLiteDatabase database){
		database.execSQL(Mediainfo.MEDIA_TABLE_CREATE);
		database.execSQL(ChannelsInfo.CHANNEL_TABLE_CREATE);
		database.execSQL(CategoriesInfo.CATEGORY_TABLE_CREATE);
		database.execSQL(ImdbInfo.IMDB_TABLE_CREATE);
		database.execSQL(Remindersinfo.REMINDER_TABLE_CREATE);
		database.execSQL(SeriesInfo.SERIES_TABLE_CREATE);
		database.execSQL(ChannelMediaInfo.CHANNEL_MEDIA_TABLE_CREATE);
		Log.i(TvTickerDBHelper.class.getName(), "Successfully Created tables !");
	}
	
	private void dropTablesFrom( SQLiteDatabase database){
		database.execSQL(Mediainfo.MEDIA_TABLE_DROP);
		database.execSQL(ChannelsInfo.CHANNEL_TABLE_DROP);
		database.execSQL(CategoriesInfo.CATEGORY_TABLE_DROP);
		database.execSQL(ImdbInfo.IMDB_TABLE_DROP);
		database.execSQL(Remindersinfo.REMINDER_TABLE_DROP);
		database.execSQL(SeriesInfo.SERIES_TABLE_DROP);
		database.execSQL(ChannelMediaInfo.CHANNEL_MEDIA_TABLE_DROP);
		Log.i(TvTickerDBHelper.class.getName(), "Successfully dropped tables !");
	}

}