package in.mobme.tvticker.helpers;

import in.mobme.tvticker.data_model.Categories;
import in.mobme.tvticker.data_model.Channels;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import in.mobme.tvticker.rpcclient.Constants;
import in.mobme.tvticker.rpcclient.RPCClient;

import java.util.ArrayList;
import java.util.Date;

import org.alexd.jsonrpc.JSONRPCException;
import org.json.JSONException;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

public class DataMocker {
	Media media;
	Context ctx = null;
	TvTickerDBAdapter mockAdapter = null;
	RPCClient rpc_client = null;
	public static int CONNECTION_FAILED = -2;
	public static int AUTHENTICATION_FAILED = -3;
	public static int STATUS = -1;

	final String TAG = "TVTICKER_DATAMOCKER";
	final String[] CHANNELS = { "STAR MOVIES", "HBO", "SURYA", "MOVIES NOW",
			"BBC", "CNBC", "DISCOVERY", "STAR WORLD", "TEN SPORTS" };
	final String[] CATEGORIES = { "MOVIES", "NEWS", "TV SERIES", "DOCUMENTARY",
			"ENTERTAINMENT", "ANIME" };
	final String[] SERIES = { "NO", "YES" };

	public DataMocker(Context ctx) {
		this.ctx = ctx;
		mockAdapter = new TvTickerDBAdapter(ctx);

	}

	public int startMocking() {
	
			rpc_client = new RPCClient();
			try {
				rpc_client.ping();
				mockAdapter.open();
				populateChannels();
				populateCategories();
				// populateSeries();
				populateMainTable();
				mockAdapter.close();
			} catch (JSONRPCException e) {
				// TODO Auto-generated catch block
				Log.i("TVTICKER_DATAMOCKER", "json exception");
				e.printStackTrace();
				return STATUS;
			}
		
		return 0;
	}

	private void populateChannels() {
		ArrayList<Channels> channels = null;

		try {
			channels = rpc_client.getAllChannelList();
			for (Channels channel : channels) {
				mockAdapter.insertNewChannel(channel.getChannelTitle());
			}
		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void populateCategories() {
		ArrayList<Categories> categories = null;

		try {
			categories = rpc_client.getAllCategoryList();
			for (Categories category : categories) {
				mockAdapter.insertNewCategory(category.getCategoryName());
			}
		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void populateSeries() {
	// Log.i(TAG, "Populating series..");
	// for (String series : SERIES) {
	// mockAdapter.insertNewChannel(series);
	// }
	// Log.i(TAG, "Done");
	// }

	private void populateMainTable() {
		Log.i(TAG, "Populating Main table..");
		ArrayList<Media> media = null;
		try {
			
			String now = (String) DateFormat.format("yyyy-MM-dd kk:mm:ss",
					new Date());
			media = rpc_client.getMediaListFor(now, "full");

			for (Media program : media) {
				String thumbnail = Constants.RPC.Media.THUMBNAIL_PREFIX
						+ program.getThumbnailID()
						+ Constants.RPC.Media.THUMBNAIL_SUFFIX;
				Log.i("media", thumbnail);
				long _id = mockAdapter.createNewMediaInfo(mockMediaFor(
						program.getMediaTitle(), program.getMediaDescription(),
						thumbnail, program.getImdbLink() + "reviews",
						program.getImdbRating(), program.getCategoryType(),
						program.getShowTime(), program.getShowEndTime(),
						program.getShowDuration(), program.getSeriesID(),
						program.getChannel()));
				mockAdapter.setIsFavorite(_id, false);

			}
		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.i(TAG, "Done");
	}

	private Media mockMediaFor(String title, String desc, String thumb,
			String imdb_url, float rating, int category, String showTime,
			String show_end, String duration, int series_id, int channel_id) {
		media = new Media();
		media.setCategoryType(category);
		media.setChannel(channel_id);
		media.setImdbLink(imdb_url);
		media.setImdbRating(rating);
		media.setMediaDescription(desc);
		media.setMediaThumb(thumb);
		media.setMediaTitle(title);
		media.setSeriesID(series_id);
		media.setShowDuration(duration);
		media.setShowEndTime(show_end);
		media.setShowTime(showTime);
		return media;
	}

}
