package com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentYourWordTabBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.YourWordSortBottomSheetBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class YourWordTabFragment : Fragment() {
    lateinit var binding: FragmentYourWordTabBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_your_word_tab, container, false)
        // Inflate the layout for this fragment
        binding.sortImg.setOnClickListener() {
            createSortBottomSheet()
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
//        TODO("click outside to collapse bottom sheet")
  //      TODO("draggable")
    //    TODO("word suggestion")
        return binding.root
    }


    private fun createSortBottomSheet() {
        val bindingBottomSheet = DataBindingUtil.inflate<YourWordSortBottomSheetBinding>(
            layoutInflater,
            R.layout.your_word_sort_bottom_sheet,
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
        bindingBottomSheet.sortByTimeAddAscTxt.setOnClickListener {
            Toast.makeText(context, "Sort by ascending add time", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByTimeAddDesTxt.setOnClickListener {
            Toast.makeText(context, "Sort by descending add time", Toast.LENGTH_SHORT).show()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.collapseBottomSheetImg.setOnClickListener {
            realBottomSheet.dismiss()
        }
        realBottomSheet.show()
    }
}
