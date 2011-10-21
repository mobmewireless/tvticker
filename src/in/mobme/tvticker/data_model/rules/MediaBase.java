package in.mobme.tvticker.data_model.rules;

/**
 * Super Interface for all the media models defined
 **/
public interface MediaBase {

	String[] getShowTime();

	void setShowTime(String[] showTime);

	String getMediaTitle();

	void setMediaTitle(String title);

	String getMediaDescription();

	void setMediaDescription(String descritpion);

	String getMediaThumb();

	void setMediaThumb(String mediaThumb);

}