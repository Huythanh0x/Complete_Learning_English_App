package com.batdaulaptrinh.completlearningenglishapp.ui.introduction

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class EntranceTestViewModelFactory(
    val application: Application,
    val wordRepository: WordRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntranceTestViewModel::class.java)) {
            return EntranceTestViewModel(application, wordRepository) as T
        }
        throw IllegalArgumentException()
    }
}