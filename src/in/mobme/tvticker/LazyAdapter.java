package in.mobme.tvticker;

import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.rpcclient.RPCClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.commonsware.cwac.endless.EndlessAdapter;

/*
 * Uses EndlessAdapter(CWAC tools) see com.commonsware.cwac.endless.EndlessAdapter
 */
public class LazyAdapter extends EndlessAdapter {
	private RotateAnimation rotate = null;
	private int pendingViewId;
	private Activity context;
	private int position;
	private RPCClient rpcClient;
	private String timeNow;

	LazyAdapter(Activity ctx, List<Media> list2, boolean displayThumb, int pos) {
		super(ctx, new MyArrayAdapter((Activity) ctx, R.layout.rowlayout,
				list2, displayThumb), R.layout.pending);
		context = ctx;
		pendingViewId = R.layout.pending;
		position = pos;
		rpcClient = new RPCClient(ctx);
		rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(800);
		rotate.setRepeatMode(Animation.RESTART);
		rotate.setRepeatCount(Animation.INFINITE);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		timeNow = (String) format.format(new Date());
	}

	// shows a loading animation until cache in background completes.
	@Override
	protected View getPendingView(ViewGroup parent) {
		View row = context.getLayoutInflater().inflate(pendingViewId, null);
		View child = row.findViewById(R.id.throbber);
		child.startAnimation(rotate);
		return (row);
	}

	// do network calls or time consuming process here.
	@Override
	protected boolean cacheInBackground() throws Exception {
		String frame = (position == Constants.ViewPager.NOW_POSITION) ? "now"
				: "later";
		rpcClient.updateMediaListFor(timeNow, frame);
		return (true);

	}

	// Apply obtained data to view here.
	@Override
	protected void appendCachedData() {
		Log.i("LAZY", ""+getWrappedAdapter().getCount());
		if (getWrappedAdapter().getCount() < 50) {
			// MyArrayAdapter a = (MyArrayAdapter) getWrappedAdapter();
			// List<String> list = new ArrayList<String>();
			// for (int i=0;i<25;i++) { a.add(""+list.size() + 1);}
		}
	}
}
