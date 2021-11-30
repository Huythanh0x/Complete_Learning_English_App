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
import com.batdaulaptrinh.completlearningenglishapp.databinding.AllWordsSortBottomSheetBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentAllWordTabBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class AllWordTabFragment : Fragment() {
    lateinit var binding: FragmentAllWordTabBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_word_tab, container, false)
        // Inflate the layout for this fragment
        binding.sortImg.setOnClickListener {
            createSortBottomSheet()
        }



        //TODO faking here
        binding.allWordsRecyclerView.adapter = WordListRecyclerAdapter(Utils.getWordList(), { _ ->
            context?.let { word -> Utils.playSoundHello(word) }
        }, { word ->
            findNavController().navigate(R.id.action_navigation_learning_to_wordDetailFragment,
                bundleOf(WordDetailFragment.DETAIL_WORK_KEY to word))
        }, {
            Toast.makeText(context, "${it.en_word} was add to your wordlist", Toast.LENGTH_SHORT)
                .show()
        })

        return binding.root
    }

    private fun createSortBottomSheet() {
        val bindingBottomSheet = DataBindingUtil.inflate<AllWordsSortBottomSheetBinding>(
            layoutInflater,
            R.layout.all_words_sort_bottom_sheet,
            null,
            false)
        val realBottomSheet = BottomSheetDialog(requireContext())
        realBottomSheet.setContentView(bindingBottomSheet.root)
        realBottomSheet.setCancelable(false)
        bindingBottomSheet.sortByAZTxt.setOnClickListener {
            Toast.makeText(context, "Sort by ascending alphabet", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByZATxt.setOnClickListener {
            Toast.makeText(context, "Sort by descending alphabet", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByLevelDesTxt.setOnClickListener {
            Toast.makeText(context, "Sort by ascending level", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByLevelAscTxt.setOnClickListener {
            Toast.makeText(context, "Sort by descending level", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.collapseBottomSheetImg.setOnClickListener {
            realBottomSheet.dismiss()
        }
        realBottomSheet.show()
    }
}