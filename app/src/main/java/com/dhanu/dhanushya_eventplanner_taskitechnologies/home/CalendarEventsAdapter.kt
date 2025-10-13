package com.dhanu.dhanushya_eventplanner_taskitechnologies.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dhanu.dhanushya_eventplanner_taskitechnologies.R
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.ItemCalendarEventsBinding
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarEventAdapter : ListAdapter<Event, CalendarEventAdapter.EventViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCalendarEventsBinding.inflate(inflater, parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(private val binding: ItemCalendarEventsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            Log.d("EventDebug", "Binding event: ${event.title}")
            binding.textViewTitle.text = event.title
            binding.textViewDate.text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(event.date))
            binding.textViewTime.text = event.time
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem == newItem
        }
    }
}