package com.dhanu.dhanushya_eventplanner_taskitechnologies.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM events WHERE date >= :currentTime ORDER BY date ASC")
    fun getUpcomingEvents(currentTime: Long): Flow<List<Event>>


    @Query("SELECT * FROM events WHERE date < :currentTime ORDER BY date DESC")
    fun getPastEvents(currentTime: Long): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE date BETWEEN :startOfDay AND :endOfDay ORDER BY time ASC")
    fun getEventsByDate(startOfDay: Long, endOfDay: Long): Flow<List<Event>>

}