package com.example.roomdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application):AndroidViewModel(application) {

    private val repository:Repository
    val readAllNotes : LiveData<List<Notes>>

    init {
        val Dao = Database.getDatabase(application).Dao()
        repository = Repository(Dao)
        readAllNotes = repository.readAllNotes
    }

    fun addData(notes: Notes){
        viewModelScope.launch ( Dispatchers.IO ) {
            repository.addData(notes)
        }
    }

    fun updateData(notes: Notes){
        viewModelScope.launch ( Dispatchers.IO ) {
            repository.updateData(notes)
        }
    }

    fun deleteData(notes: Notes){
        viewModelScope.launch ( Dispatchers.IO ){
            repository.deleteData(notes)
        }
    }
    fun deleteAllData(){
        viewModelScope.launch ( Dispatchers.IO ){
            repository.deleteAllData()
        }
    }
}