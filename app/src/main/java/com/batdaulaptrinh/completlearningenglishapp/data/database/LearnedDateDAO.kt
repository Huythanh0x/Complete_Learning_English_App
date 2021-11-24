package com.batdaulaptrinh.completlearningenglishapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.batdaulaptrinh.completlearningenglishapp.model.LearnedDate

@Dao
interface LearnedDateDAO {
    @Query("SELECT * FROM learned_date")
    fun getAllLearnedDate(): LiveData<List<LearnedDate>>

    @Insert
    fun insertLearnedDate(newLearnedDate: LearnedDate)

    @Query("DELETE FROM learned_date")
    fun deleteAllDate()
}