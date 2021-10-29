package com.example.roomdb

import androidx.lifecycle.LiveData

class Repository(private val Dao:Dao) {

    //perform query on addData or insert data in notes database
    suspend fun addData(notes: Notes){
        Dao.addData(notes)
    }

    val readAllNotes:LiveData<List<Notes>> = Dao.readAllNotes()
    suspend fun updateData(notes: Notes){
        Dao.updateNotes(notes)
    }

    suspend fun deleteData(notes: Notes){
        Dao.deleteNotes(notes)
    }

    suspend fun deleteAllData(){
        Dao.deleteAllNotes()
    }
}