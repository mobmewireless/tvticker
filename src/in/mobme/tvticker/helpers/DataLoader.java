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
	TvTickerDBAdapter mockAdapter = null;
	RPCClient rpc_client = null;
	public static int CONNECTION_FAILED = -2;
	public static int AUTHENTICATION_FAILED = -3;
	public static int STATUS = -1;

	final String TAG = "TVTICKER_DATAMOCKER";


	public DataLoader(Context ctx) {
		this.ctx = ctx;
		mockAdapter = new TvTickerDBAdapter(ctx);

	}

	public int starUpdation() throws JSONException {

		rpc_client = new RPCClient(ctx);
		try {
			Log.i("ping", "" + rpc_client.ping());
			mockAdapter.open();
			rpc_client.updateToLatestVersion(mockAdapter.getCurrentVersion(),
					false);
			mockAdapter.close();
		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
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
			rpc_client.updateToLatestVersion(mockAdapter.getCurrentVersion(),
					false);
		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.i(TAG, "Done");
	}
	
	public static String formattedThumbUrl(String thumbID) {
		return Constants.RPC.Media.THUMBNAIL_PREFIX + thumbID
				+ Constants.RPC.Media.THUMBNAIL_SUFFIX;
	}
}
