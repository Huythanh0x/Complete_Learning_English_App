package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository


class FlashCardViewModel(val wordRepository: WordRepository, application: Application) :
    AndroidViewModel(application) {
    val listWord = MutableLiveData<List<Word>>()
    val currentFlashCardPosition = MutableLiveData(0)
    var isAutoPlay = MutableLiveData(false)

    private val sharePreferencesProvider = SharePreferencesProvider(getApplication())
    var isPlaySound = (sharePreferencesProvider.getIsPlaySound())
    var isLoop = (sharePreferencesProvider.getIsLoop())
    var timeDelay = sharePreferencesProvider.getTimeDelay()
    var timeOff = sharePreferencesProvider.getTimeOff()

    fun getSetWordNth(nTh: Int) {
        listWord.postValue(wordRepository.getFakeSetWord(nTh))
    }

    fun setCurrentPosition(newPosition: Int) {
        currentFlashCardPosition.postValue(newPosition)
    }


    fun moveToNextPosition() {
        val currentPosition = currentFlashCardPosition.value
        val maxPosition = listWord.value?.size?.minus(1)
        if (currentPosition != null) {
            if (!isLoop && currentPosition < maxPosition!!) {
                currentFlashCardPosition.postValue(currentPosition + 1)
            } else if (!isLoop && currentPosition >= maxPosition!!) {
                currentFlashCardPosition.postValue(0)
                isAutoPlay.postValue(false)
            } else if (isLoop) {
                currentFlashCardPosition.postValue((currentPosition + 1) % maxPosition!!)
            }
        }
    }

    fun moveToPreviousPosition() {
        currentFlashCardPosition.postValue(currentFlashCardPosition.value?.minus(1))
    }

}