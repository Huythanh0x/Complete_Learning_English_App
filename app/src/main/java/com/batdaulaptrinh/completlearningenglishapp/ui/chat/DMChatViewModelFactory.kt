package com.batdaulaptrinh.completlearningenglishapp.ui.chat

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DMChatViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DMChatViewModel::class.java)) {
            return DMChatViewModel(application) as T
        }
        throw IllegalArgumentException()
    }
}