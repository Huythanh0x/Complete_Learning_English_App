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
    val currentPosition = MutableLiveData<Int>()
    var isAutoPlay = MutableLiveData<Boolean>(false)
    private val sharePreferencesProvider = SharePreferencesProvider(getApplication())
    private var timer = Timer()
    var setWordNth = 0
    val timeDelayLiveData = MutableLiveData(sharePreferencesProvider.getTimeDelay())
    val timeOffLiveData = MutableLiveData(sharePreferencesProvider.getTimeOff())
    val isAutoRepeatLiveData = MutableLiveData(sharePreferencesProvider.getIsAutoRepeat())
    val isPlaySoundLiveData = MutableLiveData(sharePreferencesProvider.getIsPlaySound())

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
        val _isLoop = sharePreferencesProvider.getIsAutoRepeat()
        val _isPlaySound = sharePreferencesProvider.getIsPlaySound()
        //TODO SHOULD OBSERVE CHANGE AND CALL PAUSE FROM FRAGMENT
        if (!isAutoPlay.value!!) {
            timer.cancel()
            timer.purge()
        } else if (!_isLoop && _currentPosition < _maxPosition!!) {
            currentPosition.postValue(_currentPosition + 1)
        } else if (!_isLoop && _currentPosition >= _maxPosition!!) {
            currentPosition.postValue(0)
            isAutoPlay.postValue(false)
        } else if (_isLoop && _isPlaySound) {
            currentPosition.postValue((_currentPosition + 1) % _maxPosition!!)
        }
        if (_isPlaySound) {
            listWord.value?.get(currentPosition.value!!)?.let { playSound(it.mp3_us) }
        }
    }

    fun moveToNextPosition() {
        val _currentPosition = currentPosition.value!!
        val _maxPosition = listWord.value?.size?.minus(1)
        val _isLoop = sharePreferencesProvider.getIsAutoRepeat()
        if (!_isLoop && _currentPosition < _maxPosition!!) {
            currentPosition.postValue(_currentPosition + 1)
        } else if (_isLoop) {
            currentPosition.postValue((_currentPosition + 1) % _maxPosition!!)
        }
    }

    fun moveToPreviousPosition() {
        currentPosition.postValue(currentPosition.value?.minus(1))
    }

    fun setIsAutoRepeat(isAutoRepeat: Boolean) {
        isAutoRepeatLiveData.postValue(isAutoRepeat)
    }

    fun setIsPlaySound(isPlaySound: Boolean) {
        isPlaySoundLiveData.postValue(isPlaySound)
    }

    fun setTimeOff(timeToOff: Int) {
        timeOffLiveData.postValue(timeToOff)
    }

    fun setTimeDelay(timeToDelay: Int) {
        timeDelayLiveData.postValue(timeToDelay)
    }

    fun saveSettings() {
        sharePreferencesProvider.putTimeDelay(timeDelayLiveData.value!!)
        sharePreferencesProvider.putTimeOff(timeOffLiveData.value!!)
        sharePreferencesProvider.putIsAutoRepeat(isAutoRepeatLiveData.value!!)
        sharePreferencesProvider.putIsPlaySound(isPlaySoundLiveData.value!!)
    }

    fun discardSettings() {
        timeDelayLiveData.postValue(sharePreferencesProvider.getTimeDelay())
        timeOffLiveData.postValue(sharePreferencesProvider.getTimeOff())
        isAutoRepeatLiveData.postValue(sharePreferencesProvider.getIsAutoRepeat())
        isPlaySoundLiveData.postValue(sharePreferencesProvider.getIsPlaySound())
    }

    fun playSound(mp3Us: String) {
        Log.d("PLAYSOUND TAG", mp3Us)
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

    private fun getByteArrayFromImageURL(url: String): String? {
        try {
            val imageUrl = URL(url)
            val urlConnection: URLConnection = imageUrl.openConnection()
            val inputStream: InputStream = urlConnection.getInputStream()
            val bytesOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var read = 0
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
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { mediaPlayer ->
                mediaPlayer.release()
            }
        } catch (ex: Exception) {
            print(ex.message)
        }
    }
}
