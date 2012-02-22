package in.mobme.tvticker.helpers;



import in.mobme.tvticker.rpcclient.Constants;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.text.format.DateFormat;

import com.ocpsoft.pretty.time.PrettyTime;

public class DateTimeHelper {
	public final static String jsonDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	public final static String dateFormatDb = "yyyy-MM-dd HH:mm:ss";
	public final static String dateFormatLocal = "M dd,yyyy HH:mm:ss";
	
	public final static String FRAME_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
	public final static int FRAME_NOW = 1;
	public final static int FRAME_LATER = 2;

	
	public String SanitizeJsonTime(String jsonTime)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(jsonDateFormat);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = format.parse(jsonTime);
		format = new SimpleDateFormat(dateFormatDb);
		format.setTimeZone(TimeZone.getTimeZone(Calendar.getInstance()
				.getTimeZone().getID()));
		Calendar.getInstance().getTimeZone();
		return format.format(date);
	}

	public String getFormattedMessage(Date show_time_start, Date show_time_end) {
		String message = "";
		PrettyTime p = new PrettyTime(); // refer :
											// http://ocpsoft.com/prettytime/#docs
		int style = 0;
		Date now = new Date();
		if (now.after(show_time_start) && now.before(show_time_end)) {
			message = p.format(show_time_end);
		} else if (now.before(show_time_start)) {
			long timediff = (show_time_start.getTime() - now.getTime());

			message = p.format(show_time_start);
		} else if (now.after(show_time_start)) {
			message = "Finished";
		} else {
			message = "Unknown";
		}
		return message;
	}
	
	public String[] getStringTimeFrameFor(int frameType){
		List<String> timeFrames = null;
		switch(frameType){
		case FRAME_NOW:
			timeFrames = calculateTimeFrame(1, -2);
			Collections.reverse(timeFrames);
			break;
		case FRAME_LATER:
			timeFrames = calculateTimeFrame(1, 2);
			break;
		}
		return (String[])timeFrames.toArray();
	}
	
	private List<String> calculateTimeFrame(int start, int end){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, start);
		String startTime = (String) DateFormat.format(
				FRAME_TIME_FORMAT, calendar.getTime());
		calendar.add(Calendar.HOUR, end);
		String endTime = (String) DateFormat.format(
				FRAME_TIME_FORMAT, calendar.getTime());
		return Arrays.asList(new String[]{startTime, endTime});
	}

}
