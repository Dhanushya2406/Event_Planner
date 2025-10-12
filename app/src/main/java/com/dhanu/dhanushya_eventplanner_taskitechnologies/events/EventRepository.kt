package com.dhanu.dhanushya_eventplanner_taskitechnologies.events

import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.room.EventDao
import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {

    suspend fun insertEvent(event: Event) {
        android.util.Log.d("EventDebug", "Inserting event: ${event.title}, date=${event.date}, time=${event.time}")
        eventDao.insertEvents(event)
    }

    suspend fun updateEvent(event: Event) = eventDao.updateEvent(event)

    suspend fun deleteEvent(event: Event) = eventDao.deleteEvent(event)

    fun getUpcomingEvent(currentTime: Long) = eventDao.getUpcomingEvents(currentTime)

    fun pastEvent(currentTime: Long) = eventDao.getPastEvents(currentTime)

    fun getEventsByDate(startOfDay: Long, endOfDay: Long) =
        eventDao.getEventsByDate(startOfDay, endOfDay)

}

