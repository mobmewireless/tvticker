package in.mobme.tvticker;

import in.mobme.tvticker.R;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class SettingsPreferenceActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_pref);
		Preference customizeReminders = findPreference("customize_reminders");
		final Intent target = new Intent();

		customizeReminders
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						target.setClass(getBaseContext(),
								ReminderPreferenceActivity.class);
						startActivity(target);
						return true;
					}
				});
		Preference fav_channels = findPreference("fav_channels");

		fav_channels
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						target.setClass(getBaseContext(),
								SetFavouriteChannelsActivity.class);
						startActivity(target);
						return true;
					}
				});
	}
}
