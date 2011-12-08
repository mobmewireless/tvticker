package in.mobme.tvticker.database;

import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.Models.CategoriesInfo;
import in.mobme.tvticker.database.Models.ChannelMediaInfo;
import in.mobme.tvticker.database.Models.ChannelsInfo;
import in.mobme.tvticker.database.Models.ImdbInfo;
import in.mobme.tvticker.database.Models.Mediainfo;
import in.mobme.tvticker.database.Models.Remindersinfo;

import java.util.ArrayList;

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
	ArrayList<Media> mediaList = new ArrayList<Media>();

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
		if (mCursor != null) {
			mCursor.close();
		}
		mDbHelper.close();
	}

	/**
	 * Insert a new Channel_wise_Media Entry.
	 * 
	 * @return Returns affected row id.
	 */
	public long createNewMediaInfo(Media media) {
		long mediaId = insertMedia(media);
		initialValues.clear();
		initialValues.put(ChannelMediaInfo.MEDIA_ID, mediaId);
		initialValues.put(ChannelMediaInfo.CHANNEL_ID, media.getChannel());
		initialValues.put(ChannelMediaInfo.AIR_TIME, media.getShowTime());
		initialValues.put(ChannelMediaInfo.END_TIME, media.getShowEndTime());
		return mDb.insert(ChannelMediaInfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Return a list of all media in the ChannelMediaInfoTable.
	 * 
	 * @return List of all media
	 * */
	public ArrayList<Media> fetchAllMediaInfo() {
		Media media = null;
		mCursor = mDb.query(ChannelMediaInfo.TABLE_NAME, new String[] {
				ChannelMediaInfo.ROW_ID, ChannelMediaInfo.MEDIA_ID,
				ChannelMediaInfo.CHANNEL_ID, ChannelMediaInfo.AIR_TIME,
				ChannelMediaInfo.END_TIME }, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			while (mCursor.isAfterLast() == false) {
				long media_id = mCursor.getLong(mCursor
						.getColumnIndexOrThrow(ChannelMediaInfo.MEDIA_ID));
				media = fetchMediaFor(media_id);
				media.setId(media_id);
				media.setChannel(mCursor.getInt(mCursor
						.getColumnIndexOrThrow(ChannelMediaInfo.CHANNEL_ID)));
				media.setShowTime(mCursor.getString(mCursor
						.getColumnIndexOrThrow(ChannelMediaInfo.AIR_TIME)));
				media.setShowEndTime(mCursor.getString(mCursor
						.getColumnIndexOrThrow(ChannelMediaInfo.END_TIME)));
				mediaList.add(media);
				mCursor.moveToNext();
			}
		}
		return mediaList;

	}

	/**
	 * Insert a new Media Entry, invoked from createNewMediaInfo.
	 * 
	 * @return Returns affected row id.
	 */
	private long insertMedia(Media media) {
		long imdbId = insertImdbEntryFor(media.getImdbRating(), media
				.getImdbLink());
		initialValues.clear();
		initialValues.put(Mediainfo.MEDIA_TITLE, media.getMediaTitle());
		initialValues.put(Mediainfo.MEDIA_DESCRIPTION, media
				.getMediaDescription());
		initialValues.put(Mediainfo.MEDIA_THUMB, media.getMediaThumb());
		initialValues.put(Mediainfo.IS_FAVORITE_FLAG,
				sanitiseBooleanToInteger(media.isFavorite()));
		initialValues.put(Mediainfo.MEDIA_CAT_ID, media.getCategoryType());
		initialValues.put(Mediainfo.MEDIA_DURATION, media.getShowDuration());
		initialValues.put(Mediainfo.SERIES_ID, media.getSeriesID());
		initialValues.put(Mediainfo.MEDIA_IMDB_ID, imdbId);
		return mDb.insert(Mediainfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Fetch Media Object for a particular ID.
	 * 
	 * @return Returns Media.
	 */
	private Media fetchMediaFor(long this_id) {
		Media media = null;
		Cursor tmpCursor = mDb.query(true, Mediainfo.TABLE_NAME, new String[] {
				Mediainfo.ROW_ID, Mediainfo.MEDIA_TITLE,
				Mediainfo.MEDIA_DESCRIPTION, Mediainfo.MEDIA_THUMB,
				Mediainfo.IS_FAVORITE_FLAG, Mediainfo.MEDIA_CAT_ID,
				Mediainfo.MEDIA_IMDB_ID, Mediainfo.MEDIA_DURATION,
				Mediainfo.SERIES_ID }, Mediainfo.ROW_ID + "=" + this_id, null,
				null, null, null, null);
		if (tmpCursor != null) {
			media = getMediaFromCursor(tmpCursor);
			tmpCursor.close();
		}
		return media;
	}

	/**
	 * Get Media Object from the given cursor.
	 * 
	 * @param cursor
	 * @return Media Object
	 */
	private Media getMediaFromCursor(Cursor cursor) {
		Media media = new Media();
		cursor.moveToFirst();
		media.setMediaTitle(cursor.getString(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_TITLE)));
		media.setMediaDescription(cursor.getString(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_DESCRIPTION)));
		media.setMediaThumb(cursor.getString(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_THUMB)));
		media.setIsFavorite(sanitiseIntegerToBoolean(cursor.getInt(cursor
				.getColumnIndexOrThrow(Mediainfo.IS_FAVORITE_FLAG))));
		media.setCategoryType(cursor.getInt(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_CAT_ID)));
		media.setShowDuration(cursor.getString(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_DURATION)));
		media.setSeriesID(cursor.getInt(cursor
				.getColumnIndexOrThrow(Mediainfo.SERIES_ID)));

		// get imdb details of this media from IMDBInfo table.
		Cursor tmpCursor = getImdbDetailsFor(cursor.getInt(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_IMDB_ID)));
		tmpCursor.moveToFirst();
		media.setImdbRating(tmpCursor.getFloat(tmpCursor
				.getColumnIndexOrThrow(ImdbInfo.IMDB_RATING)));
		media.setImdbLink(tmpCursor.getString(tmpCursor
				.getColumnIndexOrThrow(ImdbInfo.IMDB_LINK)));
		tmpCursor.close();
		cursor.close();
		return media;
	}

	/**
	 * Insert a new Channel.
	 * 
	 * @return Returns affected row id.
	 */
	public long insertNewChannel(String channelName) {
		initialValues.clear();
		initialValues.put(ChannelsInfo.CHANNEL_NAME, channelName);
		return mDb.insert(ChannelsInfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Insert a new Category.
	 * 
	 * @return Returns affected row id.
	 */
	public long insertNewCategory(String category) {
		initialValues.clear();
		initialValues.put(CategoriesInfo.CATAGORY_TYPE, category);
		return mDb.insert(CategoriesInfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Insert Imdb related info for a particular movie invoked from insertMedia.
	 * 
	 * @return Returns affected row id.
	 */
	private long insertImdbEntryFor(float rating, String reviewUrl) {
		initialValues.clear();
		initialValues.put(ImdbInfo.IMDB_RATING, rating);
		initialValues.put(ImdbInfo.IMDB_LINK, reviewUrl);
		return mDb.insert(ImdbInfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Fetch imdb details for a particular ID.
	 * 
	 * @return Returns cursor over Imdb details.
	 */
	public Cursor getImdbDetailsFor(long this_id) {
		return mDb.query(true, ImdbInfo.TABLE_NAME, new String[] {
				ImdbInfo.ROW_ID, ImdbInfo.IMDB_RATING, ImdbInfo.IMDB_LINK },
				Mediainfo.ROW_ID + "=" + this_id, null, null, null, null, null);

	}

	/**
	 * Update isFavorite under MediaInfo table, set to Mediainfo.IS_FAVORITE or
	 * Mediainfo.IS_NOT_FAVORITE
	 * 
	 * @return Returns true or false, status of update
	 * */
	public boolean setIsFavorite(long mediaId, boolean isFavorite,
			boolean isReminderEnabled) {
		// isReminderEnabled == true => user wants it to be
		// reminded. so..
		setReminderFor(mediaId, isReminderEnabled);
		updateValues.clear();
		updateValues.put(Mediainfo.IS_FAVORITE_FLAG,
				sanitiseBooleanToInteger(isFavorite));
		return mDb.update(Mediainfo.TABLE_NAME, updateValues, Mediainfo.ROW_ID
				+ "=" + mediaId, null) > 0;
	}

	/**
	 * Creates a new reminder entry for given media Id.
	 * 
	 * @return Returns affected row id.
	 */
	private long setReminderFor(long mediaId, boolean reminderEnabled) {
		updateValues.clear();
		updateValues.put(Remindersinfo.MEDIA_ID, mediaId);
		updateValues.put(Remindersinfo.REMINDER_ENABLED,
				sanitiseBooleanToInteger(reminderEnabled));
		return mDb.insert(Remindersinfo.TABLE_NAME, null, updateValues);
	}

	/**
	 * Enable/Disable Reminder for a particular media id.
	 * 
	 * @return Returns true or false, status of update
	 * */
	public boolean enableOrDisableReminderFor(long mediaId,
			boolean reminderEnabled) {
		updateValues.clear();
		updateValues.put(Remindersinfo.REMINDER_ENABLED,
				sanitiseBooleanToInteger(reminderEnabled));
		return mDb.update(Remindersinfo.TABLE_NAME, updateValues,
				Remindersinfo.ROW_ID + "=" + mediaId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all shows in the ReminderInfo Table.
	 * 
	 * @return Cursor over all shows
	 * */
	public Cursor fetchAllFromReminderInfo() {
		return mDb.query(Remindersinfo.TABLE_NAME, new String[] {
				Remindersinfo.ROW_ID, Remindersinfo.MEDIA_ID,
				Remindersinfo.REMINDER_ENABLED }, null, null, null, null, null);
	}

	/**
	 * Return a Cursor over the list of all Channels from the Channels Table.
	 * 
	 * @return Cursor over all channels
	 * */
	public Cursor fetchAllChannels() {
		return mDb.query(ChannelsInfo.TABLE_NAME, new String[] {
				ChannelsInfo.ROW_ID, ChannelsInfo.CHANNEL_NAME }, null, null,
				null, null, null);
	}

	/**
	 * Return a Cursor over the list of all Categories from the Categories
	 * Table.
	 * 
	 * @return Cursor over all Categories.
	 * */
	public Cursor fetchAllCategories() {
		return mDb.query(CategoriesInfo.TABLE_NAME, new String[] {
				CategoriesInfo.ROW_ID, CategoriesInfo.CATAGORY_TYPE }, null,
				null, null, null, null);

	}

	/**
	 * Sanitises boolean to integer.
	 * */
	private int sanitiseBooleanToInteger(boolean isTrue) {
		return isTrue ? Models.IS_TRUE : Models.IS_FALSE;
	}

	/**
	 * Sanitises integer to boolean.
	 * */
	private boolean sanitiseIntegerToBoolean(int isTrue) {
		return isTrue == Models.IS_TRUE ? true : false;
	}

	/**
	 * Fetch Channel Name for a particular ID.
	 * 
	 * @return Returns channel name.
	 */
	public String getChannelNameFor(long this_id) {
		String channelName = null;
		mCursor = mDb.query(true, ChannelsInfo.TABLE_NAME, new String[] {
				ChannelsInfo.ROW_ID, ChannelsInfo.CHANNEL_NAME },
				ChannelsInfo.ROW_ID + "=" + this_id, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			channelName = mCursor.getString(mCursor
					.getColumnIndexOrThrow(ChannelsInfo.CHANNEL_NAME));
		}
		return channelName;
	}

	/**
	 * Fetch Category Type for a particular ID.
	 * 
	 * @return Returns category name/type.
	 */
	public String getCategoryTypeFor(long this_id) {
		String categoryType = null;
		mCursor = mDb.query(true, CategoriesInfo.TABLE_NAME, new String[] {
				CategoriesInfo.ROW_ID, CategoriesInfo.CATAGORY_TYPE },
				CategoriesInfo.ROW_ID + "=" + this_id, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			categoryType = mCursor.getString(mCursor
					.getColumnIndexOrThrow(CategoriesInfo.CATAGORY_TYPE));
		}
		return categoryType;
	}

}
