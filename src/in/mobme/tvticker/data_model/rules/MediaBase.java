package in.mobme.tvticker.data_model.rules;

/**
 * Super Interface for all the media models defined. -> Attributes
 * --------------------- 
 * 	t.string :name 
 *  t.integer :category_id 
 *  t.integer :series_id 
 *  t.integer :channel_id 
 *  t.datetime :air_time_start 
 *  t.datetime :air_time_end 
 *  t.datetime :run_time 
 *  t.text :description
 * 	
 * ---------------------
 **/
public interface MediaBase {

	String getShowTime();

	void setShowTime(String showTime);

	String getMediaTitle();

	void setMediaTitle(String title);

	String getMediaDescription();

	void setMediaDescription(String descritpion);

	String getMediaThumb();

	void setMediaThumb(String mediaThumb);

	void setShowEndTime(String showEndTime);

	String getShowEndTime();

	void setShowDuration(String showDuration);

	String getShowDuration();
	
	void setSeriesID(int seriesId);

	int getSeriesID();
	
	void setThumbnailID(int thumbnailId);

	int getThumbnailID();
	

}
