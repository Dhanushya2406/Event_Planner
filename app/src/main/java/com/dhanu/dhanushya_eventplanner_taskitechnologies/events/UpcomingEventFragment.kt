package com.dhanu.dhanushya_eventplanner_taskitechnologies.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhanu.dhanushya_eventplanner_taskitechnologies.R
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.FragmentUpcomingEventBinding
import com.dhanu.dhanushya_eventplanner_taskitechnologies.model.Event

class UpcomingEventFragment : Fragment(), UpcomingEventsAdapter.EventListener {

    private var _binding: FragmentUpcomingEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UpcomingEventsAdapter
    private lateinit var viewModel : EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[EventViewModel::class.java]

        setupRecyclerView()
        observeEvents()
    }

    private fun setupRecyclerView() {
        adapter = UpcomingEventsAdapter(this)
        binding.rvUpcomingEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUpcomingEvents.adapter = adapter
    }

    private fun observeEvents() {
        viewModel.getUpcomingEvents(System.currentTimeMillis()).observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEdit(event: Event) {
        val fragment = AddEventFragment()

        val bundle = Bundle()
        bundle.putInt("eventId", event.id)
        bundle.putString("title", event.title)
        bundle.putString("description", event.description)
        bundle.putLong("date", event.date)
        bundle.putString("time", event.time)
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDelete(event: Event) {
        viewModel.deleteEvent(event)
        Toast.makeText(requireContext(), "Deleted: ${event.title}", Toast.LENGTH_SHORT).show()
    }

}