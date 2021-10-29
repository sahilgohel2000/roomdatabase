package com.example.roomdb

import android.content.Context
import android.os.Build
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//create Room Database class and extend RoomDatabase

@Database(entities = [Notes::class],version = 1,exportSchema = false)
abstract class Database:RoomDatabase() {

    abstract fun Dao():Dao

    companion object{
        @Volatile
        private var INSTANCE:com.example.roomdb.Database?=null

        fun getDatabase(context: Context):com.example.roomdb.Database{
            val instance= INSTANCE
            if (instance!=null)
            {
                return instance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,com.example.roomdb.Database::class.java,"notes"
                ).build()

                INSTANCE=instance
                return instance
            }
        }
    }
}