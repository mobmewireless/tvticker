package in.mobme.tvticker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;

public class ReminderPreferenceActivity extends PreferenceActivity {

	Preference first_reminder;
	Preference second_reminder;
	boolean isSeconderReminderEnabled = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.reminder_pref);
		
		first_reminder = (Preference) findPreference("first_reminder_pref");
		first_reminder.setOnPreferenceChangeListener(new MyPreferenceChangeListener());
		second_reminder = (Preference) findPreference("second_reminder_pref");

		second_reminder = (Preference) findPreference("second_reminder_pref");
		//disable second, if first one is disabled !
		isSeconderReminderEnabled = isFirstReminderEnabled();
		second_reminder.setEnabled(isSeconderReminderEnabled);

		Preference list_shows = (Preference) findPreference("show_current_reminders");
		list_shows.setOnPreferenceClickListener(new MyPreferenceClickListener());

	}

	// returns true if first reminder is enabled, false otherwise.
	private boolean isFirstReminderEnabled() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		String key = sharedPrefs.getString("first_reminder_pref", "0");
		return key.equals("0") ? false : true;
	}

	// Change listener to turn off second reminder, if first one is disabled.
	private class MyPreferenceChangeListener implements OnPreferenceChangeListener {
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			isSeconderReminderEnabled = newValue.toString().equals("0") ? false : true;
			second_reminder.setEnabled(isSeconderReminderEnabled);
			return true;
		}
	}

	//Handles preference click operation.
	private class MyPreferenceClickListener implements OnPreferenceClickListener {
		@Override
		public boolean onPreferenceClick(Preference preference) {
			Intent listShows = new Intent(getBaseContext(), EditShowRemindersActivity.class);
			listShows.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(listShows);
			return true;
		}
	}
	
//	private Spannable ColorifyTextWith(int textId, int color){
//		String text = getResources().getString(textId);
//		Spannable summary = new SpannableString ( text );
//		summary.setSpan( new ForegroundColorSpan( color ), 0, summary.length(), 0 );
//		return summary;
//	}

}
