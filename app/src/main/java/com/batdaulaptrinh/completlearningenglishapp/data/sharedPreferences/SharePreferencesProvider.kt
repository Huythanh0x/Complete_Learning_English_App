package com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils

class SharePreferencesProvider(val context: Context) {
    private val sharePreferencesProvider = PreferenceManager.getDefaultSharedPreferences(context)
    fun putIsLoop(isLoop: Boolean) {
        sharePreferencesProvider.edit() {
            putBoolean(Utils.IS_LOOP, isLoop)
        }
    }

    fun putTimeDelay(timeDelay: Int) {
        sharePreferencesProvider.edit() {
            putInt(Utils.TIME_DELAY, timeDelay)
        }
    }

    fun putTimeOff(timeOff: Int) {
        sharePreferencesProvider.edit() {
            putInt(Utils.TIME_OFF, timeOff)
        }
    }

    fun putAutoRepeat(isAutoRepeat: Boolean) {
        sharePreferencesProvider.edit() {
            putBoolean(Utils.IS_LOOP, isAutoRepeat)
        }
    }

    fun putPlaySound(isPlaySound: Boolean) {
        sharePreferencesProvider.edit() {
            putBoolean(Utils.IS_PLAY_SOUND, isPlaySound)
        }
    }

    fun getIsLoop(): Boolean {
        return sharePreferencesProvider.getBoolean(Utils.IS_LOOP, false)
    }

    fun getTimeDelay(): Int {
        return sharePreferencesProvider.getInt(Utils.TIME_DELAY, 5)
    }

    fun getTimeOff(): Int {
        return sharePreferencesProvider.getInt(Utils.TIME_OFF, 10)
    }

    fun getAutoRepeat(): Boolean {
        return sharePreferencesProvider.getBoolean(Utils.AUTO_REPEAT, false)
    }

    fun getIsPlaySound(): Boolean {
        return sharePreferencesProvider.getBoolean(Utils.IS_PLAY_SOUND, false)
    }

    fun clickPlayButton() {
        putPlaySound(!getIsPlaySound())
    }


}
