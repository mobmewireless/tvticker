package in.mobme.data_model;

import in.mobme.data_model.rules.Category;
import in.mobme.data_model.rules.Channel;
import in.mobme.data_model.rules.Media;

public class Sports implements Media, Category, Channel {

	String sportsTitle;
	String sportsDescription;
	String[] sportsThumb;
	String[] showTime;
	int categoryType;
	int[] onChannels;


	@Override
	public String[] getShowTime() {
		return showTime;
	}

	@Override
	public String getMediaDescription() {
		return sportsDescription;
	}

	@Override
	public String[] getMediaThumb() {
		return sportsThumb;
	}

	@Override
	public String getMediaTitle() {
		return sportsTitle;
	}

	@Override
	public void setShowTime(String[] showTime) {
		this.showTime = showTime;
	}

	@Override
	public void setMediaDescription(String descritpion) {
		this.sportsDescription = descritpion;
	}

	@Override
	public void setMediaThumb(String[] mediaThumb) {
		this.sportsThumb = mediaThumb;
	}

	@Override
	public void setMediaTitle(String title) {
		this.sportsTitle = title;
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

}
