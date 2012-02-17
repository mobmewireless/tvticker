package in.mobme.tvticker.data_model;

public class Channels {
	private long _id;
	private String channelTitle;

	public long get_id() {
		return _id;
	}

	public void set_id(long id) {
		_id = id;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}
}
