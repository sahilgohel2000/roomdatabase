package com.example.roomdb

import android.accounts.AuthenticatorDescription
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes(

    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val noteTitle:String,
    val noteDescription:String

)
