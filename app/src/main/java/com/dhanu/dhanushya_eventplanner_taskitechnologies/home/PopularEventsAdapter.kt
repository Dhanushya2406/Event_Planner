package com.dhanu.dhanushya_eventplanner_taskitechnologies.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhanu.dhanushya_eventplanner_taskitechnologies.R
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PopularEventsAdapter(
    private var eventList: List<Event>
) : RecyclerView.Adapter<PopularEventsAdapter.PopularEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularEventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_events, parent, false)
        return PopularEventViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularEventViewHolder, position: Int) {
        val event = eventList[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = eventList.size

    fun updateList(newList: List<Event>) {
        eventList = newList
        notifyDataSetChanged()
    }

    class PopularEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventTitle: TextView = itemView.findViewById(R.id.pop_EventTitle)
        private val eventDate: TextView = itemView.findViewById(R.id.pop_date)
        private val eventLocation: TextView = itemView.findViewById(R.id.pop_location)

        fun bind(event: Event) {
            eventTitle.text = event.title
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val formattedDate = sdf.format(Date(event.date))
            eventDate.text = formattedDate
            eventLocation.text = event.description

        }
    }
}