package in.mobme.tvticker;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class SettingsPreferenceActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_pref);
		Preference customizeReminders = findPreference("customize_reminders");
		
		customizeReminders.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Intent reminderIntent = new Intent(getBaseContext(), ReminderPreferenceActivity.class);
				startActivity(reminderIntent);
				return true;
			}
		});
	}
}
