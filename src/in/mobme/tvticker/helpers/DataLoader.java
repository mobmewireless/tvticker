package in.mobme.tvticker.helpers;

import in.mobme.tvticker.database.TvTickerDBAdapter;
import in.mobme.tvticker.rpcclient.Constants;
import in.mobme.tvticker.rpcclient.RPCClient;

import java.text.SimpleDateFormat;
import java.util.Date;

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


	public DataLoader(Context ctx) {
		this.ctx = ctx;
		dataAdapter = new TvTickerDBAdapter(ctx);

	}

	public int startUpdation() throws JSONException {

		rpc_client = new RPCClient(ctx);
		dataAdapter.open();
		String version = dataAdapter.getCurrentVersion();
		dataAdapter.close();
		try {
			rpc_client.updateToLatestVersion(version);
			//loading programs
			//rpc_client.cacheProgramsForDays(7);
		} catch (JSONRPCException e) {
			Log.i("TVTICKER_DATAMOCKER", "json exception");
			e.printStackTrace();
			return STATUS;
		} 

		return 0;
	}

	private void populateMainTable() {
		Log.i(TAG, "Populating Main table..");

		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss z");
			String now = (String) format.format(new Date());
			rpc_client.updateMediaListFor(now, "full");
			rpc_client.updateToLatestVersion(dataAdapter.getCurrentVersion());
		} catch (JSONRPCException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Log.i(TAG, "Done");
	}
	
	public static String formattedThumbUrl(String thumbID) {
		return Constants.RPC.Media.THUMBNAIL_PREFIX + thumbID
				+ Constants.RPC.Media.THUMBNAIL_SUFFIX;
	}
	
	
}
