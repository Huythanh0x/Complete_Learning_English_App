package com.batdaulaptrinh.completlearningenglishapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val thumbnail: String
)