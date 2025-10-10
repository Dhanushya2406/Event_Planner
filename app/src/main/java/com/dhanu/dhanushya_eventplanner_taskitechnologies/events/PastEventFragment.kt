package com.dhanu.dhanushya_eventplanner_taskitechnologies.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.FragmentPastEventBinding

class PastEventFragment : Fragment() {

    private var _binding: FragmentPastEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : EventViewModel
    private lateinit var adapter: PastEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPastEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[EventViewModel::class.java]

        setupRecyclerView()
        observeEvents()
    }

    private fun setupRecyclerView() {
        adapter = PastEventsAdapter()
        binding.rvPastEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPastEvents.adapter = adapter
    }

    private fun observeEvents() {
        viewModel.getPastEvents(System.currentTimeMillis()).observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}