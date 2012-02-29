package in.mobme.tvticker.database;

import in.mobme.tvticker.Constants;
import in.mobme.tvticker.FavouriteChannelModel;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.data_model.SearchableMedia;
import in.mobme.tvticker.database.Models.CategoriesInfo;
import in.mobme.tvticker.database.Models.ChannelMediaInfo;
import in.mobme.tvticker.database.Models.ChannelsInfo;
import in.mobme.tvticker.database.Models.ImdbInfo;
import in.mobme.tvticker.database.Models.Mediainfo;
import in.mobme.tvticker.database.Models.Remindersinfo;
import in.mobme.tvticker.database.Models.Version;
import in.mobme.tvticker.helpers.DateTimeHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

public class TvTickerDBAdapter {

	private TvTickerDBHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;
	Cursor mCursor = null;
	DateTimeHelper dateTimeHelper = null;
	private final int DEFAULT_WAIT_TIME = 1000;

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
		dateTimeHelper = new DateTimeHelper();
		return this;
	}

	// clean everything !
	public void close() {
		safelyCloseMCursor();
		mDbHelper.close();
	}

	private void safelyCloseMCursor() {
		if (mCursor != null) {
			mCursor.deactivate();
			mCursor.close();
		}
	}

	public void checkForDBLockRelease(int waitTime){
		while (mDb.isDbLockedByOtherThreads()) {
			try {
				Thread.sleep(waitTime);
				Log.i("TvTicker DbAdapter", "Waiting for DB lock to be released");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
	}
	/**************************
	 * Channel Media table
	 */

	/**
	 * Insert a new Channel_wise_Media Entry.
	 * 
	 * @return Returns affected row id.
	 */
	public long createNewMediaInfo(Media media) {
		checkForDBLockRelease(DEFAULT_WAIT_TIME);
		long mediaId = insertMedia(media);
		String showTimeStart = "";
		String showTimeEnd = "";

		try {
			showTimeStart = dateTimeHelper
					.SanitizeJsonTime(media.getShowTime());
			showTimeEnd = dateTimeHelper.SanitizeJsonTime(media
					.getShowEndTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(Constants.TAG, e.toString());
		}

		ContentValues initialValues = new ContentValues();
		initialValues.put(ChannelMediaInfo.MEDIA_ID, mediaId);
		initialValues.put(ChannelMediaInfo.CHANNEL_ID, media.getChannel());
		initialValues.put(ChannelMediaInfo.AIR_TIME, showTimeStart);
		initialValues.put(ChannelMediaInfo.END_TIME, showTimeEnd);
		return mDb.replace(ChannelMediaInfo.TABLE_NAME, null, initialValues);
		

	}

	// private where clause helper for now and later frames
	private String getWhereConstruct() {
		// air_time_start between :air_time_start and :air_time_end and
		// air_time_end > :end_time"
		ArrayList<Long> favChannelIds = fetchFavChannelIds();

		Log.i("query",
				ChannelMediaInfo.AIR_TIME + " between ? and ? and "
						+ ChannelMediaInfo.END_TIME + " > ? and "
						+ ChannelMediaInfo.CHANNEL_ID + " in("
						+ TextUtils.join(", ", favChannelIds) + ") order by "
						+ ChannelMediaInfo.AIR_TIME);
		if (favChannelIds.size() == 0)
			return ChannelMediaInfo.AIR_TIME
					+ " between ? and ? and end_time > ? order by "
					+ ChannelMediaInfo.AIR_TIME;
		else {

			return ChannelMediaInfo.AIR_TIME + " between ? and ? and "
					+ ChannelMediaInfo.END_TIME + " > ? and "
					+ ChannelMediaInfo.CHANNEL_ID + " in("
					+ TextUtils.join(", ", favChannelIds) + ") order by "
					+ ChannelMediaInfo.AIR_TIME;
		}
	}

	/**
	 * Return a list of all media in the ChannelMediaInfoTable for now frame.
	 * 
	 * @return List of all media
	 * */
	public ArrayList<Media> fetchShowsforNowFrame() {
		String[] timeFrame = dateTimeHelper
				.getStringTimeFrameFor(DateTimeHelper.FRAME_NOW);
		return fetchAllShowsFor(getWhereConstruct(), timeFrame);

	}

	/**
	 * Return a list of all media in the ChannelMediaInfoTable for later frame.
	 * 
	 * @return List of all media
	 * */

	public ArrayList<Media> fetchShowsforLaterFrame() {
		String[] timeFrame = dateTimeHelper
				.getStringTimeFrameFor(DateTimeHelper.FRAME_LATER);
		return fetchAllShowsFor(getWhereConstruct(), timeFrame);
	}

	/**
	 * Return all media from the ChannelMediaInfoTable.
	 * 
	 * @return list of media (ArrayList)
	 * */

	public ArrayList<Media> fetchAllShowsInfo() {
		return fetchAllShowsFor(null, null);
	}

	/**
	 * Return all media from the ChannelMediaInfoTable for the give channel ID.
	 * 
	 * @return list of media (ArrayList)
	 * */
	public ArrayList<Media> fetchAllShowsForChannel(String channelId) {
		String whereClause = ChannelMediaInfo.CHANNEL_ID + " = " + channelId;
		return fetchAllShowsFor(whereClause, null);
	}

	/**
	 * Return all media from the ChannelMediaInfoTable which passes the given
	 * whereClause.
	 * 
	 * @return list of media (ArrayList)
	 * */
	public ArrayList<Media> fetchAllShowsFor(String whereClause,
			String[] whereArgs) {
		// flush old stuff from mediaList
		ArrayList<Media> mediaList = new ArrayList<Media>();

		mCursor = mDb.query(ChannelMediaInfo.TABLE_NAME, new String[] {
				ChannelMediaInfo.MEDIA_ID, ChannelMediaInfo.CHANNEL_ID,
				ChannelMediaInfo.AIR_TIME, ChannelMediaInfo.END_TIME },
				whereClause, whereArgs, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
			while (!mCursor.isAfterLast()) {
				mediaList.add(unWrapShowDataFrom(mCursor));
				mCursor.moveToNext();
			}
		}
		safelyCloseMCursor();
		return mediaList;
	}

	public ArrayList<Media> fetchAllShowsForCategory(String categoryId) {

		ArrayList<Media> mediaList = new ArrayList<Media>();
		String whereClause = Mediainfo.MEDIA_CAT_ID + "=" + categoryId;

		Cursor cursor = fetchMediaForCondition(whereClause);

		if (cursor != null) {
			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				Media media = getMediaFromCursor(cursor);
				// get channel details of this media.
				String channelWhereClause = ChannelMediaInfo.MEDIA_ID
						+ "="
						+ cursor.getString(cursor
								.getColumnIndexOrThrow(Mediainfo.ROW_ID));
				Cursor channelCursor = fetchShowsInfoForCondition(channelWhereClause);
				channelCursor.moveToFirst();
				media.setChannel(channelCursor.getInt(channelCursor
						.getColumnIndex(ChannelMediaInfo.CHANNEL_ID)));
				media.setShowTime(channelCursor.getString(channelCursor
						.getColumnIndex(ChannelMediaInfo.AIR_TIME)));
				media.setShowEndTime(channelCursor.getString(channelCursor
						.getColumnIndex(ChannelMediaInfo.END_TIME)));
				channelCursor.close();

				mediaList.add(media);
				cursor.moveToNext();
			}
			cursor.close();
		}

		return mediaList;
	}

	/**
	 * Return a media from the ChannelMediaInfoTable for given media_id.
	 * 
	 * @return media
	 * */
	public Media fetchShowsInfoFor(long this_media_id) {
		Media media = null;
		String whereClause = ChannelMediaInfo.MEDIA_ID + "=" + this_media_id;
		mCursor = fetchShowsInfoForCondition(whereClause);
		if (mCursor != null) {
			mCursor.moveToFirst();
			media = unWrapShowDataFrom(mCursor);
		}
		safelyCloseMCursor();
		return media;
	}

	private Cursor fetchShowsInfoForCondition(String whereClause) {
		return mDb.query(ChannelMediaInfo.TABLE_NAME, new String[] {
				ChannelMediaInfo.MEDIA_ID, ChannelMediaInfo.CHANNEL_ID,
				ChannelMediaInfo.AIR_TIME, ChannelMediaInfo.END_TIME },
				whereClause, null, null, null, null);
	}

	/************************
	 * Media Table
	 */

	/**
	 * Looks up media table for movies of given pattern.
	 * 
	 * @return List of all media title matching.
	 * */
	public ArrayList<SearchableMedia> getShowsLike(String pattern) {
		SearchableMedia searcchableMedia;
		ArrayList<SearchableMedia> matchedList = new ArrayList<SearchableMedia>();
		Cursor tmpCursor = mDb.query(Mediainfo.TABLE_NAME, new String[] {
				Mediainfo.ROW_ID, Mediainfo.MEDIA_TITLE },
				Mediainfo.MEDIA_TITLE + " like '" + pattern + "%'", null, null,
				null, null);

		if (tmpCursor == null) {
			return null;
		} else {
			tmpCursor.moveToFirst();
			while (!tmpCursor.isAfterLast()) {
				searcchableMedia = new SearchableMedia();
				searcchableMedia.setMedia_id(tmpCursor.getInt(tmpCursor
						.getColumnIndexOrThrow(Mediainfo.ROW_ID)));
				searcchableMedia.setMedia_title(tmpCursor.getString(tmpCursor
						.getColumnIndexOrThrow(Mediainfo.MEDIA_TITLE)));
				searcchableMedia.setType(SearchableMedia.TYPE_SHOW);
				matchedList.add(searcchableMedia);
				tmpCursor.moveToNext();
			}
			tmpCursor.close();
			return matchedList;
		}

	}

	/**
	 * Insert a new Media Entry, invoked from createNewMediaInfo.
	 * 
	 * @return Returns affected row id.
	 */
	private long insertMedia(Media media) {
		long imdbId = insertImdbEntryFor(media.getImdbRating(),
				media.getImdbLink());
		ContentValues initialValues = new ContentValues();
		initialValues.put(Mediainfo.ROW_ID, media.getId());
		initialValues.put(Mediainfo.MEDIA_TITLE, media.getMediaTitle());
		initialValues.put(Mediainfo.MEDIA_DESCRIPTION,
				media.getMediaDescription());
		initialValues.put(Mediainfo.MEDIA_THUMB, media.getMediaThumb());
		initialValues.put(Mediainfo.MEDIA_CAT_ID, media.getCategoryType());
		initialValues.put(Mediainfo.MEDIA_DURATION, media.getShowDuration());
		initialValues.put(Mediainfo.SERIES_ID, media.getSeriesID());
		initialValues.put(Mediainfo.MEDIA_IMDB_ID, imdbId);
		return mDb.replace(Mediainfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Fetch Media Object for a particular ID.
	 * 
	 * @return Returns Media.
	 */
	private Media fetchMediaFor(long this_id) {
		Media media = null;
		String whereClause = Mediainfo.ROW_ID + "=" + this_id;
		Cursor tmpCursor = fetchMediaForCondition(whereClause);
		if (tmpCursor != null) {
			tmpCursor.moveToFirst();
			media = getMediaFromCursor(tmpCursor);
			tmpCursor.close();
		}
		return media;
	}

	private Cursor fetchMediaForCondition(String whereClause) {
		return mDb.query(true, Mediainfo.TABLE_NAME, new String[] {
				Mediainfo.ROW_ID, Mediainfo.MEDIA_TITLE,
				Mediainfo.MEDIA_DESCRIPTION, Mediainfo.MEDIA_THUMB,
				Mediainfo.MEDIA_CAT_ID, Mediainfo.MEDIA_IMDB_ID,
				Mediainfo.MEDIA_DURATION, Mediainfo.SERIES_ID }, whereClause,
				null, null, null, null, null);
	}

	/***************************
	 * Channels Table
	 */

	/**
	 * Insert a new Channel.
	 * 
	 * @return Returns affected row id.
	 */
	public long insertNewChannel(long remote_channel_id, String channelName) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(ChannelsInfo.CHANNEL_NAME, channelName);
		initialValues.put(ChannelsInfo.ROW_ID, remote_channel_id);

		return mDb.insert(ChannelsInfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Insert a new Channel.Updates the row if id already exists.
	 * 
	 * @return Returns affected row id.
	 */
	public long updateOrInsertChannel(long remote_channel_id, String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(ChannelsInfo.CHANNEL_NAME, name);
		initialValues.put(ChannelsInfo.ROW_ID, remote_channel_id);
		return mDb.replace(ChannelsInfo.TABLE_NAME, "empty", initialValues);
	}

	/**
	 * Return a Cursor over the list of all Channels from the Channels Table.
	 * 
	 * @return Cursor over all channels
	 * */
	public Cursor fetchAllChannels() {
		return mDb.query(ChannelsInfo.TABLE_NAME, new String[] {
				ChannelsInfo.ROW_ID, ChannelsInfo.CHANNEL_NAME,
				ChannelsInfo.IS_FAVORITE_CHANNEL }, null, null, null, null,
				null);
	}

	public ArrayList<Long> fetchFavChannelIds() {

		mCursor = mDb.query(ChannelsInfo.TABLE_NAME,
				new String[] { ChannelsInfo.ROW_ID },
				ChannelsInfo.IS_FAVORITE_CHANNEL + "= 1", null, null, null,
				null);
		ArrayList<Long> ids = new ArrayList<Long>();
		if (mCursor != null) {
			mCursor.moveToFirst();
			while (!mCursor.isAfterLast()) {
				ids.add(mCursor.getLong(mCursor
						.getColumnIndexOrThrow(ChannelsInfo.ROW_ID)));
				mCursor.moveToNext();
			}
			mCursor.close();
		}
		return ids;

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
			mCursor.close();
		}

		return channelName;
	}

	/**************************
	 * Category table
	 */

	/**
	 * Insert a new Category.
	 * 
	 * @return Returns affected row id.
	 */
	public long insertNewCategory(long remote_category_id, String category) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(CategoriesInfo.CATAGORY_TYPE, category);
		initialValues.put(CategoriesInfo.ROW_ID, remote_category_id);
		return mDb.insert(CategoriesInfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Insert a new Category or updates existing category.
	 * 
	 * @return Returns affected row id.
	 */
	public long updateOrInsertCategory(long remote_category_id, String name) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(CategoriesInfo.CATAGORY_TYPE, name);
		initialValues.put(CategoriesInfo.ROW_ID, remote_category_id);
		return mDb.replace(CategoriesInfo.TABLE_NAME, "empty", initialValues);
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
			mCursor.close();
		}
		return categoryType.substring(0, categoryType.indexOf(':'));
	}

	/**
	 * Fetch version stored in db
	 * 
	 * @return Returns cursor over Imdb details.
	 */
	public String getCurrentVersion() {
		String version = "";
		mCursor = mDb.query(true, Version.TABLE_NAME, new String[] {
				Version.ROW_ID, Version.VERSION_NAME }, "_id = 1", null, null,
				null, null, null);
		if (mCursor != null) {
			if (mCursor.moveToFirst())
				version = mCursor.getString(mCursor
						.getColumnIndexOrThrow(Version.VERSION_NAME));

		}
		safelyCloseMCursor();
		return version;

	}

	public String getCurrentProgramVersion() {
		String version = "";
		mCursor = mDb.query(true, Version.TABLE_NAME, new String[] {
				Version.ROW_ID, Version.VERSION_NAME }, "_id = 2", null, null,
				null, null, null);
		if (mCursor != null) {
			if (mCursor.moveToFirst())
				version = mCursor.getString(mCursor
						.getColumnIndexOrThrow(Version.VERSION_NAME));

		}
		safelyCloseMCursor();
		return version;

	}

	public long insertNewProgramVersion(String version_number) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(Version.VERSION_NAME, version_number);
		initialValues.put(Version.ROW_ID, 2);
		return mDb.replace(Version.TABLE_NAME, null, initialValues);
	}

	/**
	 * Insert a new Version.
	 * 
	 * @return Returns affected row id.
	 */
	public long insertNewVersion(String version_number) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(Version.VERSION_NAME, version_number);
		initialValues.put(Version.ROW_ID, 1);
		return mDb.replace(Version.TABLE_NAME, null, initialValues);
	}

	/*************************
	 * IMDB Table
	 */

	/**
	 * Insert Imdb related info for a particular movie invoked from insertMedia.
	 * 
	 * @return Returns affected row id.
	 */
	private long insertImdbEntryFor(float rating, String reviewUrl) {
		if (reviewUrl.toLowerCase() == "null")
			return 0;
		ContentValues initialValues = new ContentValues();
		initialValues.put(ImdbInfo.IMDB_RATING, rating);
		initialValues.put(ImdbInfo.IMDB_LINK, reviewUrl);
		return mDb.insert(ImdbInfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Fetch imdb details for a particular ID.
	 * 
	 * @return Returns cursor over Imdb details.
	 */
	private Cursor getImdbDetailsFor(long this_id) {
		return mDb.query(true, ImdbInfo.TABLE_NAME, new String[] {
				ImdbInfo.ROW_ID, ImdbInfo.IMDB_RATING, ImdbInfo.IMDB_LINK },
				Mediainfo.ROW_ID + "=" + this_id, null, null, null, null, null);

	}

	/**************************
	 * Reminders Table
	 */

	/**
	 * Update isFavorite under MediaInfo table, set to Mediainfo.IS_FAVORITE or
	 * Mediainfo.IS_NOT_FAVORITE
	 * 
	 * @return Returns true or false, status of update
	 * */
	public long setIsFavorite(long mediaId, boolean isFavorite) {
		checkForDBLockRelease(DEFAULT_WAIT_TIME);
		ContentValues initialValues = new ContentValues();
		initialValues.put(Remindersinfo.MEDIA_ID, mediaId);
		initialValues.put(Remindersinfo.REMINDER_ENABLED,
				sanitiseBooleanToInteger(isFavorite));
		initialValues.put(Remindersinfo.IS_FAVORITE_FLAG,
				sanitiseBooleanToInteger(isFavorite));
		return mDb.replace(Remindersinfo.TABLE_NAME, null, initialValues);
	}

	/**
	 * Enable/Disable Reminder for a particular media id.
	 * 
	 * @return Returns true or false, status of update
	 * */
	public boolean toggleReminderFor(long mediaId, boolean reminderEnabled) {
		ContentValues updateValues = new ContentValues();
		updateValues.put(Remindersinfo.REMINDER_ENABLED,
				sanitiseBooleanToInteger(reminderEnabled));
		return mDb.update(Remindersinfo.TABLE_NAME, updateValues,
				Remindersinfo.MEDIA_ID + "=" + mediaId, null) > 0;
	}

	/**
	 * Updates a channel to favourite or not
	 * 
	 * 
	 * @return true if updated, false otherwise.
	 */

	public void setFavoriteChannels(List<FavouriteChannelModel> list) {
		ContentValues updateValues = new ContentValues();
		Iterator<FavouriteChannelModel> iter = list.iterator();
		while (iter.hasNext()) {
			FavouriteChannelModel model = (FavouriteChannelModel) iter.next();
			updateValues.put(ChannelsInfo.IS_FAVORITE_CHANNEL,
					model.isSelected());
			mDb.update(Models.ChannelsInfo.TABLE_NAME, updateValues,
					ChannelsInfo.ROW_ID + "=" + model.get_id(), null);
		}

	}

	/**
	 * Return a Cursor over the list of all favorite shows in the ReminderInfo
	 * Table.
	 * 
	 * @return Cursor over all shows
	 * */
	public Cursor fetchAllFromReminderInfo() {
		return mDb.query(Remindersinfo.TABLE_NAME, new String[] {
				Remindersinfo.MEDIA_ID, Remindersinfo.IS_FAVORITE_FLAG,
				Remindersinfo.REMINDER_ENABLED }, null, null, null, null, null);
	}
	public Cursor fetchFavsFromReminderInfo() {
		return mDb.query(Remindersinfo.TABLE_NAME, new String[] {
				Remindersinfo.MEDIA_ID, Remindersinfo.IS_FAVORITE_FLAG,
				Remindersinfo.REMINDER_ENABLED }, Remindersinfo.IS_FAVORITE_FLAG+"= 1", null, null, null, null);
	}

	/**
	 * Checks whether is favorite is enabled for the given media id
	 * 
	 * @param this_mediaID
	 * @return true if found, false otherwise.
	 */
	public boolean IsFavoriteEnabledFor(long this_mediaID) {
		boolean isEnabled = false;
		Cursor tmpCursor = mDb.query(true, Remindersinfo.TABLE_NAME,
				new String[] { Remindersinfo.MEDIA_ID,
						Remindersinfo.IS_FAVORITE_FLAG },
				Remindersinfo.MEDIA_ID + "=" + this_mediaID, null, null, null,
				null, null);
		if (tmpCursor == null) {
			// safety !
			isEnabled = false;
		} else if (!tmpCursor.moveToFirst()) {
			isEnabled = false;
		} else {
			isEnabled = sanitiseIntegerToBoolean(tmpCursor.getInt(tmpCursor
					.getColumnIndexOrThrow(Remindersinfo.IS_FAVORITE_FLAG)));
		}
		tmpCursor.close();
		return isEnabled;
	}
	/**
	 * Fetches all favorite media.
	 * 
	 */
	public ArrayList<Media> fetchFavorites(){
		ArrayList<Media> favMediaList = new ArrayList<Media>();
		Cursor tmpCursor = fetchFavsFromReminderInfo();
		if (tmpCursor != null) {
			tmpCursor.moveToFirst();
			while (!tmpCursor.isAfterLast()) {
				Media media = fetchShowsInfoFor(tmpCursor.getLong(tmpCursor.getColumnIndexOrThrow(Remindersinfo.MEDIA_ID)));
				favMediaList.add(media);
				tmpCursor.moveToNext();
			}
		}
		tmpCursor.close();
		return favMediaList;
	}

	/**************************
	 * Private Helpers
	 */

	/**
	 * Get Basic Media Object from the given cursor.
	 * 
	 * @param cursor
	 *            from channel_info table
	 * @return Media Object
	 */
	
	

	private Media unWrapShowDataFrom(Cursor tmpCursor) {
		Media media;
		long media_id = tmpCursor.getLong(tmpCursor
				.getColumnIndexOrThrow(ChannelMediaInfo.MEDIA_ID));
		media = fetchMediaFor(media_id);
		media.setId(media_id);
		media.setChannel(tmpCursor.getInt(tmpCursor
				.getColumnIndexOrThrow(ChannelMediaInfo.CHANNEL_ID)));
		media.setShowTime(tmpCursor.getString(tmpCursor
				.getColumnIndexOrThrow(ChannelMediaInfo.AIR_TIME)));
		media.setShowEndTime(tmpCursor.getString(tmpCursor
				.getColumnIndexOrThrow(ChannelMediaInfo.END_TIME)));
		return media;
	}

	
	/**
	 * Get Media Object from the given cursor.
	 * 
	 * @param cursor
	 *            from media_info table
	 * @return Media Object
	 */
	private Media getMediaFromCursor(Cursor cursor) {
		Media media = new Media();
		media.setMediaTitle(cursor.getString(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_TITLE)));
		media.setMediaDescription(cursor.getString(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_DESCRIPTION)));
		media.setMediaThumb(cursor.getString(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_THUMB)));
		media.setCategoryType(cursor.getInt(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_CAT_ID)));
		media.setShowDuration(cursor.getString(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_DURATION)));
		media.setSeriesID(cursor.getInt(cursor
				.getColumnIndexOrThrow(Mediainfo.SERIES_ID)));
		int imdbId = cursor.getInt(cursor
				.getColumnIndexOrThrow(Mediainfo.MEDIA_IMDB_ID));
		if (imdbId <= 0) {
			media.setImdbRating(0.0F);
			media.setImdbLink(null);
			return media;
		} else {
			// get imdb details of this media from IMDBInfo table.
	
			Cursor tmpCursor = getImdbDetailsFor(cursor.getInt(cursor
					.getColumnIndexOrThrow(Mediainfo.MEDIA_IMDB_ID)));
			tmpCursor.moveToFirst();

			if (tmpCursor.getCount() > 0) {
				media.setImdbRating(tmpCursor.getFloat(tmpCursor
						.getColumnIndexOrThrow(ImdbInfo.IMDB_RATING)));
				media.setImdbLink(tmpCursor.getString(tmpCursor
						.getColumnIndexOrThrow(ImdbInfo.IMDB_LINK)));
				tmpCursor.close();
			}
		}
		return media;
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

}
