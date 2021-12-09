package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.app.Application
import android.media.MediaPlayer
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.util.*


class FlashCardViewModel(val wordRepository: WordRepository, application: Application) :
    AndroidViewModel(application) {
    val listWord = MutableLiveData<List<Word>>()
    var setWordNth = 0
    val lastPosition = MutableLiveData<Int>()
    val currentPosition = MutableLiveData<Int>(1)
    var isAutoPlay = MutableLiveData(false)
    private val sharePreferencesProvider = SharePreferencesProvider(getApplication())
    val timeDelayLiveData = MutableLiveData(sharePreferencesProvider.getTimeDelay())
    val timeOffLiveData = MutableLiveData(sharePreferencesProvider.getTimeOff())
    val isAutoRepeatLiveData = MutableLiveData(sharePreferencesProvider.getIsAutoRepeat())
    val isPlaySoundLiveData = MutableLiveData(sharePreferencesProvider.getIsPlaySound())
    private var timer = Timer()
    private var countDownTime = sharePreferencesProvider.getTimeOff() * 60
    private val mediaPlayer = MediaPlayer()

    fun getSetWordNth(nTh: Int) {
        listWord.postValue(wordRepository.getFakeSetWord(nTh))
        setWordNth = nTh
    }

    fun clickPlayButton() {
        if (isAutoPlay.value!!) {
            pauseAutoPlay()
        } else {
            startAutoPlay()
        }
    }

    private fun startAutoPlay() {
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

    private fun pauseAutoPlay() {
        timer.cancel()
        timer.purge()
        isAutoPlay.postValue(false)
    }

    fun setCurrentPosition(newPosition: Int) {
        if (currentPosition.value!! != newPosition) {
            lastPosition.postValue(currentPosition.value!!)
            currentPosition.postValue(newPosition)
        }
    }

    fun autoMoveToNextPosition() {
        val _currentPosition = currentPosition.value!!
        val _maxPosition = listWord.value?.size ?: 20
        val _isLoop = sharePreferencesProvider.getIsAutoRepeat()
        if (!isAutoPlay.value!!) {
            timer.cancel()
            timer.purge()
        } else if (_currentPosition + 1 < _maxPosition) {
            setCurrentPosition(_currentPosition + 1)
        } else if (!_isLoop && _currentPosition + 1 >= _maxPosition) {
            setCurrentPosition(0)
            isAutoPlay.postValue(false)
        } else if (_isLoop) {
            setCurrentPosition((_currentPosition + 1) % _maxPosition)
        }
        countDownTime -= timeDelayLiveData.value!!
        if (countDownTime <= 0) {
            pauseAutoPlay()
        }
    }

    fun moveToNextPosition() {
        val _currentPosition = currentPosition.value!!
        val _maxPosition = listWord.value?.size ?: 20
        if (_currentPosition + 1 < _maxPosition) {
            setCurrentPosition(_currentPosition + 1)
        }
    }

    fun moveToPreviousPosition() {
        val _currentPosition = currentPosition.value!!
        setCurrentPosition(_currentPosition - 1)
    }

    fun clickPlaySound() {
        playCurrentSoundWord()
    }

    fun changePositionPlaySound() {
        if (isPlaySoundLiveData.value!!) {
            playCurrentSoundWord()
        }
    }

    private fun playCurrentSoundWord() {
        val _currentPosition = currentPosition.value!!
        val mp3Us = listWord.value?.get(_currentPosition)?.mp3_us ?: return
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val base64String = getByteArrayFromImageURL(mp3Us)
                if (base64String != null) {
                    playAudio(base64String)
                }
            }
        } catch (e: Exception) {
            Log.e("response", e.toString())
        }
    }

    fun saveSettings() {
        sharePreferencesProvider.putTimeDelay(timeDelayLiveData.value!!)
        sharePreferencesProvider.putTimeOff(timeOffLiveData.value!!)
        sharePreferencesProvider.putIsAutoRepeat(isAutoRepeatLiveData.value!!)
        sharePreferencesProvider.putIsPlaySound(isPlaySoundLiveData.value!!)
        if (isAutoPlay.value!!) {
            updateSettingWhileAuto()
        }
    }

    private fun updateSettingWhileAuto() {
        timer.cancel()
        timer.purge()
        val timeDelay = (sharePreferencesProvider.getTimeDelay() * 1000).toLong()
        val runnableSlide = object : TimerTask() {
            override fun run() {
                autoMoveToNextPosition()
            }
        }
        timer = Timer()
        timer.schedule(runnableSlide, timeDelay, timeDelay)
    }

    fun discardSettings() {
        timeDelayLiveData.postValue(sharePreferencesProvider.getTimeDelay())
        timeOffLiveData.postValue(sharePreferencesProvider.getTimeOff())
        isAutoRepeatLiveData.postValue(sharePreferencesProvider.getIsAutoRepeat())
        isPlaySoundLiveData.postValue(sharePreferencesProvider.getIsPlaySound())
    }


    fun setIsAutoRepeat(isAutoRepeat: Boolean) {
        isAutoRepeatLiveData.value = isAutoRepeat
    }

    fun setIsPlaySound(isPlaySound: Boolean) {
        isPlaySoundLiveData.value = isPlaySound
    }

    fun setTimeOff(timeToOff: Int) {
        timeOffLiveData.value = timeToOff
    }

    fun setTimeDelay(timeToDelay: Int) {
        timeDelayLiveData.value = timeToDelay
    }

    private fun getByteArrayFromImageURL(url: String): String? {
        try {
            val imageUrl = URL(url)
            val urlConnection: URLConnection = imageUrl.openConnection()
            val inputStream: InputStream = urlConnection.getInputStream()
            val bytesOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer, 0, buffer.size).also { read = it } != -1) {
                bytesOutputStream.write(buffer, 0, read)
            }
            bytesOutputStream.flush()
            return Base64.encodeToString(bytesOutputStream.toByteArray(), Base64.DEFAULT)
                .filter { !it.isWhitespace() }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
        return null
    }

    private fun playAudio(base64EncodedString: String) {
        try {
            val url = "data:audio/mp3;base64,$base64EncodedString"
            mediaPlayer.reset()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { mediaPlayer ->
                mediaPlayer.reset()
            }
        } catch (ex: Exception) {
            print(ex.message)
        }
    }


}
