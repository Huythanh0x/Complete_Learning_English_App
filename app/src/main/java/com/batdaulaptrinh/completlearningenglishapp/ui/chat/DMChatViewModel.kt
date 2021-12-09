package com.batdaulaptrinh.completlearningenglishapp.ui.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.batdaulaptrinh.completlearningenglishapp.model.Message
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils

class DMChatViewModel(application: Application) : AndroidViewModel(application) {
    val listMessage = MutableLiveData<List<Message>>()
    fun addMessage(newMessage: Message) {
        Log.d("TAG ADD MESSAGE", newMessage.photoUrl.toString())
        val mutableList = listMessage.value!!.toMutableList()
        mutableList.add(newMessage)
        listMessage.postValue(mutableList)
    }

    fun getFakeListMessage() {
        listMessage.postValue(Utils.getListMessage())
    }


    fun getLastMessage(): Message {
        return listMessage.value!![listMessage.value!!.size - 1]
    }
}