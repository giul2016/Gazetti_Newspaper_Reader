package com.example.try_masterdetail.preference;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.example.try_masterdetail.LicensesActivity;
import com.example.try_masterdetail.R;

public class SettingsActivity extends PreferenceActivity {

	int ActionBarColorId = -1;

	@SuppressLint("NewApi")
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		setupSimplePreferencesScreen();

		ActionBarColorId = getIntent().getIntExtra("ActionBarColor", -1);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);

			if (ActionBarColorId != -1) {
				getActionBar().setDisplayShowTitleEnabled(false);
				getActionBar().setBackgroundDrawable(new ColorDrawable(ActionBarColorId));
				getActionBar().setDisplayShowTitleEnabled(true);
			}
		}

		// Feed Selector
		Preference feedSelectPref = (Preference) findPreference("feedSelectPref");
		feedSelectPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {

				Intent feedIntent = new Intent(SettingsActivity.this, FeedSelectViewpager.class);
				if (ActionBarColorId != -1) {
					feedIntent.putExtra("ActionBarColor", ActionBarColorId);
				}
				startActivity(feedIntent);
				return false;
			}
		});

		// License Section
		Preference licensePref = (Preference) findPreference("licensePref");
		licensePref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {

				Intent licenseIntent = new Intent(SettingsActivity.this, LicensesActivity.class);
				if (ActionBarColorId != -1) {
					licenseIntent.putExtra("ActionBarColor", ActionBarColorId);
				}
				startActivity(licenseIntent);
				return false;
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackPressed();

			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	private void setupSimplePreferencesScreen() {

		// Add 'general' preferences.
		addPreferencesFromResource(R.xml.pref_general);

	}

}
