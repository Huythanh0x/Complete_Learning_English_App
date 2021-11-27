package com.batdaulaptrinh.completlearningenglishapp.model

import androidx.lifecycle.LiveData


data class ChatRoom(
    var urlImage: String? = null,
    var chatId: String? = null,
    var senderId: String? = null,
    var receiverId: String? = null,
    var isGroupChat: Boolean? = null,
    var listMessage: LiveData<List<Message>>
)
