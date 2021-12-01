package com.batdaulaptrinh.completlearningenglishapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learned_date")
data class LearnedDate(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val is_complete: Int,
    val set_nt: Int,
    val progress: String,
) {
}