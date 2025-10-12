package com.dhanu.dhanushya_eventplanner_taskitechnologies.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.FragmentHomeBinding
import com.dhanu.dhanushya_eventplanner_taskitechnologies.events.EventViewModel
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event
import java.util.Calendar
import java.util.Date

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EventViewModel
    private lateinit var dateAdapter: DateAdapter
    private lateinit var eventAdapter: CalendarEventAdapter

    private var selectedDate: Date = Date()

    private val searchHints = listOf(
        "Search 'Birthday events'",
        "Search 'Music shows'",
        "Search 'Pet parties'",
        "Search 'Wedding venues'",
        "Search 'Corporate events'"
    )

    private var hintIndex = 0
    private val hintHandler = Handler(Looper.getMainLooper())
    private val hintRunnable = object : Runnable {
        override fun run() {
            binding.searchBar.hint = searchHints[hintIndex]
            hintIndex = (hintIndex + 1) % searchHints.size
            hintHandler.postDelayed(this, 5000)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[EventViewModel::class.java]

        setupDateStrip()
        setupEventList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.eventsForSelectedDate.observe(viewLifecycleOwner) { events ->
            android.util.Log.d("EventDebug", "HomeFragment received ${events.size} events for $selectedDate")
            events.forEach {
                android.util.Log.d("EventDebug", "Event: ${it.title}, date=${it.date}, time=${it.time}")
            }
            updateEventList(events)
        }
    }

    private fun setupDateStrip() {
        val dates = generateNextDays(30)
        dateAdapter = DateAdapter(dates) { selected ->
            selectedDate = normalizeDate(selected)
            viewModel.setSelectedDate(selectedDate)
        }
        binding.rvDates.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDates.adapter = dateAdapter
    }

    private fun setupEventList() {
        eventAdapter = CalendarEventAdapter()
        binding.rvCalendarEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCalendarEvents.adapter = eventAdapter
    }

    private fun updateEventList(events: List<Event>) {
        Log.d("EventDebug", "updateEventList called with ${events.size} events")
        events.forEach { Log.d("EventDebug", "Updating UI: ${it.title}, date=${it.date}") }

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

    private fun normalizeDate(date: Date): Date {
        val cal = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return cal.time
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.setSelectedDate(selectedDate)
        hintHandler.post(hintRunnable)
    }

    override fun onPause() {
        super.onPause()
        hintHandler.removeCallbacks(hintRunnable)
    }
}