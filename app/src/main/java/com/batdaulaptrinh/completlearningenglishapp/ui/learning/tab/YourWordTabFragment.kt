package com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentYourWordTabBinding
import com.batdaulaptrinh.completlearningenglishapp.databinding.YourWordSortBottomSheetBinding
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar


class YourWordTabFragment : Fragment() {
    lateinit var yourWordViewModel: YourWordViewModel
    lateinit var adapter: WordListRecyclerAdapter
    lateinit var binding: FragmentYourWordTabBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_your_word_tab, container, false)
        // Inflate the layout for this fragment
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        LearningAppDatabase.getInstance(requireContext()).learnedDateDAO
        val wordRepository = WordRepository(wordDao)
        val yourWordViewModelFactory = YourWordViewModelFactory(wordRepository)
        yourWordViewModel =
            ViewModelProvider(this, yourWordViewModelFactory)[YourWordViewModel::class.java]

        adapter = WordListRecyclerAdapter(arrayListOf(), { word ->
            findNavController().navigate(R.id.action_navigation_learning_to_wordDetailFragment,
                bundleOf(WordDetailFragment.DETAIL_WORK_KEY to word))
        }) { word ->
            yourWordViewModel.deleteFavouriteWord(word._id)
            yourWordViewModel.removeWordFromList(word._id)
            Snackbar.make(binding.root,
                "${word.en_word} was removed from your wordlist",
                Snackbar.LENGTH_LONG).setAction("Undo") {
                restoreYourWord(word._id)
            }.show()
        }
        yourWordViewModel.listYourWord.observe(viewLifecycleOwner, { listWord ->
            binding.loadingCl.visibility = View.GONE
            if (listWord.isEmpty()) {
                binding.emptyImg.visibility = View.VISIBLE
            } else {
                binding.emptyImg.visibility = View.GONE
            }
            adapter.addList(listWord)
        })

        binding.searchView.setOnQueryTextListener(searchListener)
        binding.yourWordsRecyclerView.adapter = adapter

        yourWordViewModel.lastAction.observe(viewLifecycleOwner, {
            Log.d("TAG LAST ACTION", it.toString())
        })
        binding.sortImg.setOnClickListener() {
            binding.searchView.setQuery("", false)
            binding.searchView.clearFocus()
            createSortBottomSheet()
        }
        return binding.root
    }

    private fun restoreYourWord(_id: String) {
        yourWordViewModel.insertFavouriteWord(_id)
        yourWordViewModel.updateListWordWord()
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
            yourWordViewModel.setActionSort()
            yourWordViewModel.getYourWordSortedListAZ()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.sortByZATxt.setOnClickListener {
            yourWordViewModel.setActionSort()
            yourWordViewModel.getYourWordSortedListZA()
            realBottomSheet.dismiss()
        }
        bindingBottomSheet.collapseBottomSheetImg.setOnClickListener {
            realBottomSheet.dismiss()
        }
        realBottomSheet.show()
    }

    override fun onResume() {
        yourWordViewModel.updateListWordWord()
        super.onResume()
    }

    private val searchListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                yourWordViewModel.setActionSearch()
                yourWordViewModel.getSearchYourWord(it)
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null && newText.isNotEmpty()) {
                yourWordViewModel.setActionSearch()
                yourWordViewModel.getSearchYourWord(newText)
            }
            if (newText != null && newText.isEmpty()) {
                if (yourWordViewModel.lastAction.value == Utils.SEARCH) {
                    yourWordViewModel.getSearchYourWord(newText)
                    Log.e("TAG SEARCH", "CALLED")
                }
            }
            return false
        }
    }
}
