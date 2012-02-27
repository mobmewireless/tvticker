package in.mobme.tvticker;

import in.mobme.tvticker.helpers.DataConnection;
import in.mobme.tvticker.helpers.DataLoader;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private TextView progressText;
	private Context ctx;
	private int activeConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		ctx = this;
		progressText = (TextView) findViewById(R.id.progressTextView);
		// executing with random string argument..!
		new UpdationTask().execute("startUpdating");

	}

	//Checks Internet connection, also updates categories and channels.
	private class UpdationTask extends AsyncTask<String, String, Boolean> {
		protected Boolean doInBackground(String... urls) {
			// check Internet
			publishProgress("Checking connectivity..");
			DataConnection dataConn = new DataConnection(ctx);
			activeConnection = dataConn.getActiveConnection();
			Log.i("SplashActivity", "active connection is :" + activeConnection);
			if (activeConnection == DataConnection.TYPE_OFFLINE) {
				return false;
			} else {
				DataLoader dataloader = new DataLoader(getBaseContext());
				try {
					publishProgress("Loading Data..");
					dataloader.startUpdation();
					publishProgress("Done");
					return true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return false;
		}

		protected void onProgressUpdate(String... progress) {
			progressText.setText(progress[0]);
		}

		protected void onPostExecute(Boolean isSuccess) {
			int mode;
			switch (activeConnection) {
			case DataConnection.TYPE_GPRS:
				mode = DataConnection.TYPE_GPRS;
				break;
			case DataConnection.TYPE_3G:
				mode = DataConnection.TYPE_3G;
				break;
			case DataConnection.TYPE_WIFI:
				mode = DataConnection.TYPE_WIFI;
				break;
			case DataConnection.TYPE_EDGE:
				mode = DataConnection.TYPE_3G;
				break;
			case DataConnection.TYPE_HSDPA:
				mode = DataConnection.TYPE_3G;
				break;
			default:
				mode = DataConnection.TYPE_OFFLINE;
				break;
			}
			finish();
			Intent intent = new Intent(getBaseContext(), HomePageActivity.class);
			intent.putExtra(Constants.MODE, mode);
			ctx.startActivity(intent);
		}
	}

}
