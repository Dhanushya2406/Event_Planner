package com.dhanu.dhanushya_eventplanner_taskitechnologies.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dhanu.dhanushya_eventplanner_taskitechnologies.R
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.ItemUpcomingEventsBinding
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpcomingEventsAdapter(private val listener: EventListener):
    ListAdapter<Event, UpcomingEventsAdapter.EventViewHolder>(DiffCallback()) {

    interface EventListener {
        fun onEdit(event: Event)
        fun onDelete(event: Event)
    }
    inner class EventViewHolder(private val binding: ItemUpcomingEventsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(event: Event) {
            binding.textViewTitle.text = event.title
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.textViewDate.text = dateFormat.format(Date(event.date))
            binding.textViewTime.text = event.time
            binding.editOption.setOnClickListener { view ->
                showPopupMenu(view, event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = ItemUpcomingEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun showPopupMenu(view: View, event: Event) {
        val popup = PopupMenu(view.context, view)
        popup.menuInflater.inflate(R.menu.event_options_menu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_edit -> {
                    listener.onEdit(event)
                    true
                }
                R.id.menu_delete -> {
                    listener.onDelete(event)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem

    }

}