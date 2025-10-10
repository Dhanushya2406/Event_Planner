package com.dhanu.dhanushya_eventplanner_taskitechnologies.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dhanu.dhanushya_eventplanner_taskitechnologies.R
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarEventAdapter : ListAdapter<Event, CalendarEventAdapter.EventViewHolder>(DiffCallback()) {

    private var events: List<Event> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_events, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount() = events.size

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val tvDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val tvTime: TextView = itemView.findViewById(R.id.textViewTime)

        fun bind(event: Event) {
            tvTitle.text = event.title
            tvTime.text = event.time

            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val formattedDate = sdf.format(Date(event.date))

            tvDate.text = formattedDate
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
    }
}