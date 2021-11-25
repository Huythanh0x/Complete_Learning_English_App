package com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentYourWordTabBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class YourWordTabFragment : Fragment() {
    lateinit var binding: FragmentYourWordTabBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_your_word_tab, container, false)
        // Inflate the layout for this fragment
        val bottomSheet = BottomSheetBehavior.from(binding.standardBottomSheet)
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheet.peekHeight = 0
        binding.sortImg.setOnClickListener() {
            if (bottomSheet.state == BottomSheetBehavior.STATE_COLLAPSED) {
                binding.root.setBackgroundColor(Color.GRAY)
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED

            } else {
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                binding.root.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            }
        }
        binding.collapseBottomSheetImg.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.root.setBackgroundColor(Color.GRAY)
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.root.setBackgroundColor(Color.WHITE)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("TAG BOTTOM SHEET", slideOffset.toString())
            }
        })

        return binding.root
    }
}