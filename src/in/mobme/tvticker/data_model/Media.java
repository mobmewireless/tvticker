package in.mobme.tvticker.data_model;

import in.mobme.tvticker.data_model.rules.Category;
import in.mobme.tvticker.data_model.rules.Channel;
import in.mobme.tvticker.data_model.rules.IMDB;
import in.mobme.tvticker.data_model.rules.MediaBase;

public class Media implements MediaBase, Category, Channel, IMDB {

	String mediaTitle;
	String mediaThumb;
	String mediaDescription;
	String showTime;
	String imdbLink;
	float imdbRating;
	String categoryType;
	String onChannel;

	//media specific methods
	@Override
	public String getShowTime() {
		return showTime;
	}

	@Override
	public String getMediaDescription() {
		return mediaDescription;
	}

	@Override
	public String getMediaThumb() {
		return mediaThumb;
	}

	@Override
	public String getMediaTitle() {
		return mediaTitle;
	}

	@Override
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	@Override
	public void setMediaDescription(String descritpion) {
		this.mediaDescription = descritpion;
	}

	@Override
	public void setMediaThumb(String mediaThumb) {
		this.mediaThumb = mediaThumb;
	}

	@Override
	public void setMediaTitle(String title) {
		this.mediaTitle = title;
	}

	//Category specific
	@Override
	public String getCategoryType() {
		return categoryType;
	}

	@Override
	public void setCategoryType(String type) {
		this.categoryType = type;
	}

	//Channel specific
	@Override
	public String getChannel() {
		return onChannel;
	}

	@Override
	public void setChannel(String onChannel) {
		this.onChannel = onChannel;
	}
	
	//Imdb specific
	@Override
	public String getImdbLink() {
		return imdbLink;
	}

	@Override
	public void setImdbLink(String imdbLink) {
		this.imdbLink = imdbLink;
	}

	@Override
	public float getImdbRating() {
		return imdbRating;
	}

	@Override
	public void setImdbRating(float imdbRating) {
		this.imdbRating = imdbRating;
	}

}
