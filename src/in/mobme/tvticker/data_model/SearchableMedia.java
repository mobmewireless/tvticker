package in.mobme.tvticker.data_model;

//Minimal Version of media class to help Searchable activity
public class SearchableMedia {
	
	public final static int TYPE_SHOW = 0;
	public final static int TYPE_CHANNEL = 1;
	
	private int type;
	private long _id;
	private String media_title;
	
	public long getMedia_id() {
		return _id;
	}
	
	public void setMedia_id(long mediaId) {
		_id = mediaId;
	}
	
	public String getMedia_title() {
		return media_title;
	}
	
	public void setMedia_title(String mediaTitle) {
		media_title = mediaTitle;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
