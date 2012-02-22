package in.mobme.tvticker;

import in.mobme.tvticker.activity.AllShowsMainPageActivity;
import in.mobme.tvticker.data_model.Media;
import in.mobme.tvticker.database.TvTickerDBAdapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

class ViewPagerAdapter extends PagerAdapter {

	final static int FAVORITES_POSITION = 0;
	final static int NOW_POSITION = 1;
	final static int LATER_TODAY_POSITION = 2;

	// private String EMPTY[] = {};

	private String[] pageTitles = null;
	private int listViewId;

	private static List<Media> nowMediaList = new ArrayList<Media>();
	private static List<Media> laterMediaList = new ArrayList<Media>();
	private static List<Media> favmediaList = new ArrayList<Media>();

	private ListView listView;
	private Button browseAllShowsButton;
	private TextView favFooterText = null;
	// private View favListEmptyView;
	private Context context = null;
	private LazyAdapter lazyAdapter = null;
	private MyArrayAdapter favoritesAdapter = null;
	private TvTickerDBAdapter tvDataAdapter = null;
	public static ViewPagerAdapter staticAdapterObj;

	public ViewPagerAdapter(int listViewIdentifier, String[] titles, Context ctx) {
		this.pageTitles = titles;
		this.listViewId = listViewIdentifier;
		this.context = ctx;
		this.tvDataAdapter = new TvTickerDBAdapter(context);
		tvDataAdapter.open();
		nowMediaList = tvDataAdapter.fetchShowsforNowFrame();
		laterMediaList = tvDataAdapter.fetchShowsforLaterFrame();
		favmediaList = getFavoriteMediaFrom( tvDataAdapter.fetchAllShowsInfo());
		tvDataAdapter.close();
		staticAdapterObj = this;
	}

	@Override
	public int getCount() {
		return pageTitles.length;
	}

	private List<Media> getFavoriteMediaFrom(List<Media> list) {
		List<Media> fav_list = new ArrayList<Media>();
		//should get the list from database .. PENDING
		return fav_list;
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
		listView = (ListView) layoutInflater.inflate(listViewId, null);

		if (position == NOW_POSITION) {
			
			lazyAdapter = new LazyAdapter((Activity) context, nowMediaList, true);
			listView.setAdapter(lazyAdapter);
			listView.setOnItemClickListener(new CustomOnItemClickListener());
			
		} else if (position == LATER_TODAY_POSITION) {
		
			lazyAdapter = new LazyAdapter((Activity) context, laterMediaList, true);
			listView.setAdapter(lazyAdapter);
			listView.setOnItemClickListener(new CustomOnItemClickListener());

		} else if (position == FAVORITES_POSITION) {

			favoritesAdapter = new MyArrayAdapter((Activity) context,
					R.layout.rowlayout, favmediaList, true);
			boolean isEmpty = favoritesAdapter.getCount() <= 0 ? true : false;
			listView.addFooterView(getFooterView(isEmpty));
			listView.setAdapter(favoritesAdapter);
			listView.setOnItemClickListener(new FavoritesOnItemClickListener());
		}

		
		((ViewPager) collection).addView(listView, 0);
		return listView;
	}

	/**
	 * Inflates a view to be appended, as the footer.
	 * 
	 * @param isEmptyView
	 *            , either true or false
	 * @return view
	 */
	private View getFooterView(boolean isEmptyView) {
		View v = null;
		
		v = ((LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.favorites_inflated_empty_view, null, false);
		
		favFooterText  = (TextView) v.findViewById(R.id.fav_footer_text);
		if(!isEmptyView)
			favFooterText.setText(R.string.favorites_normal_text);
		
		browseAllShowsButton = (Button) v
				.findViewById(R.id.browseAllShowsButton);
		browseAllShowsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browseShowsIntent = new Intent(context,
						AllShowsMainPageActivity.class);
				context.startActivity(browseShowsIntent);
			}
		});
		return v;
	}

	// handles list item click
	private class CustomOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {
			Media selectedMedia = nowMediaList.get(position);
			Intent detailedViewIntent = new Intent(context,
					DetailedDescriptionActivity.class);
			Bundle b = new Bundle();
			b.putSerializable(Constants.MEDIA_OBJECT, selectedMedia);
			detailedViewIntent.putExtras(b);
			context.startActivity(detailedViewIntent);
		}
	}
	
	// handles list item click
	private class FavoritesOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {
			Media selectedMedia = favmediaList.get(position);
			Intent detailedViewIntent = new Intent(context,
					DetailedDescriptionActivity.class);
			Bundle b = new Bundle();
			b.putSerializable(Constants.MEDIA_OBJECT, selectedMedia);
			detailedViewIntent.putExtras(b);
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
		// System.out.println("on destroyItem() for page at " + position);
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
		// System.out.println("on finishUpdate()" + arg0.getId());
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
		// System.out.println("on startUpdate() for " + arg0.getId());
	}

	@Override
	public int getItemPosition(Object object) {
		// System.out.println("on getItemPosition()");
		return POSITION_NONE;
	}

	@Override
	public void notifyDataSetChanged() {
		// System.out.println("on notifyDataSetChanged()");
		super.notifyDataSetChanged();
	}

	public void refreshFavAdapter(Media media) {
		int mediaPosition = checkIfListContains(media);
		if (mediaPosition == -1) {
			System.out.println("New Entry, Adding..");
			favmediaList.add(media);
		} else {
			favmediaList.remove(mediaPosition);
			System.out.println("Duplicate Entry.., Removing");
		}
		favoritesAdapter = new MyArrayAdapter((Activity) context,
				R.layout.rowlayout, favmediaList, true);
		notifyDataSetChanged();
	}

	private int checkIfListContains(Media thisMedia) {
		int hasMedia = -1;
		for (int i = 0; i < favmediaList.size(); i++) {
			Media media = favmediaList.get(i);
			if (media.getId() == thisMedia.getId()) {
				hasMedia = i;
				break;
			}
		}
		return hasMedia;
	}
	
	

}
