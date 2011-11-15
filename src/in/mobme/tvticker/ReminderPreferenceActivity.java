package in.mobme.tvticker;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

public class ReminderPreferenceActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.reminder_pref);

		Preference firstReminder = findPreference("first_reminder_pref");
		Preference secondReminder = findPreference("second_reminder_pref");
		Preference showReminders =  findPreference("show_current_reminders");
		
		firstReminder.setOnPreferenceChangeListener(new MyPreferenceChangeListener());
		secondReminder.setOnPreferenceChangeListener(new MyPreferenceChangeListener());
		showReminders.setOnPreferenceClickListener(new MyPreferenceClickListener());

	}
	
	
	private class MyPreferenceChangeListener implements OnPreferenceChangeListener{
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			Toast.makeText(getBaseContext(), " Reminder Saved", Toast.LENGTH_SHORT).show();
			return true;
		}	
	}
	
	private class MyPreferenceClickListener implements OnPreferenceClickListener{

		@Override
		public boolean onPreferenceClick(Preference preference) {
			Toast.makeText(getBaseContext(), "Under Construction", Toast.LENGTH_SHORT).show();
			return true;
		}
		
	}
}
