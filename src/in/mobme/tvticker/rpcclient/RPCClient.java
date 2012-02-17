package in.mobme.tvticker.rpcclient;

import in.mobme.tvticker.data_model.Categories;
import in.mobme.tvticker.data_model.Channels;
import in.mobme.tvticker.data_model.Media;

import java.util.ArrayList;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RPCClient {
	private JSONRPCClient client;
	private MediaJsonParser parser;
	private String[] hashed_key = new String[] { "", "" };

	public RPCClient(String URI, int ConnTimeOut, int SoTimeOut) throws NoSuchAlgorithmException {
		client = JSONRPCClient.create(URI);
		client.setConnectionTimeout(ConnTimeOut);
		client.setSoTimeout(SoTimeOut);
		Log.i(Constants.TAG, client.toString());
		parser = new MediaJsonParser();
		hashed_key = getAuthenticationParameters();
	}

	public RPCClient()  throws NoSuchAlgorithmException {
		int ConnTimeOut = Constants.RPC.CONNECTION_TIMEOUT;
		int SoTimeOut = Constants.RPC.SO_TIMEOUT;
		client = JSONRPCClient.create(Constants.RPC.SERVICE_URI);
		client.setConnectionTimeout(ConnTimeOut);
		client.setSoTimeout(SoTimeOut);
		Log.i(Constants.TAG, client.toString());
		parser = new MediaJsonParser();
		hashed_key = getAuthenticationParameters();
	}

	public boolean ping() throws JSONRPCException {
		return client.callString(Constants.RPC.Services.PING,hashed_key).equals("pong") ? true
				: false;
	}

	public ArrayList<Channels> getAllChannelList() throws JSONRPCException,
			JSONException {
		ArrayList<Channels> channelList = new ArrayList<Channels>();
		JSONArray channels = client
				.callJSONArray(Constants.RPC.Services.LIST_OF_CHANNELS,hashed_key);
		for (int i = 0; i < channels.length(); i++) {
			JSONObject mediaObj = channels.getJSONObject(i).getJSONObject(
					Constants.RPC.CHANNEL_TAG);
			// straight to DB or list .. Pending
			channelList.add(parser.parseJsonChannel(mediaObj));
		}
		return channelList;
	}

	public ArrayList<Categories> getAllCategoryList() throws JSONRPCException,
			JSONException {
		ArrayList<Categories> categoryList = new ArrayList<Categories>();
		JSONArray categories = client
				.callJSONArray(Constants.RPC.Services.LIST_OF_CATEGORIES,hashed_key);
		for (int i = 0; i < categories.length(); i++) {
			JSONObject mediaObj = categories.getJSONObject(i).getJSONObject(
					Constants.RPC.CATEGORY_TAG);
			// straight to DB or list .. Pending
			categoryList.add(parser.parseJsonCategory(mediaObj));
		}
		return categoryList;
	}

	public ArrayList<Media> getMediaListFor(String time, String frameType)
			throws JSONRPCException, JSONException {
		ArrayList<Media> mediaList = new ArrayList<Media>();
		JSONArray pgms = client.callJSONArray(
				Constants.RPC.Services.PROGRAMS_FOR_FRAME, hashed_key[0], hashed_key[1], time, frameType);
		for (int i = 0; i < pgms.length(); i++) {
			JSONObject mediaObj = pgms.getJSONObject(i).getJSONObject(
					Constants.RPC.PROGRAM_TAG);
			// straight to DB or list .. Pending
			mediaList.add(parser.parseJsonMedia(mediaObj));
		}
		return mediaList;
	}

	public String getCurrentVersion() throws JSONRPCException {
		return client.callString(Constants.RPC.Services.CURRENT_DATA_VERSION,hashed_key);
	}

	public void updateTo(String version) throws JSONRPCException, JSONException {
		JSONObject updateResult = client
				.callJSONObject(Constants.RPC.Services.UPDATE_TO_VERSION,hashed_key);
		Log.i(Constants.TAG, updateResult.getString("channels"));
		for (int i = 0; i < updateResult.length(); i++) {
			// JSONObject mediaObj =
			// updateResult.getJSONObject(i).getJSONObject("version");
			// Log.i(Constants.TAG, mediaObj.toString());
		}
	}

	private String[] getAuthenticationParameters()
			throws NoSuchAlgorithmException {
		String[] parameters = { "", "" };
		MessageDigest md = MessageDigest.getInstance("MD5");

		String timestamp = String.valueOf(java.lang.System.currentTimeMillis());
		String keyWithTimestamp = timestamp
				.concat(Constants.RPC.Services.API_KEY);

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

		parameters[0] = timestamp;
		parameters[1] = hashtext;

		return parameters;
	}
}
