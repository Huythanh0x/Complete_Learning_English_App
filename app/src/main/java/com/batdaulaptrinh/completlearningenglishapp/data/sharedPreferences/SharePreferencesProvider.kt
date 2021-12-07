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
        sharePreferencesProvider.edit().putInt(Utils.TIME_DELAY, timeDelay).commit()
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
        sharePreferencesProvider.edit().putBoolean(Utils.IS_LOOP, isAutoRepeat).commit()
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

    fun clickPlayButton() {
        putIsPlaySound(!getIsPlaySound())
    }
}
