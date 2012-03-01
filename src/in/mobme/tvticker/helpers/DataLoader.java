package in.mobme.tvticker.helpers;

import in.mobme.tvticker.Constants;
import in.mobme.tvticker.database.TvTickerDBAdapter;
import in.mobme.tvticker.rpcclient.RPCClient;

import org.alexd.jsonrpc.JSONRPCException;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

public class DataLoader {
	Context ctx = null;
	TvTickerDBAdapter dataAdapter = null;
	RPCClient rpc_client = null;
	public static int CONNECTION_FAILED = -2;
	public static int AUTHENTICATION_FAILED = -3;
	public static int STATUS = -1;

	final String TAG = "TVTICKER_DATAMOCKER";
	public static final int TYPE_LARGE = 10;
	public static final int TYPE_SMALL = 20;


	public DataLoader(Context ctx) {
		this.ctx = ctx;
		dataAdapter = new TvTickerDBAdapter(ctx);

	}

	public int startUpdation() throws JSONException {

		dataAdapter.open();
		rpc_client = new RPCClient(dataAdapter);
		
		String version = dataAdapter.getCurrentVersion();
		
		try {
			rpc_client.updateToLatestVersion(version);
			//loading programs
			//rpc_client.cacheProgramsForDays(7);
		} catch (JSONRPCException e) {
			Log.i("TVTICKER_DATAMOCKER", "json exception");
			e.printStackTrace();
			return STATUS;
		} finally{
			dataAdapter.close();
		}

		return 0;
	}
	
	public static String formattedThumbUrl(String thumbID, int size) {
		return Constants.RPC.Media.THUMBNAIL_PREFIX + thumbID
				+ (size == TYPE_LARGE ? Constants.RPC.Media.THUMBNAIL_LARGE : Constants.RPC.Media.THUMBNAIL_SMALL);
	}
	
	
}
