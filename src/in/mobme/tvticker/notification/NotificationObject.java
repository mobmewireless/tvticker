package in.mobme.tvticker.notification;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationObject implements Parcelable {
	
	
	private String showName;
	private Date showTime;
	private String channelName;
	
	private static final String NOTIFICATION_TEXT = " starts soon in ";
	public static final String EXTRAS_FLAG = "notification_object";
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public Date getShowTime() {
		return showTime;
	}

	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}

	public String generateMessage() {
		String message = showName + NOTIFICATION_TEXT + channelName;
		return message;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(showName);
		dest.writeString(channelName);
		dest.writeValue(showTime);
	}
	
	public static final Parcelable.Creator<NotificationObject> CREATOR = new Parcelable.Creator<NotificationObject>() {

		@Override
		public NotificationObject createFromParcel(Parcel source) {
			NotificationObject n = new NotificationObject();
			n.setShowName(source.readString());
			n.setChannelName(source.readString());
			n.setShowTime((Date) source.readValue(null));
			return n;
		}

		@Override
		public NotificationObject[] newArray(int size) {
			return new NotificationObject[size];
		}
	
	};

	
}
