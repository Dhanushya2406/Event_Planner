package com.dhanu.dhanushya_eventplanner_taskitechnologies.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dhanu.dhanushya_eventplanner_taskitechnologies.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateAdapter(
    private val dates: List<Date>,
    private val onDateSelected: (Date) -> Unit
) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date_card, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val date = dates[position]
        holder.bind(date, position == selectedPosition)
        holder.itemView.setOnClickListener {
            val oldPos = selectedPosition
            selectedPosition = position
            notifyItemChanged(oldPos)
            notifyItemChanged(selectedPosition)
            onDateSelected(date)
        }
    }

    override fun getItemCount() = dates.size

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val card = itemView.findViewById<CardView>(R.id.cardDate)
        private val tvDay = itemView.findViewById<TextView>(R.id.tvDay)
        private val tvMonth = itemView.findViewById<TextView>(R.id.tvMonth)

        fun bind(date: Date, isSelected: Boolean) {
            val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
            val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
            tvDay.text = dayFormat.format(date)
            tvMonth.text = monthFormat.format(date)

            card.setCardBackgroundColor(if (isSelected) Color.parseColor("#FF9800") else Color.WHITE)
            tvDay.setTextColor(if (isSelected) Color.WHITE else Color.BLACK)
            tvMonth.setTextColor(if (isSelected) Color.WHITE else Color.DKGRAY)
        }
    }
}