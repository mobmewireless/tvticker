package in.mobme.tvticker.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


public class DataConnection {

	final static int TYPE_OFFLINE = TelephonyManager.NETWORK_TYPE_UNKNOWN;
	final static int TYPE_GPRS = TelephonyManager.NETWORK_TYPE_GPRS;
	final static int TYPE_3G = TelephonyManager.NETWORK_TYPE_UMTS;
	final static int TYPE_HSDPA = TelephonyManager.NETWORK_TYPE_HSDPA;
	final static int TYPE_EDGE = TelephonyManager.NETWORK_TYPE_EDGE;
	final static int TYPE_WIFI = ConnectivityManager.TYPE_WIFI + 100;

	private ConnectivityManager cManager;

	public DataConnection(Context context) {
		cManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	public int getActiveConnection() {
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if(info != null)
			return getConnectionTypeFrom(info);
		else
			return TYPE_OFFLINE;
	}

	private int getConnectionTypeFrom(NetworkInfo info) {
		if (isTypeMobile(info.getType()))
			return info.getSubtype();
		else
			return info.getType();
	}

	private boolean isTypeMobile(int type) {
		return ConnectivityManager.TYPE_MOBILE == type ? true : false;
	}
}
