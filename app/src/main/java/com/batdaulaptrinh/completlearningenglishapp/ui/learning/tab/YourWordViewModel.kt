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

class YourWordViewModel(private val wordRepository: WordRepository) : ViewModel() {
    val listYourWord = MutableLiveData<List<MinimalWord>>()
    val lastAction = MutableLiveData(Utils.NOTHING)
    fun deleteFavouriteWord(_id: String) {
        wordRepository.deleteFavouriteWord(_id)
    }

    fun insertFavouriteWord(_id: String) {
        wordRepository.insertFavouriteWord(_id)
    }

    fun setActionSort() {
        lastAction.value = Utils.SORT
    }

    fun removeWordFromList(_id: String) {
        listYourWord.value
        val newList = listYourWord.value?.filter {minimalWord -> minimalWord._id!=_id}
        listYourWord.postValue(newList!!)
    }

    fun setActionNothing() {
        lastAction.value = Utils.NOTHING
    }

    fun setActionSearch() {
        lastAction.value = Utils.SEARCH
    }

    fun getYourWordSortedListAZ() {
        listYourWord.postValue(wordRepository.getYourWordSortedListAZ())
    }

    fun getYourWordSortedListZA() {
        listYourWord.postValue(wordRepository.getYourWordSortedListZA())
    }

    fun updateListWordWord() {
        viewModelScope.launch(Dispatchers.IO) {
            if (lastAction.value == Utils.NOTHING) {
                Log.e("TAG UPDATE", "UPDATE WORD LIST")
                listYourWord.postValue(wordRepository.getMyWordList())
            }
        }
    }

    fun getSearchYourWord(formattedString: String) {
        listYourWord.postValue(wordRepository.getSearchYourWord(formattedString))
    }
}