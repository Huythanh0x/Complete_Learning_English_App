package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentHomeBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false)
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Test"
                else -> tab.text = "Everyday Word"
            }
        }.attach()


        return binding.root
    }


}