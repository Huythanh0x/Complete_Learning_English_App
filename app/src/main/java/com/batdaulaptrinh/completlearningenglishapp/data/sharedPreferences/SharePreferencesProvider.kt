package com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils

class SharePreferencesProvider(val context: Context) {
    private val sharePreferencesProvider = PreferenceManager.getDefaultSharedPreferences(context)

    fun putTimeDelay(timeDelay: Int) {
        sharePreferencesProvider.edit() {
            putInt(Utils.TIME_DELAY, timeDelay)
        }
        sharePreferencesProvider.edit().putInt(Utils.TIME_DELAY, timeDelay).apply()
    }

    fun putTimeOff(timeOff: Int) {
        sharePreferencesProvider.edit() {
            putInt(Utils.TIME_OFF, timeOff)
        }
    }

    fun putIsAutoRepeat(isAutoRepeat: Boolean) {
        sharePreferencesProvider.edit() {
            putBoolean(Utils.IS_LOOP, isAutoRepeat)
        }
        sharePreferencesProvider.edit().putBoolean(Utils.IS_LOOP, isAutoRepeat).apply()
    }

    fun putIsPlaySound(isPlaySound: Boolean) {
        sharePreferencesProvider.edit() {
            putBoolean(Utils.IS_PLAY_SOUND, isPlaySound)
        }
    }

    fun getIsAutoRepeat(): Boolean {
        return sharePreferencesProvider.getBoolean(Utils.IS_LOOP, true)
    }

    fun getTimeDelay(): Int {
        return sharePreferencesProvider.getInt(Utils.TIME_DELAY, 5)
    }

    fun getTimeOff(): Int {
        return sharePreferencesProvider.getInt(Utils.TIME_OFF, 10)
    }

    fun getIsPlaySound(): Boolean {
        return sharePreferencesProvider.getBoolean(Utils.IS_PLAY_SOUND, false)
    }

    fun putEmail(email: String) {
        sharePreferencesProvider.edit().putString(Utils.EMAIL, email).apply()
    }

    fun getEmail(): String {
        return sharePreferencesProvider.getString(Utils.EMAIL, "admin@batdaulaptrinh.com")!!
    }

    fun putPhoneNumber(phoneNumber: String) {
        sharePreferencesProvider.edit().putString(Utils.PHONE_NUMBER, phoneNumber).apply()
    }

    fun getPhoneNumber(): String {
        return sharePreferencesProvider.getString(Utils.PHONE_NUMBER, "0347009370")!!
    }

    fun putLocation(location: String) {
        sharePreferencesProvider.edit().putString(Utils.LOCATION, location).apply()
    }

    fun getLocation(): String {
        return sharePreferencesProvider.getString(Utils.LOCATION, "Viet Nam")!!
    }


    fun putJoinedDate(joinDate: String) {
        sharePreferencesProvider.edit().putString(Utils.JOINED_DATE, joinDate).apply()
    }

    fun getJoinedDate(): String {
        return sharePreferencesProvider.getString(Utils.JOINED_DATE, "10/10/2010")!!
    }


    fun putPreferAccent(preferAccent: String) {
        sharePreferencesProvider.edit().putString(Utils.PREFER_ACCENT, preferAccent).apply()
    }

    fun getPreferAccent(): String {
        return sharePreferencesProvider.getString(Utils.PREFER_ACCENT, "UK")!!
    }


    fun putFullName(fullName: String) {
        sharePreferencesProvider.edit().putString(Utils.FULL_NAME, fullName).apply()
    }

    fun getFullName(): String {
        return sharePreferencesProvider.getString(Utils.FULL_NAME, "Vo Huy Thanh")!!
    }


    fun putIsDarkMode(isDarkMode: Boolean) {
        sharePreferencesProvider.edit().putBoolean(Utils.IS_DARK_MODE, isDarkMode).apply()
    }

    fun getIsDarkMode(): Boolean {
        return sharePreferencesProvider.getBoolean(Utils.IS_DARK_MODE, true)
    }
}
