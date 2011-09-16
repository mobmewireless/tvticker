package com.sample.hor_pager;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.jakewharton.android.viewpagerindicator.TitleProvider;

class AwesomePagerAdapter extends PagerAdapter implements TitleProvider {

	private String[]pageTitles = null;
	private int listviewid;
	private int rowLayoutIdentifier;
	private ListView listView1;
	private Context context = null;
	private MyArrayAdapter dataAdapter = null;
	
	public AwesomePagerAdapter(int listViewIdentifier, int rowLayoutIdentifier, String[] titles, Context ctx){
		this.pageTitles = titles;
		this.listviewid = listViewIdentifier;
		this.rowLayoutIdentifier = rowLayoutIdentifier;
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

		LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
		listView1 = (ListView) layoutInflater.inflate(listviewid, null);
		String[] listData = null;
		if (position == 0) {
			listData = context.getResources().getStringArray(R.array.list1);
			dataAdapter = new MyArrayAdapter((Activity) context,
					rowLayoutIdentifier, listData);
		} else if (position == 1) {
			listData = context.getResources().getStringArray(R.array.list2);
			dataAdapter = new MyArrayAdapter((Activity) context,
					rowLayoutIdentifier, listData);
		} else {
			listData = context.getResources().getStringArray(R.array.list3);
			dataAdapter = new MyArrayAdapter((Activity) context,
					rowLayoutIdentifier, listData);
		}

		listView1.setAdapter(dataAdapter);
		listView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg3) {
				Toast.makeText(context,
						adapter.getAdapter().getItem(position).toString(),
						Toast.LENGTH_LONG).show();
			}
		});

		((ViewPager) collection).addView(listView1, 0);

		return listView1;
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
		System.out.println("on destroyItem()");
		((ViewPager) collection).removeView((ListView) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		System.out.println("on isViewFromObject()");
		return view == ((ListView) object);
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
		System.out.println("on finishUpdate()");
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		System.out.println("on restoreState()");
	}

	@Override
	public Parcelable saveState() {
		System.out.println("on saveState()");
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		System.out.println("on startUpdate()");
	}

	@Override
	public String getTitle(int position) {
		return pageTitles[position];
	}

}
