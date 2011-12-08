package in.mobme.tvticker.data_model;

import in.mobme.tvticker.data_model.rules.Category;
import in.mobme.tvticker.data_model.rules.Channel;
import in.mobme.tvticker.data_model.rules.IMDB;
import in.mobme.tvticker.data_model.rules.MediaBase;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Media implements MediaBase, Category, Channel, IMDB, Serializable {

	long _id;
	String mediaTitle;
	String mediaThumb;
	String mediaDescription;
	String showTime;
	String imdbLink;
	String showEndTime;
	String showDuration;
	float imdbRating;
	int categoryType;
	int onChannel;
	int seriesId;
	boolean isFavorite = false;

	public void setId(long _id){
		this._id = _id;
	}
	
	public long getId(){
		return _id;
	}
	
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
	
	@Override
	public boolean isFavorite() {
		return isFavorite;
	}

	@Override
	public void setIsFavorite(boolean isFav) {
		this.isFavorite = isFav;
	}
	
	@Override
	public String getShowDuration() {
		return showDuration;
	}
	
	@Override
	public void setShowDuration(String showDuration) {
		this.showDuration = showDuration;
	}

	@Override
	public String getShowEndTime() {
		return showEndTime;
	}
	
	@Override
	public void setShowEndTime(String showEndTime) {
		this.showEndTime = showEndTime;
	}
	
	@Override
	public int getSeriesID() {
		return seriesId;
	}

	@Override
	public void setSeriesID(int seriesId) {
		this.seriesId = seriesId;
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
	public int getChannel() {
		return onChannel;
	}

	@Override
	public void setChannel(int onChannel) {
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
