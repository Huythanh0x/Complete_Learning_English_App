package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearningAppDatabase
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordDetailBinding
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class WordDetailFragment : Fragment() {
    lateinit var wordDetailViewModel: WordDetailViewModel

    companion object {
        val DETAIL_WORK_KEY = "DETAIL_WORD_KEY"
    }

    lateinit var binding: FragmentWordDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_detail, container, false)
        // Inflate the layout for this fragment
        val wordDao = LearningAppDatabase.getInstance(requireContext()).wordDao
        val wordRepository = WordRepository(wordDao)
        val wordDetailViewModelFactory = WordDetailViewModelFactory(wordRepository)
        wordDetailViewModel =
            ViewModelProvider(this, wordDetailViewModelFactory)[WordDetailViewModel::class.java]

        arguments?.let { bundle ->
            val minimalWord = bundle.get(DETAIL_WORK_KEY)
            if (minimalWord is MinimalWord) {
                wordDetailViewModel.getWord(minimalWord._id)
            } else if (minimalWord is Word) {
                wordDetailViewModel.getWord(minimalWord._id)
            }
        }
        binding.moreDetailTxt.setOnClickListener {
            findNavController().navigate(R.id.action_wordDetailFragment_to_wordMoreDetail,
                bundleOf(WordMoreDetailFragment.KEY_MORE_DETAIL to wordDetailViewModel.fullWord.value))
        }
        wordDetailViewModel.fullWord.observe(viewLifecycleOwner, { fullWord ->
            binding.word = fullWord
        })
        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}