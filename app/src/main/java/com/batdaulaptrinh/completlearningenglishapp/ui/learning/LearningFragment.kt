package com.batdaulaptrinh.completlearningenglishapp.ui.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentLearningBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.HomePagerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.LearningPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class LearningFragment : Fragment() {
    lateinit var binding: FragmentLearningBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learning, container, false)

        val adapter = LearningPagerAdapter(childFragmentManager, lifecycle)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learning, null, false)
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Your Words"
                else -> tab.text = "All words"
            }
        }.attach()
        // Inflate the layout for this fragment
        return binding.root
    }


}