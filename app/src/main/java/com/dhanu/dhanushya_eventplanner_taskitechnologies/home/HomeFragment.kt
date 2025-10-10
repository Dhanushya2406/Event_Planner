package com.dhanu.dhanushya_eventplanner_taskitechnologies.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.FragmentHomeBinding
import com.dhanu.dhanushya_eventplanner_taskitechnologies.events.EventViewModel
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EventViewModel
    private lateinit var popularEventsAdapter: PopularEventsAdapter
    private lateinit var dateAdapter: DateAdapter
    private lateinit var eventAdapter: CalendarEventAdapter

    private var selectedDate: Date = Date()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[EventViewModel::class.java]
        setupPopularEvents()
        setupDateStrip()
        setupEventList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEventsForDate(selectedDate)
    }

    private fun setupPopularEvents() {
        val dummyPopularEvents = listOf(
            Event(
                title = "Music Fest 2025",
                description = "Bangalore",
                date = System.currentTimeMillis(),
                time = "6:00 PM"
            ),
            Event(
                title = "Food Carnival",
                description = "Hyderabad",
                date = System.currentTimeMillis() + 86400000,
                time = "12:00 PM"
            ),
            Event(
                title = "Startup Expo",
                description = "Chennai",
                date = System.currentTimeMillis() + 2 * 86400000,
                time = "9:30 AM"
            ),
            Event(
                title = "Art & Culture Fair",
                description = "Pune",
                date = System.currentTimeMillis() + 3 * 86400000,
                time = "11:00 AM"
            )
        )

        popularEventsAdapter = PopularEventsAdapter(dummyPopularEvents)
        binding.rvPopularEvents.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularEvents.adapter = popularEventsAdapter
    }

    private fun setupDateStrip() {
        val dates = generateNextDays(30)
        dateAdapter = DateAdapter(dates) { selected ->
            selectedDate = selected
            observeEventsForDate(selectedDate)
        }
        binding.rvDates.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDates.adapter = dateAdapter
    }

    private fun setupEventList() {
        eventAdapter = CalendarEventAdapter()
        binding.rvCalendarEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCalendarEvents.adapter = eventAdapter
    }

    private fun observeEventsForDate(date: Date) {
        viewModel.getEventsByDate(date).observe(viewLifecycleOwner) { events ->
            updateEventList(events)
        }
    }

    private fun updateEventList(events: List<Event>) {
        if (events.isNotEmpty()) {
            eventAdapter.submitList(events)
            binding.rvCalendarEvents.visibility = View.VISIBLE
            binding.tvNoEvents.visibility = View.GONE
        } else {
            binding.rvCalendarEvents.visibility = View.GONE
            binding.tvNoEvents.visibility = View.VISIBLE
        }
    }

    private fun generateNextDays(count: Int): List<Date> {
        val list = mutableListOf<Date>()
        val cal = Calendar.getInstance()
        repeat(count) {
            list.add(cal.time)
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}