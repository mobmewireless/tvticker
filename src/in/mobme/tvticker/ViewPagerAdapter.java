package in.mobme.tvticker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

class ViewPagerAdapter extends PagerAdapter {

	final static int NOW_POSITION = 1;
	final static int LATER_TODAY_POSITION = 2;
	final static int FAVORITES_POSITION = 0;

	//private String EMPTY[] = {};

	private String[] pageTitles = null;
	private int listViewId;
	private static List<String> list = new ArrayList<String>();

	private FrameLayout frame = null;
	private ListView listView1;
	private Button browseAllShowsButton;
	// private View favListEmptyView;
	private Context context = null;
	private LazyAdapter lazyAdapter = null;

	public ViewPagerAdapter(int listViewIdentifier, String[] titles, Context ctx) {
		this.pageTitles = titles;
		this.listViewId = listViewIdentifier;
		this.context = ctx;
	}

	@Override
	public int getCount() {
		return pageTitles.length;
	}

	/**
	 * Create the page for the given position. The adapter is responsible for
	 * adding the view to the container given here, although it only must ensure
	 * this is done by the time it returns from {@link #finishUpdate()}.
	 * 
	 * @param container
	 *            The containing View in which the page will be shown.
	 * @param position
	 *            The page position to be instantiated.
	 * @return Returns an Object representing the new page. This does not need
	 *         to be a View, but can be some other container of the page.
	 */
	@Override
	public Object instantiateItem(View collection, int position) {

		LayoutInflater layoutInflater = ((Activity) context)
				.getLayoutInflater();
		frame = (FrameLayout) layoutInflater.inflate(listViewId, null);
		listView1 = (ListView) frame.findViewById(android.R.id.list);

		String[] listData = null;
		if (position == NOW_POSITION) {

			listData = context.getResources().getStringArray(R.array.list1);
			list = convertToList(listData);
			lazyAdapter = new LazyAdapter((Activity) context, list);

		} else if (position == LATER_TODAY_POSITION) {

			listData = context.getResources().getStringArray(R.array.list2);
			list = convertToList(listData);
			lazyAdapter = new LazyAdapter((Activity) context, list);

		} else if (position == FAVORITES_POSITION) {

			listData = context.getResources().getStringArray(R.array.list3);
			list = convertToList(listData);
			lazyAdapter = new LazyAdapter((Activity) context, list);

			if (lazyAdapter.getCount() <= 0) { // list view is empty
				ViewStub emptyView = (ViewStub) frame
						.findViewById(android.R.id.empty);
				// System.out.println("empty view activated");
				View v = emptyView.inflate();
				browseAllShowsButton = (Button) v
						.findViewById(R.id.browseAllShowsButton);
				browseAllShowsButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent browseShowsIntent = new Intent(context,
								BrowseAllShowsActivity.class);
						context.startActivity(browseShowsIntent);
					}
				});
				listView1.setEmptyView(v);

			} else {
				System.out.println("empty view de-activated");
			}

		}

		listView1.setAdapter(lazyAdapter);
		listView1.setOnItemClickListener(new CustomOnItemClickListener());

		((ViewPager) collection).addView(frame, 0);

		return frame;
	}

	private List<String> convertToList(String[] items) {
		List<String> list = new ArrayList<String>();
		for (String item : items) {
			list.add(item);
		}
		return list;
	}

	// handles list item click
	private class CustomOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {
			String selectedItem = adapter.getAdapter().getItem(position)
					.toString();
			Toast.makeText(context, selectedItem, Toast.LENGTH_LONG).show();
			Intent detailedViewIntent = new Intent(context,
					DetailedDescriptionActivity.class);
			detailedViewIntent.putExtra("selectedItem", selectedItem);
			context.startActivity(detailedViewIntent);
		}
	}

	/**
	 * Remove a page for the given position. The adapter is responsible for
	 * removing the view from its container, although it only must ensure this
	 * is done by the time it returns from {@link #finishUpdate()}.
	 * 
	 * @param container
	 *            The containing View from which the page will be removed.
	 * @param position
	 *            The page position to be removed.
	 * @param object
	 *            The same object that was returned by
	 *            {@link #instantiateItem(View, int)}.
	 */
	@Override
	public void destroyItem(View collection, int position, Object view) {
		// System.out.println("on destroyItem()");
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// System.out.println("on isViewFromObject()");
		return view == ((View) object);
	}

	/**
	 * Called when the a change in the shown pages has been completed. At this
	 * point you must ensure that all of the pages have actually been added or
	 * removed from the container as appropriate.
	 * 
	 * @param container
	 *            The containing View which is displaying this adapter's page
	 *            views.
	 */
	@Override
	public void finishUpdate(View arg0) {
		// System.out.println("on finishUpdate()");
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// System.out.println("on restoreState()");
	}

	@Override
	public Parcelable saveState() {
		// System.out.println("on saveState()");
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// System.out.println("on startUpdate()");
	}

}