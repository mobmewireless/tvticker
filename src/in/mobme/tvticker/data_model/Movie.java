package in.mobme.tvticker.data_model;

import in.mobme.tvticker.data_model.rules.Category;
import in.mobme.tvticker.data_model.rules.Channel;
import in.mobme.tvticker.data_model.rules.IMDB;
import in.mobme.tvticker.data_model.rules.Media;

public class Movie implements Media, Category, Channel, IMDB {

	String movieTitle;
	String[] movieThumb;
	String movieDescription;
	String[] showTime;
	String imdbLink;
	float imdbRating;
	int categoryType;
	int[] onChannels;

	//media specific methods
	@Override
	public String[] getShowTime() {
		return showTime;
	}

	@Override
	public String getMediaDescription() {
		return movieDescription;
	}

	@Override
	public String[] getMediaThumb() {
		return movieThumb;
	}

	@Override
	public String getMediaTitle() {
		return movieTitle;
	}

	@Override
	public void setShowTime(String[] showTime) {
		this.showTime = showTime;
	}

	@Override
	public void setMediaDescription(String descritpion) {
		this.movieDescription = descritpion;
	}

	@Override
	public void setMediaThumb(String[] mediaThumb) {
		this.movieThumb = mediaThumb;
	}

	@Override
	public void setMediaTitle(String title) {
		this.movieTitle = title;
	}

	//Category specific
	@Override
	public int getCategoryType() {
		return categoryType;
	}

	@Override
	public void setCategoryType(int type) {
		this.categoryType = type;
	}

	//Channel specific
	@Override
	public int[] getChannels() {
		return onChannels;
	}

	@Override
	public void setChannels(int[] onChannels) {
		this.onChannels = onChannels;
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
