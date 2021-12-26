package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordListBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.SetWordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.ChoosingModeFragment
import com.batdaulaptrinh.completlearningenglishapp.ui.home.WordDetailFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class WordListFragment : Fragment() {
    lateinit var binding: FragmentWordListBinding
    lateinit var wordListViewModel: WordListViewModel
    lateinit var adapter: SetWordListRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            com.batdaulaptrinh.completlearningenglishapp.R.layout.fragment_word_list,
            container,
            false
        )
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        LearningAppDatabase.getInstance(requireContext()).learnedDateDAO
        val wordRepository = WordRepository(wordDao)
        val wordListViewModelFactory = WordListViewModelFactory(wordRepository)
        wordListViewModel =
            ViewModelProvider(this, wordListViewModelFactory)[WordListViewModel::class.java]
        arguments?.let {
            val setWord = it.get(ChoosingModeFragment.KEY_ARGS_SET)
            if (setWord is WordSet) {
                wordListViewModel.getNewWordList(setWord.setNth)
            }
        }
        adapter = SetWordListRecyclerAdapter(arrayListOf(), { word ->
            findNavController().navigate(
                R.id.action_wordListFragment_to_wordDetailFragment,
                bundleOf(WordDetailFragment.DETAIL_WORK_KEY to word)
            )
        }) { word ->
            if (word.is_favourite == 1) {
                wordListViewModel.insertFavouriteWord(word._id)
//                MotionToast.createColorToast(
//                    context as Activity,
//                    "${word.en_word}",
//                    "was added to your wordlist",
//                    MotionToastStyle.INFO,
//                    MotionToast.GRAVITY_BOTTOM,
//                    Utils.SUPER_SHORT_DURATION,
//                    ResourcesCompat.getFont(context as Activity, R.font.helvetica_regular)
//                )
            } else {
                wordListViewModel.deleteFavouriteWord(word._id)
            }
        }
        binding.recyclerView.adapter = adapter
        wordListViewModel.listWord.observe(viewLifecycleOwner, { listWord ->
            adapter.addList(listWord)
        })
        wordListViewModel.wordSetNth.observe(viewLifecycleOwner) {
            binding.wordListViewModel = wordListViewModel
        }
        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}