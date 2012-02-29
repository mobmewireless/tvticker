package in.mobme.tvticker.rpcclient;

import in.mobme.tvticker.data_model.Categories;
import in.mobme.tvticker.data_model.Channels;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class RPCClient {
	private JSONRPCClient client;
	private MediaJsonParser parser;
	private TvTickerDBAdapter dataAdapter;

	public RPCClient(String URI, int ConnTimeOut, int SoTimeOut,
			Context ctx, TvTickerDBAdapter adapter) {
		client = JSONRPCClient.create(URI);
		client.setConnectionTimeout(ConnTimeOut);
		client.setSoTimeout(SoTimeOut);
		this.dataAdapter = adapter;
		parser = new MediaJsonParser();
	}

	public RPCClient(TvTickerDBAdapter adapter) {
		client = JSONRPCClient.create(Constants.RPC.SERVICE_URI);
		client.setConnectionTimeout(Constants.RPC.CONNECTION_TIMEOUT);
		client.setSoTimeout(Constants.RPC.SO_TIMEOUT);
		this.dataAdapter = adapter;
		parser = new MediaJsonParser();
	}
	
	public boolean ping() throws JSONRPCException {
		return callClientString(Constants.RPC.Services.PING).equals("pong") ? true
				: false;
	}

	public void updateMediaListFor(String time, String frameType)
			throws JSONRPCException, JSONException {
		JSONArray pgms = callClientJSONArray(
				Constants.RPC.Services.PROGRAMS_FOR_FRAME, time, frameType);
		
		for (int i = 0; i < pgms.length(); i++) {
			JSONObject jsonObject = pgms.getJSONObject(i).getJSONObject(
					Constants.RPC.PROGRAM_TAG);
			// Expecting a db lock
			try{
				long _id = dataAdapter.createNewMediaInfo(parseMedia(jsonObject));
				dataAdapter.setIsFavorite(_id, false);
			}catch(Exception e ){
				Log.i("TvTicker UpdateMediaList","Error inserting data to tables.");
				e.printStackTrace();
			}
		}
	}

	public void updateToLatestVersion(String versionString)
			throws JSONRPCException, JSONException {
		JSONObject updateResult = callClientJSONObject(
				Constants.RPC.Services.UPDATE_TO_VERSION, versionString);
		updateChannels(updateResult.getJSONArray("channels"));
		updateCategories(updateResult.getJSONArray("categories"));
		updateVersion(updateResult.getJSONArray("versions"));
	}

	// Do this only if wifi is available
	public void cacheProgramsForDays(int days) throws JSONException,
			JSONRPCException {
		
		String currentVersion = dataAdapter.getCurrentProgramVersion();
		JSONObject updateResult = callClientJSONObject(Constants.RPC.Services.UPDATE_MEDIA,currentVersion,Constants.RPC.Services.CACHE_FOR_DAYS);
		String latestVersion = updateResult.getString("version").toString();
		updateMedia(updateResult.getJSONArray("programs"));
		dataAdapter.insertNewProgramVersion(latestVersion);
		
	}

	public void updateMedia(JSONArray programs) throws JSONException {
		for (int i = 0; i < programs.length(); i++) {
			JSONObject jsonObject = programs.getJSONObject(i).getJSONObject(
					Constants.RPC.PROGRAM_TAG);
			long _id = dataAdapter.createNewMediaInfo(parseMedia(jsonObject));
			dataAdapter.setIsFavorite(_id, false);
		}
	}
	
	//Parses media also checks and handle cases for IMDB entry is null.
	private Media parseMedia(JSONObject jsonObject) throws JSONException{
		Media program = parser.parseJsonMedia(jsonObject);
		program.setMediaThumb(""+program.getThumbnailID());
		if (program.getImdbLink().toLowerCase() != "null") {
			program.setImdbLink(program.getImdbLink());
			program.setImdbRating((float) jsonObject
					.getDouble(Constants.RPC.Media.IMDB_RATING_TAG));
		}
		return program;
	}

	public void updateVersion(JSONArray version) throws JSONException {
		if (version.length() > 0) {
			JSONObject jsonObject = version.getJSONObject(0).getJSONObject(
					Constants.RPC.VERSION_TAG);
			
			String version_number = jsonObject.getString("number");
			
			dataAdapter.insertNewVersion(version_number);
			
		}
	}

	public void updateChannels(JSONArray channels) throws JSONException {
		
		for (int i = 0; i < channels.length(); i++) {
			JSONObject jsonObject = channels.getJSONObject(i).getJSONObject(
					Constants.RPC.CHANNEL_TAG);
			Channels channel = parser.parseJsonChannel(jsonObject);
			dataAdapter.updateOrInsertChannel(channel.get_id(),
					channel.getChannelTitle());
		}
		
	}

	public void updateCategories(JSONArray categories) throws JSONException {
		
		for (int i = 0; i < categories.length(); i++) {
			JSONObject jsonObject = categories.getJSONObject(i).getJSONObject(
					Constants.RPC.CATEGORY_TAG);
			Categories category = parser.parseJsonCategory(jsonObject);
			dataAdapter.updateOrInsertCategory(category.get_id(),
					category.getCategoryName());
		}
		
	}

	
	private String callClientString(String method, Object... params)
			throws JSONRPCException {
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.addAll(getAuthenticationParameters());
		parameters.addAll(Arrays.asList(params));
		return client.callString(method, parameters.toArray());
	}

	private JSONObject callClientJSONObject(String method, Object... params)
			throws JSONRPCException {
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.addAll(getAuthenticationParameters());
		parameters.addAll(Arrays.asList(params));
		return client.callJSONObject(method, parameters.toArray());
	}

	private JSONArray callClientJSONArray(String method, Object... params)
			throws JSONRPCException {
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.addAll(getAuthenticationParameters());
		parameters.addAll(Arrays.asList(params));
		return client.callJSONArray(method, parameters.toArray());
	}

	private ArrayList<String> getAuthenticationParameters() {
		ArrayList<String> parameters = new ArrayList<String>();
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException error) {
			Log.e(Constants.TAG, "Can't generate hash!");
			return parameters;
		}

		String timestamp = String.valueOf(java.lang.System.currentTimeMillis());
		String keyWithTimestamp = timestamp.concat(Constants.RPC.API_KEY);

		// Let's generate a hex representation of the hash
		// http://stackoverflow.com/a/421696
		md.reset();
		md.update(keyWithTimestamp.getBytes());
		byte[] digest = md.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}

		parameters.add(timestamp);
		parameters.add(hashtext);

		return parameters;
	}
}
