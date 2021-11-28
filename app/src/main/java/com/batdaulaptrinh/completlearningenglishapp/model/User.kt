package com.batdaulaptrinh.completlearningenglishapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var userId: String? = null,
    var username: String? = null,
    @field:JvmField var isOnline: Boolean? = null,
    var lastSeenTimestamp: String? = null,
    var photoUrl: String? = null,
) : Parcelable