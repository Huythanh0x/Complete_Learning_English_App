package com.batdaulaptrinh.completlearningenglishapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.tab.TestTabFragment
import com.batdaulaptrinh.completlearningenglishapp.ui.home.tab.WordDayTabFragment
import com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab.AllWordTabFragment
import com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab.YourWordTabFragment

class LearningPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> YourWordTabFragment()
            1 -> AllWordTabFragment()
            else -> YourWordTabFragment()
        }
    }
}