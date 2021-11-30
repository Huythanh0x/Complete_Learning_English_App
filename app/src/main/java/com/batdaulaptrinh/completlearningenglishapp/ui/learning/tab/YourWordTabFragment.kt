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
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentYourWordTabBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class YourWordTabFragment : Fragment() {
    lateinit var binding: FragmentYourWordTabBinding
    lateinit var sortBottomSheet:  BottomSheetBehavior<CardView>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_your_word_tab, container, false)
        // Inflate the layout for this fragment
        sortBottomSheet = BottomSheetBehavior.from(binding.standardBottomSheet)
        sortBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        sortBottomSheet.peekHeight = 0
        binding.sortImg.setOnClickListener() {
            if (sortBottomSheet.state == BottomSheetBehavior.STATE_COLLAPSED) {
                binding.root.setBackgroundColor(Color.GRAY)
                sortBottomSheet.state = BottomSheetBehavior.STATE_EXPANDED

            } else {
                sortBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                binding.root.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            }
        }
        binding.collapseBottomSheetImg.setOnClickListener {
            sortBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        sortBottomSheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.root.setBackgroundColor(Color.GRAY)
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.root.setBackgroundColor(Color.WHITE)
                }
            }

            override fun onSlide(sortBottomSheet: View, slideOffset: Float) {
                Log.d("TAG BOTTOM SHEET", slideOffset.toString())
            }
        })
        
        sortBottomSheet.isDraggable = false

        binding.sortByAZTxt.setOnClickListener {
            Toast.makeText(context, "Sort by ascending alphabet", Toast.LENGTH_SHORT).show()
            sortBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.sortByZATxt.setOnClickListener {
            Toast.makeText(context, "Sort by descending alphabet", Toast.LENGTH_SHORT).show()
            sortBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.sortByTimeAddAscTxt.setOnClickListener {
            Toast.makeText(context, "Sort by ascending add time", Toast.LENGTH_SHORT).show()
            sortBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.sortByTimeAddDesTxt.setOnClickListener {
            Toast.makeText(context, "Sort by descending add time", Toast.LENGTH_SHORT).show()
            sortBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }


        //TODO faking here
        binding.yourWordsRecyclerView.adapter =
            WordListRecyclerAdapter(Utils.getWordList(), { _ ->
                context?.let { word -> Utils.playSoundHello(word) }
            }, { word ->
                findNavController().navigate(R.id.action_navigation_learning_to_wordDetailFragment,
                    bundleOf(WordDetailFragment.DETAIL_WORK_KEY to word))
            }, {
                Snackbar.make(binding.root,
                    "${it.en_word} was removed from your wordlist",
                    Snackbar.LENGTH_LONG).setAction("Undo") {
                }.show()
            })

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        sortBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED

    }
}
