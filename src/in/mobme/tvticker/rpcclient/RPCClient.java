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

import android.util.Log;

public class RPCClient {
	private JSONRPCClient client;
	private MediaJsonParser parser;
	


	public RPCClient(String URI, int ConnTimeOut, int SoTimeOut) {
		client = JSONRPCClient.create(URI);
		client.setConnectionTimeout(ConnTimeOut);
		client.setSoTimeout(SoTimeOut);
		Log.i(Constants.TAG, client.toString());
		parser = new MediaJsonParser();
	//	hashed_key = getAuthenticationParameters();
	}

	
	public RPCClient() {
		int ConnTimeOut = Constants.RPC.CONNECTION_TIMEOUT;
		int SoTimeOut = Constants.RPC.SO_TIMEOUT;
		client = JSONRPCClient.create(Constants.RPC.SERVICE_URI);
		client.setConnectionTimeout(ConnTimeOut);
		client.setSoTimeout(SoTimeOut);
		Log.i(Constants.TAG, client.toString());
		parser = new MediaJsonParser();
	//	hashed_key = getAuthenticationParameters();
	}

	
	public boolean ping() throws JSONRPCException{
		return callClientString(Constants.RPC.Services.PING).equals("pong") ? true : false;
	}
	
	public ArrayList<Channels> getAllChannelList() throws JSONRPCException, JSONException {
		ArrayList<Channels> channelList = new ArrayList<Channels>();
		JSONArray channels = callClientJSONArray(Constants.RPC.Services.LIST_OF_CHANNELS);
		for(int i = 0; i < channels.length(); i++ ){
			JSONObject mediaObj = channels.getJSONObject(i).getJSONObject(Constants.RPC.CHANNEL_TAG);
			//straight to DB or list .. Pending
			channelList.add(parser.parseJsonChannel(mediaObj));
		}
		return channelList;
	}
	
	public ArrayList<Categories> getAllCategoryList() throws JSONRPCException, JSONException {
		ArrayList<Categories> categoryList = new ArrayList<Categories>();
		JSONArray categories = callClientJSONArray(Constants.RPC.Services.LIST_OF_CATEGORIES);
		for(int i = 0; i < categories.length(); i++ ){
			JSONObject mediaObj = categories.getJSONObject(i).getJSONObject(Constants.RPC.CATEGORY_TAG);
			//straight to DB or list .. Pending
			categoryList.add(parser.parseJsonCategory(mediaObj));
		}
		return categoryList;
	}
	
	public ArrayList<Media> getMediaListFor(String time, String frameType ) throws JSONRPCException, JSONException {
		ArrayList<Media> mediaList = new ArrayList<Media>();
		JSONArray pgms = callClientJSONArray(Constants.RPC.Services.PROGRAMS_FOR_FRAME, time, frameType);
		for(int i = 0; i < pgms.length(); i++ ){
			JSONObject mediaObj = pgms.getJSONObject(i).getJSONObject(Constants.RPC.PROGRAM_TAG);
			//straight to DB or list .. Pending
			mediaList.add(parser.parseJsonMedia(mediaObj));
		}
		return mediaList;
	}
	
	public void updateToLatestVersion(String version_string,TvTickerDBAdapter mockAdapter) throws JSONRPCException, JSONException {
		
		JSONObject updateResult = callClientJSONObject(Constants.RPC.Services.UPDATE_TO_VERSION,version_string);
		JSONArray channels = updateResult.getJSONArray("channels");
		JSONArray categories = updateResult.getJSONArray("categories");
		//JSONArray programs = updateResult.getJSONArray("programs");
		//JSONArray series = updateResult.getJSONArray("series");
		JSONArray version = updateResult.getJSONArray("versions");
		updateChannels(channels, mockAdapter);
		updateVersion(version,mockAdapter);
		Log.i("categories",""+categories);
		updateCategories(categories, mockAdapter);
			
	}
	public void updateVersion(JSONArray version,TvTickerDBAdapter mockAdapter) throws JSONException
	{
		if(version.length() >0){
		JSONObject jsonObject = version.getJSONObject(0).getJSONObject(Constants.RPC.VERSION_TAG);
		String version_number = jsonObject.getString("number");
		mockAdapter.insertNewVersion(version_number);
		}
	}
	
	public void updateChannels(JSONArray channels,TvTickerDBAdapter mockAdapter) throws JSONException {
		ArrayList<Channels> channelList = new ArrayList<Channels>();
		
		for(int i = 0; i < channels.length(); i++ ){
			JSONObject jsonObject = channels.getJSONObject(i).getJSONObject(Constants.RPC.CHANNEL_TAG);
			Channels channel = parser.parseJsonChannel(jsonObject);
			mockAdapter.updateOrInsertChannel(channel.get_id(),channel.getChannelTitle());
		}
		
	}
	
	public void updateCategories(JSONArray categories,TvTickerDBAdapter mockAdapter) throws JSONException {
		ArrayList<Channels> channelList = new ArrayList<Channels>();
		
		for(int i = 0; i < categories.length(); i++ ){
			JSONObject jsonObject = categories.getJSONObject(i).getJSONObject(Constants.RPC.CATEGORY_TAG);
			Categories category  = parser.parseJsonCategory(jsonObject);
			mockAdapter.updateOrInsertCategory(category.get_id(),category.getCategoryName());
		}
		
	}
	
	public String getCurrentVersion() throws JSONRPCException {
		return client.callString(Constants.RPC.Services.CURRENT_DATA_VERSION);
	}
	
	public void updateTo(String version) throws JSONRPCException, JSONException {
		JSONObject updateResult = callClientJSONObject(Constants.RPC.Services.UPDATE_TO_VERSION);
		Log.i(Constants.TAG, updateResult.getString("channels"));
		for (int i = 0; i < updateResult.length(); i++) {
			// JSONObject mediaObj =
			// updateResult.getJSONObject(i).getJSONObject("version");
			// Log.i(Constants.TAG, mediaObj.toString());
		}
	}
	
	
	
	private String callClientString(String method, Object... params) throws JSONRPCException {
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.addAll(getAuthenticationParameters());
		parameters.addAll(Arrays.asList(params));
		return client.callString(method, parameters.toArray());
	}
	
	private JSONObject callClientJSONObject(String method, Object... params) throws JSONRPCException {
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.addAll(getAuthenticationParameters());
		parameters.addAll(Arrays.asList(params));
		return client.callJSONObject(method, parameters.toArray());
	}
	
	private JSONArray callClientJSONArray(String method, Object... params) throws JSONRPCException {
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
		} catch(NoSuchAlgorithmException error) {
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
