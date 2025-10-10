package com.dhanu.dhanushya_eventplanner_taskitechnologies.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.room.AppDatabase
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class EventViewModel(application: Application): AndroidViewModel(application) {
    private val repository: EventRepository

    init {
        val eventDao = AppDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
    }

    fun insertEvent(event: Event) = viewModelScope.launch {
        repository.insertEvent(event)
    }

    fun updateEvent(event: Event) = viewModelScope.launch {
        repository.updateEvent(event)
    }

    fun deleteEvent(event: Event) = viewModelScope.launch {
        repository.deleteEvent(event)
    }

    fun getUpcomingEvents(currentTime: Long) = repository.getUpcomingEvent(currentTime).asLiveData()

    fun getPastEvents(currentTime: Long) = repository.pastEvent(currentTime).asLiveData()

    fun getEventsByDate(selectedDate: Date): LiveData<List<Event>> {
        val cal = Calendar.getInstance()
        cal.time = selectedDate

        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val startOfDay = cal.timeInMillis

        // End of day
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 999)
        val endOfDay = cal.timeInMillis

        return repository.getEventsByDate(startOfDay, endOfDay).asLiveData()
    }


}