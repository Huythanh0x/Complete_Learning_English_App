package com.batdaulaptrinh.completlearningenglishapp.model

data class User(
    var userId: String? = null,
    var username: String? = null,
    @field:JvmField var isOnline: Boolean? = null,
    var lastSeenTimestamp: Any? = null,
    var photoUrl: String? = null,
    var usernameList: List<String>? = null,
    var fcmTokenList: MutableList<String>? = null
)