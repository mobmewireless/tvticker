/*
	Copyright (c) 2011 Rapture In Venice

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in
	all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	THE SOFTWARE.
*/

package in.mobme.tvticker.customwidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.raptureinvenice.webimageview.cache.WebImageCache;
import com.raptureinvenice.webimageview.download.WebImageManager;

public class WebImageView extends ImageView {

	public WebImageView(Context context) {
		super(context);
	}

	public WebImageView(Context context, AttributeSet attSet) {
		super(context, attSet);
	}

	public static void setMemoryCachingEnabled(boolean enabled) {
		WebImageCache.setMemoryCachingEnabled(enabled);
	}

	public static void setDiskCachingEnabled(boolean enabled) {
		WebImageCache.setDiskCachingEnabled(enabled);
	}

	public static void setDiskCachingDefaultCacheTimeout(int seconds) {
		WebImageCache.setDiskCachingDefaultCacheTimeout(seconds);
	}

	@Override
	public void onDetachedFromWindow() {
		// cancel loading if view is removed
		cancelCurrentLoad();
	}

	public void setImageWithURL(Context context, String urlString, Drawable placeholderDrawable, int diskCacheTimeoutInSeconds) {
	    final WebImageManager mgr = WebImageManager.getInstance();

	    cancelCurrentLoad();
		setImageDrawable(placeholderDrawable);
		
        if (urlString != null) {
            mgr.downloadURL(context, urlString, WebImageView.this, diskCacheTimeoutInSeconds);
        }
	}

	public void setImageWithURL(Context context, String urlString, Drawable placeholderDrawable) {
		setImageWithURL(context, urlString, placeholderDrawable, -1);
	}

	public void setImageWithURL(final Context context, final String urlString, int diskCacheTimeoutInSeconds) {
	    setImageWithURL(context, urlString, null, diskCacheTimeoutInSeconds);
	}

	public void setImageWithURL(final Context context, final String urlString) {
	    setImageWithURL(context, urlString, null, -1);
	}

	public void cancelCurrentLoad() {
	    WebImageManager mgr = WebImageManager.getInstance();
	    // cancel any existing request
	    mgr.cancelForWebImageView(this);
	}

}
