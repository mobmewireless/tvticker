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

public class RPCClient {
	private JSONRPCClient client;
	private MediaJsonParser parser;

	public RPCClient(String URI , int ConnTimeOut, int SoTimeOut) {
		client = JSONRPCClient.create(URI);
		client.setConnectionTimeout(ConnTimeOut);
		client.setSoTimeout(SoTimeOut);
		Log.i(Constants.TAG, client.toString());
		parser = new MediaJsonParser();
	}
	public RPCClient( ) {
		int ConnTimeOut = Constants.RPC.CONNECTION_TIMEOUT;
		int SoTimeOut = Constants.RPC.SO_TIMEOUT;
		client = JSONRPCClient.create(Constants.RPC.SERVICE_URI);
		client.setConnectionTimeout(ConnTimeOut);
		client.setSoTimeout(SoTimeOut);
		Log.i(Constants.TAG, client.toString());
		parser = new MediaJsonParser();
	}
	
	public boolean ping() throws JSONRPCException{
		return client.callString(Constants.RPC.Services.PING).equals("pong") ? true : false;
	}
	
	public ArrayList<Channels> getAllChannelList() throws JSONRPCException, JSONException{
		ArrayList<Channels> channelList = new ArrayList<Channels>();
		JSONArray channels = client.callJSONArray(Constants.RPC.Services.LIST_OF_CHANNELS);
		for(int i = 0; i < channels.length(); i++ ){
			JSONObject mediaObj = channels.getJSONObject(i).getJSONObject(Constants.RPC.CHANNEL_TAG);
			//straight to DB or list .. Pending
			channelList.add(parser.parseJsonChannel(mediaObj));
		}
		return channelList;
	}
	
	public ArrayList<Categories> getAllCategoryList() throws JSONRPCException, JSONException{
		ArrayList<Categories> categoryList = new ArrayList<Categories>();
		JSONArray categories = client.callJSONArray(Constants.RPC.Services.LIST_OF_CATEGORIES);
		for(int i = 0; i < categories.length(); i++ ){
			JSONObject mediaObj = categories.getJSONObject(i).getJSONObject(Constants.RPC.CATEGORY_TAG);
			//straight to DB or list .. Pending
			categoryList.add(parser.parseJsonCategory(mediaObj));
		}
		return categoryList;
	}
	
	public ArrayList<Media> getMediaListFor(String time, String frameType) throws JSONRPCException, JSONException{
		ArrayList<Media> mediaList = new ArrayList<Media>();
		JSONArray pgms = client.callJSONArray(Constants.RPC.Services.PROGRAMS_FOR_FRAME, time, frameType);
		for(int i = 0; i < pgms.length(); i++ ){
			JSONObject mediaObj = pgms.getJSONObject(i).getJSONObject(Constants.RPC.PROGRAM_TAG);
			//straight to DB or list .. Pending
			mediaList.add(parser.parseJsonMedia(mediaObj));
		}
		return mediaList;	
	}
	
	public String getCurrentVersion() throws JSONRPCException{
		return client.callString(Constants.RPC.Services.CURRENT_DATA_VERSION);
	}
	
	public void updateTo(String version) throws JSONRPCException, JSONException{
		JSONObject updateResult = client.callJSONObject(Constants.RPC.Services.UPDATE_TO_VERSION);
		Log.i(Constants.TAG, updateResult.getString("channels"));
		for(int i = 0; i < updateResult.length(); i++ ){
//			JSONObject mediaObj = updateResult.getJSONObject(i).getJSONObject("version");
//			Log.i(Constants.TAG, mediaObj.toString());
		}
	}

}
