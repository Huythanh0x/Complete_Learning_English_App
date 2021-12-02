package com.batdaulaptrinh.completlearningenglishapp.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class MinimalWord(
    @NonNull
    @PrimaryKey
    val _id: String,
    @NonNull
    val en_word: String,
    @NonNull
    val api_uk: String,
    @NonNull
    val api_us: String,
    @NonNull
    val mp3_uk: String,
    @NonNull
    val mp3_us: String,
    @NonNull
    val is_favourite: Int,
    @NonNull
    val add_date: String,
) : Parcelable