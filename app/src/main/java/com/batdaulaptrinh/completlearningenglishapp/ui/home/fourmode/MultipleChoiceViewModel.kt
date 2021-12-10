package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class MultipleChoiceViewModel(val wordRepository: WordRepository) : ViewModel() {
    val listWord = MutableLiveData<List<Word>>(listOf())
    val listWrongAnswer = MutableLiveData<List<Word>>(listOf())
    val setNth = MutableLiveData(0)

    fun shuffleListWord() {
        listWord.value = listWord.value?.shuffled()
    }

    fun getWordSetNth(nTh: Int) {
        listWord.postValue(wordRepository.getFakeSetWord(nTh))
        setNth.postValue(nTh)
    }

    fun addWrongAnswer(wrongWord: Word) {
        listWrongAnswer.value?.toMutableList()?.add(wrongWord)
        Log.d("TAG WONG LIST", listWrongAnswer.toString())
    }
}