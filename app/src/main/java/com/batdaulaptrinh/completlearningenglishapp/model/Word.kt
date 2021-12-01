package com.batdaulaptrinh.completlearningenglishapp.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey
    val _id: String,
    @NonNull
    val en_word: String,
    @NonNull
    val type: String,
    @NonNull
    val cefr: String,
    @NonNull
    val definition: String,
    @NonNull
    val api_uk: String,
    @NonNull
    val api_us: String,
    @NonNull
    val mp3_uk: String,
    @NonNull
    val mp3_us: String,
    @NonNull
    val examples: String,
    @NonNull
    val clean_word_url: String,
    @NonNull
    val thumbnail: String,
    @Nullable
    val set_nth: Int?,
    @Nullable
    val is_favourite: Int?,
    @Nullable
    val add_date: String?,
) : Parcelable