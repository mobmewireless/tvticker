package in.mobme.tvticker.rpcclient;

import in.mobme.tvticker.data_model.Categories;
import in.mobme.tvticker.data_model.Channels;
import in.mobme.tvticker.data_model.Media;

import org.json.JSONException;
import org.json.JSONObject;

public class MediaJsonParser {
	
	public Media parseJsonMedia(JSONObject mediaObj) throws JSONException{
		Media media = new Media();
		media.setCategoryType(mediaObj.getInt(Constants.RPC.Media.CATEGORY_TAG));
		media.setChannel(mediaObj.getInt(Constants.RPC.Media.CHANNEL_TAG));
		media.setId(mediaObj.getLong(Constants.RPC.Media._ID));
		media.setImdbLink(mediaObj.getString(Constants.RPC.Media.IMDB_INFO_TAG));
		media.setMediaDescription(mediaObj.getString(Constants.RPC.Media.DESCRIPTION_TAG));
		media.setMediaThumb(mediaObj.getString(Constants.RPC.Media.THUMBNAIL_TAG));
		media.setMediaTitle(mediaObj.getString(Constants.RPC.Media.TITLE_TAG));
		media.setSeriesID(mediaObj.getInt(Constants.RPC.Media.SERIES_ID_TAG));
		media.setThumbnailID(mediaObj.getInt(Constants.RPC.Media.THUMBNAIL_ID_TAG));
		media.setShowDuration(mediaObj.getString(Constants.RPC.Media.DURATION_TAG));
		media.setShowEndTime(mediaObj.getString(Constants.RPC.Media.SHOW_TIME_END_TAG));
		media.setShowTime(mediaObj.getString(Constants.RPC.Media.SHOW_TIME_START_TAG));
		return media;
	}
	
	public Channels parseJsonChannel(JSONObject channelObj) throws JSONException{
		Channels channel = new Channels();
		channel.set_id(channelObj.getLong(Constants.RPC.Media._ID));
		channel.setChannelTitle(channelObj.getString(Constants.RPC.Media.TITLE_TAG));
		return channel;
	}
	
	public Categories parseJsonCategory(JSONObject CategoryObj) throws JSONException{
		Categories category = new Categories();
		category.set_id(CategoryObj.getLong(Constants.RPC.Media._ID));
		category.setCategoryName(CategoryObj.getString(Constants.RPC.Media.TITLE_TAG));
		return category;
	}

}
