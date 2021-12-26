package com.batdaulaptrinh.completlearningenglishapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferencesProvider = SharePreferencesProvider(application)
    val fullNameLiveData = MutableLiveData(sharedPreferencesProvider.getFullName())
    val phoneNumberLiveData = MutableLiveData(sharedPreferencesProvider.getPhoneNumber())
    val emailLiveData = MutableLiveData(sharedPreferencesProvider.getEmail())
    val locationLiveData = MutableLiveData(sharedPreferencesProvider.getLocation())
    val joinedDateLiveData = MutableLiveData(sharedPreferencesProvider.getJoinedDate())
    val preferAccentLiveData = MutableLiveData(sharedPreferencesProvider.getPreferAccent())
    val isDarkModeLiveData = MutableLiveData(sharedPreferencesProvider.getIsDarkMode())
    val personalGoalLiveData = MutableLiveData(sharedPreferencesProvider.getPersonalGoal())
    val preferLearningTimeLiveData =
        MutableLiveData(sharedPreferencesProvider.getPreferLearningTime())
    val loopNotificationLiveData = MutableLiveData(sharedPreferencesProvider.getLoopNotification())
    val rangeNotificationTimeLiveData =
        MutableLiveData(sharedPreferencesProvider.getRangeNotificationTime())
    val isPersonalExpanded = MutableLiveData<Boolean>(false)
    val isSettingsExpanded = MutableLiveData<Boolean>(false)

    fun fetchDataFromLocalMemory() {
        fullNameLiveData.postValue(sharedPreferencesProvider.getFullName())
        phoneNumberLiveData.postValue(sharedPreferencesProvider.getPhoneNumber())
        emailLiveData.postValue(sharedPreferencesProvider.getEmail())
        locationLiveData.postValue(sharedPreferencesProvider.getLocation())
        joinedDateLiveData.postValue(sharedPreferencesProvider.getJoinedDate())
        preferAccentLiveData.postValue(sharedPreferencesProvider.getPreferAccent())
        isDarkModeLiveData.postValue(sharedPreferencesProvider.getIsDarkMode())
    }

    fun updateCurrentLocation(): String {
        val location =
            getApplication<Application>().resources.configuration.locale.displayLanguage
        putLocation(location)
        return location
    }

    fun putFullName(fullName: String) {
        sharedPreferencesProvider.putFullName(fullName)
        fullNameLiveData.postValue(fullName)
    }

    fun putPhoneNumber(phoneNumber: String) {
        sharedPreferencesProvider.putPhoneNumber(phoneNumber)
        phoneNumberLiveData.postValue(phoneNumber)
    }

    fun putEmail(email: String) {
        sharedPreferencesProvider.putEmail(email)
        emailLiveData.postValue(email)
    }

    fun putLocation(location: String) {
        sharedPreferencesProvider.putLocation(location)
        locationLiveData.postValue(location)
    }

    fun putJoinedDate(joinedDate: String) {
        sharedPreferencesProvider.putJoinedDate(joinedDate)
        joinedDateLiveData.postValue(joinedDate)
    }

    fun putPreferAccent(position: Int) {
        var preferAccent = "US"
        if (position == 1) preferAccent = "UK"
        sharedPreferencesProvider.putPreferAccent(preferAccent)
        preferAccentLiveData.postValue(preferAccent)
    }

    fun putDarMode(isDarkMode: Boolean) {
        sharedPreferencesProvider.putIsDarkMode(isDarkMode)
        isDarkModeLiveData.postValue(isDarkMode)
    }

    fun putPersonalGoal(position: Int) {
        var personalGoal = 20
        when (position) {
            0 -> personalGoal = 10
            1 -> personalGoal = 20
            2 -> personalGoal = 30
        }
        sharedPreferencesProvider.putPersonalGoal(personalGoal)
        personalGoalLiveData.postValue(personalGoal)
    }

    fun getPreferAccentPosition(): Int {
        return when (preferAccentLiveData.value!!) {
            "US" -> 0
            "UK" -> 1
            else -> 0
        }
    }

    fun getPersonalGoalPosition(): Int {
        return when (personalGoalLiveData.value!!) {
            10 -> 0
            20 -> 1
            30 -> 2
            else -> 20
        }
    }

    fun collapsePersonalInfo() {
        isPersonalExpanded.postValue(false)
    }

    fun expandPersonalInfo() {
        isPersonalExpanded.postValue(true)
        if (isSettingsExpanded.value!!) {
            collapseSetting()
        }
    }

    fun collapseSetting() {
        isSettingsExpanded.postValue(false)
    }

    fun expandSetting() {
        isSettingsExpanded.postValue(true)
        if (isPersonalExpanded.value!!) {
            collapsePersonalInfo()
        }
    }

    fun putPreferLearningTime(preferLearningTime: String) {
        var hourOfDay = preferLearningTime.split(":")[0]
        var minute = preferLearningTime.split(":")[1]
        if (minute.toInt() < 10) {
            minute = "0$minute"
        }
        if(hourOfDay.toInt() < 10){
            hourOfDay = "0$hourOfDay"
        }
        preferLearningTimeLiveData.postValue("$hourOfDay:$minute")
        sharedPreferencesProvider.putPreferLearningTime("$hourOfDay:$minute")
    }

    fun putRangeNotification(rangeNotificationTime: String) {
        sharedPreferencesProvider.putRangeNotificationTime(rangeNotificationTime)
        rangeNotificationTimeLiveData.postValue(rangeNotificationTime)
    }

    fun putLoopNotification(position: Int) {
        val loopNotificationTime = when (position) {
            0 -> 15
            1 -> 30
            2 -> 45
            3 -> 60
            4 -> 90
            5 -> 120
            else -> 30
        }
        sharedPreferencesProvider.putLoopNotificationTime(loopNotificationTime)
    }

}