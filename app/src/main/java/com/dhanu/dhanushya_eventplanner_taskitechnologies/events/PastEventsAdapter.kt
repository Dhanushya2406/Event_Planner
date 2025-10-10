package com.dhanu.dhanushya_eventplanner_taskitechnologies.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.ItemPastEventsBinding
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import java.text.SimpleDateFormat
import java.util.Locale

class PastEventsAdapter : ListAdapter<Event, PastEventsAdapter.PastEventViewHolder>(DiffCallback()) {

    class PastEventViewHolder(private val binding: ItemPastEventsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.textViewTitle.text = event.title
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.textViewDate.text = dateFormat.format(event.date)
            binding.textViewTime.text = event.time
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastEventViewHolder {
        val binding = ItemPastEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PastEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PastEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}