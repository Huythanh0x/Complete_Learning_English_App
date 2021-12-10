package com.batdaulaptrinh.completlearningenglishapp.ui.introduction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class EntranceTestViewModel(application: Application, val wordRepository: WordRepository) :
    AndroidViewModel(application) {
    val listWord = MutableLiveData<List<MinimalWord>>()
    val listAnswer =
        MutableLiveData(List(50) { false })
    val sharePreferencesProvider = SharePreferencesProvider(application)

    fun getListWord() {
        listWord.value = listOf()
        listWord.postValue(wordRepository.getEntranceListWord())
    }

    fun checkAt(position: Int, isCheck: Boolean) {
        val tempList = listAnswer.value?.toMutableList()
        tempList?.set(position, isCheck)
        listAnswer.postValue(tempList)
    }

    fun getResultLevel(): String {
        var score = 0
        listAnswer.value?.forEachIndexed { index, isCheck ->
            if (listWord.value != null && isCheck) {
                if (listWord.value!![index].cefr.contains("A")) {
                    score += 2
                } else if (listWord.value!![index].cefr.contains("B"))
                    score += 5
                else if (listWord.value!![index].cefr.contains("C"))
                    score += 7
            }
        }
        Log.d("SCORE IS TAG", score.toString())
        if (score < 120) {
            return "BEGINNER"
        } else if (score > 190)
            return "ADVANCE"
        return "INTERMEDIATE"
    }

    fun resetAnswer() {
        listAnswer.postValue(List(size = 50) { false })
    }

    fun putCurrentLevel() {
        sharePreferencesProvider.putCurrentLevel(getResultLevel())
    }

}