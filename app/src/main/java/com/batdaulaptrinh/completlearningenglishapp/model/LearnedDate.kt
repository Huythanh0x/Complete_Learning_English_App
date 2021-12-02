package com.batdaulaptrinh.completlearningenglishapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learned_date")
data class LearnedDate(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id_date: Int,
    @NonNull
    val date: String,
    @NonNull
    val is_complete: Int,
    @NonNull
    val set_nth: Int,
    @NonNull
    val progress: String,
) {
}