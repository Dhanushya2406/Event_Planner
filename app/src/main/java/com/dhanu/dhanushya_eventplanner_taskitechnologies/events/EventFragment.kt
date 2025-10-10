package com.dhanu.dhanushya_eventplanner_taskitechnologies.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dhanu.dhanushya_eventplanner_taskitechnologies.R
import com.dhanu.dhanushya_eventplanner_taskitechnologies.ViewPagerAdapter
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.FragmentEventBinding
import com.google.android.material.tabs.TabLayoutMediator

class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        val tabTitles = listOf("Upcoming", "Past Events")

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        binding.fabAddEvent.setOnClickListener {
            val addEventFragment = AddEventFragment()
            parentFragmentManager.beginTransaction().apply {
                hide(this@EventFragment)
                add(R.id.fragment_container, addEventFragment, "AddEventFragment")
                addToBackStack("AddEventFragment")
                commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}