package com.dhanu.dhanushya_eventplanner_taskitechnologies.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event

@Database(entities = [Event::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun eventDao() : EventDao

    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "event_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}