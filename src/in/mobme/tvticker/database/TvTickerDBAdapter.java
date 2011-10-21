package in.mobme.tvticker.database;

import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.Models.Mediainfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TvTickerDBAdapter {

	private TvTickerDBHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;
	ContentValues initialValues = null;

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	public TvTickerDBAdapter(Context ctx) {
		this.mCtx = ctx;
		
	}

	/**
	 * Open the user database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	public TvTickerDBAdapter open() throws SQLException {
		mDbHelper = new TvTickerDBHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		initialValues = new ContentValues();
		return this;
	}

	public void close() {
		initialValues.clear();
		mDbHelper.close();
	}

	public void insertMedia(Media media) {
		initialValues.clear();
		initialValues.put(Mediainfo.MEDIA_TITLE, media.getMediaTitle());
		initialValues.put(Mediainfo.MEDIA_DESCRIPTION, media.getMediaDescription());
		initialValues.put(Mediainfo.MEDIA_THUMB, media.getMediaThumb());
	}

}
