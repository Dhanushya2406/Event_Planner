package com.dhanu.dhanushya_eventplanner_taskitechnologies.events

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
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

    private val _selectedDate = MutableLiveData<Date>(Date())

    val eventsForSelectedDate: LiveData<List<Event>> =
        _selectedDate.switchMap { date ->
            val calStart = Calendar.getInstance().apply {
                time = date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val startOfDay = calStart.timeInMillis
            val endOfDay = startOfDay + 86_400_000 - 1
            Log.d("EventDebug", "Querying events for range: $startOfDay - $endOfDay")
            repository.getEventsByDate(startOfDay, endOfDay).asLiveData()
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

    fun setSelectedDate(date: Date) {
        _selectedDate.value = date
    }

}