package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import java.util.*


class FlashCardViewModel(val wordRepository: WordRepository, application: Application) :
    AndroidViewModel(application) {
    val listWord = MutableLiveData<List<Word>>()
    val currentPosition = MutableLiveData(0)
    var isAutoPlay = MutableLiveData(false)
    private val sharePreferencesProvider = SharePreferencesProvider(getApplication())
    private var timer = Timer()
    fun getSetWordNth(nTh: Int) {
        listWord.postValue(wordRepository.getFakeSetWord(nTh))
    }

    fun clickPlayButton() {
        if (isAutoPlay.value!!) {
            pauseAutoPlay()
        } else {
            startAutoPlay()
        }
    }

    fun startAutoPlay() {
        val timeDelay = (sharePreferencesProvider.getTimeDelay() * 1000).toLong()
        val runnableSlide = object : TimerTask() {
            override fun run() {
                autoMoveToNextPosition()
            }
        }
        timer = Timer()
        timer.schedule(runnableSlide, timeDelay, timeDelay)
        isAutoPlay.value = true

    }

    fun pauseAutoPlay() {
        timer.cancel()
        timer.purge()
        isAutoPlay.value = false
    }

    fun setCurrentPosition(newPosition: Int) {
        currentPosition.postValue(newPosition)
    }

    fun autoMoveToNextPosition() {
        val _currentPosition = currentPosition.value!!
        val _maxPosition = listWord.value?.size?.minus(1)
        val _isLoop = sharePreferencesProvider.getIsLoop()
        //TODO SHOULD OBSERVE CHANGE AND CALL PAUSE FROM FRAGMENT
        if (!isAutoPlay.value!!) {
            timer.cancel()
            timer.purge()
        } else if (!_isLoop && _currentPosition < _maxPosition!!) {
            currentPosition.postValue(_currentPosition + 1)
        } else if (!_isLoop && _currentPosition >= _maxPosition!!) {
            currentPosition.postValue(0)
            isAutoPlay.postValue(false)
        } else if (_isLoop) {
            currentPosition.postValue((_currentPosition + 1) % _maxPosition!!)
        }
    }

    fun moveToNextPosition() {
        val _currentPosition = currentPosition.value!!
        val _maxPosition = listWord.value?.size?.minus(1)
        val _isLoop = sharePreferencesProvider.getIsLoop()
        if (!_isLoop && _currentPosition < _maxPosition!!) {
            currentPosition.postValue(_currentPosition + 1)
        } else if (_isLoop) {
            currentPosition.postValue((_currentPosition + 1) % _maxPosition!!)
        }
    }

    fun moveToPreviousPosition() {
        currentPosition.postValue(currentPosition.value?.minus(1))
    }


}