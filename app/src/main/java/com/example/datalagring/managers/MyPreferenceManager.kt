package com.example.datalagring.managers

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.datalagring.R

class MyPreferenceManager(private val activity: AppCompatActivity) {

	private val resources = activity.resources
	private val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
	private val editor: SharedPreferences.Editor = preferences.edit()

	fun putString(key: String, value: String) {
		editor.putString(key, value)
		editor.apply()
	}

	fun getString(key: String, defaultValue: String): String {
		return preferences.getString(key, defaultValue) ?: defaultValue
	}

	fun updateBackgroundColor() {
		val backgroundValues = resources.getStringArray(R.array.background_color_values)
		val value = getString(
				resources.getString(R.string.background_mode),
				resources.getString(R.string.background_color_default_value)
		)
		when (value) {
			backgroundValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
			backgroundValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
			backgroundValues[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
			backgroundValues[3] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
		}
	}

	fun registerListener(activity: SharedPreferences.OnSharedPreferenceChangeListener) {
		preferences.registerOnSharedPreferenceChangeListener(activity)
	}

	fun unregisterListener(activity: SharedPreferences.OnSharedPreferenceChangeListener) {
		preferences.unregisterOnSharedPreferenceChangeListener(activity)
	}
}
