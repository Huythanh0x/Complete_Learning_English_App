package com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllWordViewModel(private val wordRepository: WordRepository) : ViewModel() {
    val allWords = MutableLiveData<List<MinimalWord>>()
    val lastAction = MutableLiveData<String>(Utils.NOTHING)

    fun setActionSort() {
        lastAction.value = Utils.SORT
    }

    fun setActionNothing() {
        lastAction.value = Utils.NOTHING
    }

    fun setActionSearch() {
        lastAction.value = Utils.SEARCH
    }

    fun updateListWord() {
        viewModelScope.launch(Dispatchers.IO) {
            if (lastAction.value == Utils.NOTHING) {
                Log.e("TAG UPDATE", "UPDATE WORD LIST")
                allWords.postValue(wordRepository.getAllWords())
            }
        }
    }

    fun getAllWordSortedListAZ() {
            allWords.postValue(wordRepository.getALlWordSortedListAZ())
    }

    fun getAllWordSortedListZA() {
            allWords.postValue(wordRepository.getALlWordSortedListZA())
    }

    fun deleteFavouriteWord(_id: String) {
        wordRepository.deleteFavouriteWord(_id)
    }

    fun insertFavouriteWord(_id: String) {
        wordRepository.insertFavouriteWord(_id)
    }

    fun getSearchAllWord(formattedString: String) {
        allWords.postValue(wordRepository.getSearchALlWord(formattedString))
    }

    fun getAllWordSortedListLevelASC() {
        allWords.postValue(wordRepository.getAllWordSortedListLevelASC())
    }

    fun getAllWordSortedListLevelDESC() {
        allWords.postValue(wordRepository.getAllWordSortedListLevelDESC())
    }
}