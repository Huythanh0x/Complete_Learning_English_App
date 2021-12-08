package com.batdaulaptrinh.completlearningenglishapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.model.UserInfo
import com.batdaulaptrinh.completlearningenglishapp.model.UserSettings

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferencesProvider = SharePreferencesProvider(application)
    val settings = MutableLiveData<UserSettings>()
    val userInfo = MutableLiveData<UserInfo>()

    fun loadDataFromLocalMemory() {
        loadUserInoFromLocalMemory()
        loadSettingFromLocalMemory()
    }

    private fun loadSettingFromLocalMemory() {
        settings.value = (UserSettings(
            sharedPreferencesProvider.getPreferAccent(),
            sharedPreferencesProvider.getIsDarkMode()
        ))
    }

    private fun loadUserInoFromLocalMemory() {
        userInfo.value = (UserInfo(
            sharedPreferencesProvider.getFullName(),
            sharedPreferencesProvider.getPhoneNumber(),
            sharedPreferencesProvider.getEmail(),
            sharedPreferencesProvider.getLocation(),
            sharedPreferencesProvider.getJoinedDate()
        ))
    }

    private fun updateUserInfoToMemory(newUserInfo: UserInfo) {
        sharedPreferencesProvider.putFullName(newUserInfo.fullName)
        sharedPreferencesProvider.putPhoneNumber(newUserInfo.phoneNumber)
        sharedPreferencesProvider.putEmail(newUserInfo.email)
        sharedPreferencesProvider.putLocation(newUserInfo.location)
        sharedPreferencesProvider.putJoinedDate(newUserInfo.joinedDate)
    }

    private fun updateUserSettingToMemory(newSetting: UserSettings) {
        sharedPreferencesProvider.putIsDarkMode(newSetting.isDarkMode)
        sharedPreferencesProvider.putPreferAccent(newSetting.preferAccent)
    }

    fun updateSettingFromUser(newSetting: UserSettings) {
        settings.postValue(newSetting)
        updateUserSettingToMemory(newSetting)
    }

    fun updateInfoFromUser(newUserInfo: UserInfo) {
        userInfo.postValue(newUserInfo)
        updateUserInfoToMemory(newUserInfo)
    }
}