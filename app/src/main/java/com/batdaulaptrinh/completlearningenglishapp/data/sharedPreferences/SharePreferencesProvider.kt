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

    fun putCurrentLevel(currentLevel: String) {
        sharePreferencesProvider.edit().putString(Utils.CURRENT_LEVEL, currentLevel).apply()
    }

    fun getCurrentLevel(): String {
        return sharePreferencesProvider.getString(Utils.CURRENT_LEVEL, "BEGINNER")!!
    }

    fun putLearnedWord(learnedWord: Int) {
        sharePreferencesProvider.edit().putInt(Utils.LEARNED_WORD, learnedWord).apply()
    }

    fun getLearnedWord(): Int {
        return sharePreferencesProvider.getInt(Utils.LEARNED_WORD, 0)
    }

    fun putLearnDate(learnedWord: Int) {
        sharePreferencesProvider.edit().putInt(Utils.LEARNED_DATE, learnedWord).apply()
    }

    fun getLearnedDate(): Int {
        return sharePreferencesProvider.getInt(Utils.LEARNED_DATE, 0)
    }

    fun putCurrentStreak(currentStreak: Int) {
        sharePreferencesProvider.edit().putInt(Utils.CURRENT_STREAK, currentStreak).apply()
    }

    fun getCurrentStreak(): Int {
        return sharePreferencesProvider.getInt(Utils.CURRENT_STREAK, 0)
    }

    fun putLongestStreak(longestStreak: String) {
        sharePreferencesProvider.edit().putString(Utils.LONGEST_STREAK, longestStreak).apply()
    }

    fun getLongestStreak(): String {
        return sharePreferencesProvider.getString(Utils.LONGEST_STREAK, "0")!!
    }

    fun putPersonalGoal(myGoal: Int) {
        sharePreferencesProvider.edit().putInt(Utils.MY_GOAL, myGoal).apply()
    }

    fun getPersonalGoal(): Int {
        return sharePreferencesProvider.getInt(Utils.MY_GOAL, 20)
    }

    fun putCurrentSetNth(currentSetNth: Int) {
        sharePreferencesProvider.edit().putInt(Utils.CURRENT_LEVEL, currentSetNth).apply()
    }

    fun getCurrentSetNth(): Int {
        return sharePreferencesProvider.getInt(Utils.CURRENT_SET_NTH, 1)
    }

    fun putLoopNotificationTime(minuteTimeCycle: Int) {
        sharePreferencesProvider.edit().putInt(Utils.MINUTE_TIME_CYCLE, minuteTimeCycle).apply()
    }

    fun getLoopNotification(): Int {
        return sharePreferencesProvider.getInt(Utils.MINUTE_TIME_CYCLE, 1)
    }

    fun putReminderTime(reminderTime: Int) {
        sharePreferencesProvider.edit().putInt(Utils.REMINDER_TIME, reminderTime).apply()
    }

    fun getReminderTime(): Int {
        return sharePreferencesProvider.getInt(Utils.REMINDER_TIME, 1)
    }

    fun getPreferLearningTime(): String {
        return sharePreferencesProvider.getString(Utils.PREFER_LEARNING_TIME, "20:00")!!
    }

    fun putPreferLearningTime(preferLearningTime: String) {
        sharePreferencesProvider.edit().putString(Utils.PREFER_LEARNING_TIME, preferLearningTime)
            .apply()
    }

    fun getRangeNotificationTime(): String {
        return sharePreferencesProvider.getString(Utils.PREFER_LEARNING_TIME, "7:00,22:00")!!
    }

    fun putRangeNotificationTime(rangeNotification: String) {
        sharePreferencesProvider.edit().putString(Utils.PREFER_LEARNING_TIME, rangeNotification)
            .apply()
    }

}
