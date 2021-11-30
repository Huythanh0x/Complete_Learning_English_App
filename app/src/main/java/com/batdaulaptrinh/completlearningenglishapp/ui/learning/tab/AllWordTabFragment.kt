package com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentAllWordTabBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior

class AllWordTabFragment : Fragment() {
    lateinit var binding: FragmentAllWordTabBinding
    lateinit var bottomSheet:  BottomSheetBehavior<CardView>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_word_tab, container, false)
        // Inflate the layout for this fragment
        bottomSheet = BottomSheetBehavior.from(binding.standardBottomSheet)
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


        binding.sortByAZTxt.setOnClickListener {
            Toast.makeText(context, "Sort by ascending alphabet", Toast.LENGTH_SHORT).show()
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.sortByZATxt.setOnClickListener {
            Toast.makeText(context, "Sort by descending alphabet", Toast.LENGTH_SHORT).show()
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.sortByLevelAscTxt.setOnClickListener {
            Toast.makeText(context, "Sort by ascending level", Toast.LENGTH_SHORT).show()
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.sortByLevelDesTxt.setOnClickListener {
            Toast.makeText(context, "Sort by descending level", Toast.LENGTH_SHORT).show()
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        //TODO faking here
        binding.allWordsRecyclerView.adapter = WordListRecyclerAdapter(Utils.getWordList(), { _ ->
            context?.let { word -> Utils.playSoundHello(word) }
        }, { word ->
            findNavController().navigate(R.id.action_navigation_learning_to_wordDetailFragment,
                bundleOf(WordDetailFragment.DETAIL_WORK_KEY to word))
        }, {
            Toast.makeText(context, "${it.en_word} was add to your wordlist", Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }

    override fun onPause() {
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        super.onPause()
    }
}