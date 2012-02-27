package in.mobme.tvticker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class ReminderPreferenceActivity extends PreferenceActivity {

	Preference firstReminder;
	Preference secondReminder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.reminder_pref);
		
		firstReminder = (Preference) findPreference(Constants.PREF_FIRST_REMINDER);
		firstReminder.setOnPreferenceChangeListener(new MyPreferenceChangeListener());
		secondReminder = (Preference) findPreference(Constants.PREF_SECOND_REMINDER);

		secondReminder.setEnabled(isFirstReminderEnabled());
	}

	// returns true if first reminder is enabled, false otherwise.
	private boolean isFirstReminderEnabled() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		System.out.println(sharedPrefs.getAll().toString());
		int key = Integer.parseInt(sharedPrefs.getString(Constants.PREF_FIRST_REMINDER, "1"));
		return key != 0;
	}

	// Change listener to turn off second reminder, if first one is disabled.
	private class MyPreferenceChangeListener implements OnPreferenceChangeListener {
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			boolean enableSecondReminder = !((String) newValue).equals("0");
			secondReminder.setEnabled(enableSecondReminder);
			return true;
		}
	}

}
