package com.example.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addData(notes: Notes)

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun readAllNotes():LiveData<List<Notes>>

    @Update
    suspend fun updateNotes(notes: Notes)

    @Delete
    suspend fun deleteNotes(notes: Notes)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}