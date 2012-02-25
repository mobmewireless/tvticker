package in.mobme.tvticker.helpers;

import in.mobme.tvticker.data_model.Categories;
import in.mobme.tvticker.data_model.Channels;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import in.mobme.tvticker.rpcclient.Constants;
import in.mobme.tvticker.rpcclient.RPCClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.alexd.jsonrpc.JSONRPCException;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

public class DataLoader {
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

	public DataLoader(Context ctx) {
		this.ctx = ctx;
		mockAdapter = new TvTickerDBAdapter(ctx);

	}

	public int starUpdation() throws JSONException {
	
			rpc_client = new RPCClient();
			try {
				Log.i("ping",""+rpc_client.ping());
				
				mockAdapter.open();
				
				//Log.i("update status",""+mockAdapter.updateOrInsertChannel(1, "star"));
				
				rpc_client.updateToLatestVersion(mockAdapter.getCurrentVersion(),mockAdapter);
				
				//populateChannels();
				//populateCategories();
				// populateSeries();
				//populateMainTable();
				
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
				mockAdapter.updateOrInsertChannel(channel.get_id(),channel.getChannelTitle());
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
				//mockAdapter.insertNewCategory(category.getCategoryName());
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
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
			 TimeZone tz = Calendar.getInstance().getTimeZone();

			String now = (String) format.format(new Date());
			media = rpc_client.getMediaListFor(now, "full");
			rpc_client.updateToLatestVersion(mockAdapter.getCurrentVersion(),mockAdapter);
			

			for (Media program : media) {
					String thumbnail = Constants.RPC.Media.THUMBNAIL_PREFIX  + program.getThumbnailID() + Constants.RPC.Media.THUMBNAIL_SUFFIX;
					program.setMediaThumb(thumbnail);
				long _id = mockAdapter.createNewMediaInfo(mockMediaFor(program));
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

	private Media mockMediaFor(Media thisMedia) {
		media = new Media();
		media.setCategoryType(thisMedia.getCategoryType());
		media.setChannel(thisMedia.getChannel());
		media.setImdbLink(thisMedia.getImdbLink() + "reviews");
		media.setImdbRating(thisMedia.getImdbRating());
		media.setMediaDescription(thisMedia.getMediaDescription());
		media.setMediaThumb(thisMedia.getMediaThumb());
		media.setMediaTitle(thisMedia.getMediaTitle());
		media.setSeriesID(thisMedia.getSeriesID());
		media.setShowDuration(thisMedia.getShowDuration());
		media.setShowEndTime(thisMedia.getShowEndTime());
		media.setShowTime(thisMedia.getShowTime());
		return media;
	}

}
