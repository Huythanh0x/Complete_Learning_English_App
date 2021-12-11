package com.batdaulaptrinh.completlearningenglishapp.ui.introduction

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

const val NUMBER_OF_QUESTION = 100
const val BEGINNER_THREAD = 0.6 * 4.2 * NUMBER_OF_QUESTION
const val ADVANCED_THREAD = 0.9 * 4.2 * NUMBER_OF_QUESTION

class EntranceTestViewModel(application: Application, val wordRepository: WordRepository) :
    AndroidViewModel(application) {
    val listWord = MutableLiveData<List<MinimalWord>>()
    val listAnswer =
        MutableLiveData(List(NUMBER_OF_QUESTION) { false })
    private val sharePreferencesProvider = SharePreferencesProvider(application)

    fun getListWord() {
        listWord.value = listOf()
        listWord.value = (wordRepository.getEntranceListWord())
    }

    fun checkAt(position: Int, isCheck: Boolean) {
        Log.d("PostVALUE TAG ALL", "$position $isCheck ${listAnswer.value!![position]}")
        if (listAnswer.value!![position] == isCheck) return
        val tempList = listAnswer.value?.toMutableList()
        tempList?.set(position, isCheck)
        listAnswer.postValue(tempList!!.toList())
    }


    fun getResultLevel(): String {
        var score = 0
        listAnswer.value?.forEachIndexed { index, isCheck ->
            if (listWord.value != null && isCheck) {
                when {
                    listWord.value!![index].cefr.contains("A") -> score += 2
                    listWord.value!![index].cefr.contains("B") -> score += 5
                    listWord.value!![index].cefr.contains("C") -> score += 7
                }
            }
        }
        Log.d("SCORE IS TAG", score.toString())
        Log.d("SCORE ADVANCE TAG", ADVANCED_THREAD.toString())
        Log.d("SCORE BEGINNER TAG", BEGINNER_THREAD.toString())

        if (score < BEGINNER_THREAD) {
            return "BEGINNER"
        } else if (score > ADVANCED_THREAD)
            return "ADVANCE"
        return "INTERMEDIATE"
    }

    fun resetAnswer() {
        listAnswer.value = (List(size = NUMBER_OF_QUESTION) { false })
    }

    fun putCurrentLevel() {
        sharePreferencesProvider.putCurrentLevel(getResultLevel())
    }
}