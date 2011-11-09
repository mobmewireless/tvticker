package in.mobme.tvticker.database;

import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.Models.CategoriesInfo;
import in.mobme.tvticker.database.Models.ChannelMediaInfo;
import in.mobme.tvticker.database.Models.ChannelsInfo;
import in.mobme.tvticker.database.Models.ImdbInfo;
import in.mobme.tvticker.database.Models.Mediainfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TvTickerDBAdapter {

	private TvTickerDBHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;
	ContentValues initialValues = null;
	ContentValues updateValues = null;
	Cursor mCursor = null;

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
		updateValues = new ContentValues();
		return this;
	}

	public void close() {
		initialValues.clear();
		if(mCursor != null){
			mCursor.close();
		}
		mDbHelper.close();
	}

	/** Insert a new Channel_wise_Media Entry.
	 * @return
	 *  Returns affected row id. */
	public long createNewMediaInfo(Media media) {
		initialValues.clear();
		long mediaId = insertMedia(media);
		initialValues.put(ChannelMediaInfo.MEDIA_ID, mediaId);
		initialValues.put(ChannelMediaInfo.CHANNEL_ID, media.getChannel());
		initialValues.put(ChannelMediaInfo.AIR_TIME, media.getShowTime());
		return mDb.insert(ChannelMediaInfo.TABLE_NAME, null, initialValues);
	}

	/** Insert a new Media Entry, invoked from createNewMediaInfo.
	 * @return
	 *  Returns affected row id. */
	private long insertMedia(Media media) {
		initialValues.clear();
		initialValues.put(Mediainfo.MEDIA_TITLE, media.getMediaTitle());
		initialValues.put(Mediainfo.MEDIA_DESCRIPTION, media.getMediaDescription());
		initialValues.put(Mediainfo.MEDIA_THUMB, media.getMediaThumb());
		initialValues.put(Mediainfo.IS_FAVORITE_FLAG, sanitiseIsFavorite(media.isFavorite()));
		initialValues.put(Mediainfo.MEDIA_CAT_ID, media.getCategoryType());
		long imdbId = insertImdbEntryFor(media.getImdbRating(), media.getImdbLink());
		initialValues.put(Mediainfo.MEDIA_IMDB_ID, imdbId);
		return mDb.insert(Mediainfo.TABLE_NAME, null, initialValues);
	}

	/** Insert a new Channel.
	 * @return
	 *  Returns affected row id. */
	public long insertNewChannel(String channelName) {
		initialValues.clear();
		initialValues.put(ChannelsInfo.CHANNEL_NAME, channelName);
		return mDb.insert(ChannelsInfo.TABLE_NAME, null, initialValues);
	}

	/** Insert a new Category. 
	 * @return
	 *  Returns affected row id.*/
	public long insertNewCategory(String category) {
		initialValues.clear();
		initialValues.put(CategoriesInfo.CATAGORY_TYPE, category);
		return mDb.insert(CategoriesInfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Insert Imdb related info for a particular movie invoked from
	 * insertMedia.
	 * @return
	 * Returns affected row id.
	 */
	private long insertImdbEntryFor(float rating, String reviewUrl) {
		initialValues.clear();
		initialValues.put(ImdbInfo.IMDB_RATING, rating);
		initialValues.put(ImdbInfo.IMDB_LINK, reviewUrl);
		return mDb.insert(ImdbInfo.TABLE_NAME, null, initialValues);
	}

	/** Update isFavorite, set to Mediainfo.IS_FAVORITE or Mediainfo.IS_NOT_FAVORITE 
	 * @return
	 *  Returns true or false, status of update
	 * */ 
	public boolean setIsFavorite(long mediaId, boolean isFavorite) {
		updateValues.clear();
		updateValues.put(Mediainfo.IS_FAVORITE_FLAG, sanitiseIsFavorite(isFavorite));
		return mDb.update(Mediainfo.TABLE_NAME, updateValues, Mediainfo.ROW_ID + "="
				+ mediaId, null) > 0;
	}
	
	/**
	 * Sanitises boolean to integer. 
	 * */
	private int sanitiseIsFavorite(boolean isFav){
		return isFav ? Mediainfo.IS_FAVORITE : Mediainfo.IS_NOT_FAVORITE;
	}
	
	/** Fetch Channel Name for a particular ID.
	 * @return
	 *  Returns channel name. */
	public String getChannelNameFor(long this_id){
		String channelName = null;
		mCursor = mDb.query(true, ChannelsInfo.TABLE_NAME, new String[] {
				ChannelsInfo.ROW_ID, ChannelsInfo.CHANNEL_NAME },
				ChannelsInfo.ROW_ID + "=" + this_id, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			channelName = mCursor.getString(mCursor.getColumnIndexOrThrow(ChannelsInfo.CHANNEL_NAME));
		}
		return channelName;	
	}
	
	/** Fetch Category Type for a particular ID.
	 * @return
	 *  Returns category name/type.*/
	public String getCategoryTypeFor(long this_id){
		String categoryType = null;
		mCursor = mDb.query(true, CategoriesInfo.TABLE_NAME, new String[] {
				CategoriesInfo.ROW_ID, CategoriesInfo.CATAGORY_TYPE},
				CategoriesInfo.ROW_ID + "=" + this_id, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			categoryType = mCursor.getString(mCursor.getColumnIndexOrThrow(CategoriesInfo.CATAGORY_TYPE));
		}
		return categoryType;
	}

}
