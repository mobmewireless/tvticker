package in.mobme.tvticker.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.ocpsoft.pretty.time.PrettyTime;

public class DateHelper {
	public final static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	public final static String dateFormatForDb = "yyyy-MM-dd HH:mm:ss";
	public final static String dateFormattemp = "M dd,yyyy HH:mm:ss";

	public static String SanitizeJsonTime(String jsonTime) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date =  format.parse(jsonTime);
		format = new SimpleDateFormat(dateFormatForDb);
		format.setTimeZone(TimeZone.getTimeZone(Calendar.getInstance().getTimeZone().getID()));
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

}
