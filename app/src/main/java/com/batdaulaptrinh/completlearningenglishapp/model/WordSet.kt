package com.batdaulaptrinh.completlearningenglishapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordSet(
    val setNth: Int,
    val imageOfSet: String,
) : Parcelable