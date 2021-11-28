package com.batdaulaptrinh.completlearningenglishapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatRoom(
    var urlImage: String? = null,
    var chatId: String? = null,
    var senderId: String? = null,
    var receiverId: String? = null,
    var isGroupChat: Boolean? = null,
):Parcelable
