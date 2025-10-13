package com.dhanu.dhanushya_eventplanner_taskitechnologies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dhanu.dhanushya_eventplanner_taskitechnologies.databinding.ActivityDashboardBinding
import com.dhanu.dhanushya_eventplanner_taskitechnologies.events.AddEventFragment
import com.dhanu.dhanushya_eventplanner_taskitechnologies.events.EventFragment
import com.dhanu.dhanushya_eventplanner_taskitechnologies.home.HomeFragment
import com.dhanu.dhanushya_eventplanner_taskitechnologies.profile.ProfileFragment

class Dashboard : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var eventFragment: EventFragment
    private lateinit var profileFragment: ProfileFragment

    private lateinit var binding: ActivityDashboardBinding

    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            homeFragment = HomeFragment()
            eventFragment = EventFragment()
            profileFragment = ProfileFragment()

            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, homeFragment, "HomeFragment")
                add(R.id.fragment_container, eventFragment, "EventFragment").hide(eventFragment)
                add(R.id.fragment_container, profileFragment, "ProfileFragment").hide(profileFragment)
                commit()
            }

            activeFragment = homeFragment
        } else {
            homeFragment = supportFragmentManager.findFragmentByTag("HomeFragment") as HomeFragment
            eventFragment = supportFragmentManager.findFragmentByTag("EventFragment") as EventFragment
            profileFragment = supportFragmentManager.findFragmentByTag("ProfileFragment") as ProfileFragment

            activeFragment = when (binding.bottomNavigation.selectedItemId) {
                R.id.home -> homeFragment
                R.id.event -> eventFragment
                R.id.profile -> profileFragment
                else -> homeFragment
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> switchFragment(homeFragment)
                R.id.event -> switchFragment(eventFragment)
                R.id.profile -> switchFragment(profileFragment)
            }
            true
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            binding.bottomNavigation.visibility =
                if (currentFragment is AddEventFragment) View.GONE else View.VISIBLE
        }
    }

    private fun switchFragment(targetFragment: Fragment) {
        if (activeFragment == targetFragment) return

        supportFragmentManager.beginTransaction().apply {
            activeFragment?.let { hide(it) }
            show(targetFragment)
            commit()
        }

        activeFragment = targetFragment
    }
}