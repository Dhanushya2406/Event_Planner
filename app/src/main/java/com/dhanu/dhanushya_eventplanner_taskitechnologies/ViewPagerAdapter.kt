package com.dhanu.dhanushya_eventplanner_taskitechnologies

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dhanu.dhanushya_eventplanner_taskitechnologies.events.PastEventFragment
import com.dhanu.dhanushya_eventplanner_taskitechnologies.events.UpcomingEventFragment

class ViewPagerAdapter(fragmentActivity: Fragment) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UpcomingEventFragment()
            else -> PastEventFragment()
        }
    }
}