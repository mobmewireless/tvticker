package in.mobme.tvticker.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.text.format.DateFormat;
import android.util.Log;

import com.ocpsoft.pretty.time.PrettyTime;

public class DateTimeHelper {
	public final static String jsonDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	public final static String dateFormatDb = "yyyy-MM-dd HH:mm:ss";
	public final static String dateFormatLocal = "M dd,yyyy HH:mm:ss";

	public final static String FRAME_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
	public final static int FRAME_NOW = 1;
	public final static int FRAME_LATER = 2;

	public String SanitizeJsonTime(String jsonTime) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(jsonDateFormat);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = format.parse(jsonTime);
		format = new SimpleDateFormat(dateFormatDb);
		format.setTimeZone(TimeZone.getTimeZone(Calendar.getInstance()
				.getTimeZone().getID()));
		return format.format(date);
	}

	public String sanitizeTimeTo(String time, String currentFormat,
			String expectedFormat) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(currentFormat);
		Date date = format.parse(time);
		format = new SimpleDateFormat(expectedFormat);
		return format.format(date);
	}

	public String[] getFormattedMessage(Date show_time_start, Date show_time_end) {
		String message = "";
		String header = "";
		PrettyTime p = new PrettyTime(); // refer :
		// http://ocpsoft.com/prettytime/#docs
		String style = "0";
		Date now = new Date();
		if (now.after(show_time_start) && now.before(show_time_end)) {
			p = new PrettyTime(now);
			header = "now running";
			message = p.format(show_time_end).replaceAll("from now", "left");
		} else if (now.after(show_time_end)) {
			header = "Completed";
			message = p.format(show_time_end);
		} else if (now.before(show_time_start)) {
			header = "coming later";
			message = p.format(show_time_start);
		} 
		 else {
			message = "Unknown";
		}
		if (message.toLowerCase().contains("moments from now")){
			style = "1";
			header = "coming next";
		}
		return new String[] { message, style, header };
	}

	public String[] getStringTimeFrameFor(int frameType) {
		List<String> timeFrames = null;
		switch (frameType) {
		case FRAME_NOW:
			timeFrames = calculateTimeFrame(-1, 2, -1);
			// Collections.reverse(timeFrames);
			break;
		case FRAME_LATER:
			timeFrames = calculateTimeFrame(1, 2, -2);
			break;
		}
		return (String[]) timeFrames.toArray();
	}

	private List<String> calculateTimeFrame(int airtimestart,
			int airtimeend, int endtime) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, airtimestart);
		String airTimeStart = (String) DateFormat.format(FRAME_TIME_FORMAT,
				calendar.getTime());
		
		calendar.add(Calendar.HOUR_OF_DAY, airtimeend);
		String airTimeEnd = (String) DateFormat.format(FRAME_TIME_FORMAT,
				calendar.getTime());
		calendar.add(Calendar.HOUR_OF_DAY, endtime);
		String endTime = (String) DateFormat.format(FRAME_TIME_FORMAT,
				calendar.getTime());
			Log.i("time frame", airTimeStart+" "+ airTimeEnd+" "+ endTime);
		return Arrays
				.asList(new String[] { airTimeStart, airTimeEnd, endTime });
	}

}
