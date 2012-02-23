package in.mobme.tvticker;

public class FavouriteChannelModel {
	private long _id;
	private String name;
	private boolean selected;

	public FavouriteChannelModel(long id, String name, boolean isSelected) {
		this._id = id;
		this.name = name;
		selected = isSelected;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
