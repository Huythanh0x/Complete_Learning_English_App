package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentChoosingModeBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet
import com.batdaulaptrinh.completlearningenglishapp.ui.home.tab.WordDayTabFragment

class ChoosingModeFragment : Fragment() {
    companion object {
        val KEY_ARGS_SET = "KEY_ARGS"
    }

    lateinit var binding: FragmentChoosingModeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_choosing_mode, container, false)

        arguments?.let {
            val setWord = it.get(WordDayTabFragment.KEY_AGRS_SET)
            if (setWord is WordSet) {
                binding.titleToolBar.text = "WORD SET NUMBER ${setWord.setNth}"
            }

            binding.testCv.setOnClickListener {
                findNavController().navigate(R.id.action_choosingModeFragment_to_multipleChoiceFragment,
                    bundleOf(KEY_ARGS_SET to setWord))
            }
            binding.wordListCv.setOnClickListener {
                findNavController().navigate(R.id.action_choosingModeFragment_to_wordListFragment,
                    bundleOf(KEY_ARGS_SET to setWord))
            }
            binding.gameCv.setOnClickListener {
                findNavController().navigate(R.id.action_choosingModeFragment_to_connectWordFragment,
                    bundleOf(KEY_ARGS_SET to setWord))
            }
            binding.flashCardCv.setOnClickListener {
                findNavController().navigate(R.id.action_choosingModeFragment_to_flashCardFragment,
                    bundleOf(KEY_ARGS_SET to setWord))
            }
            binding.backwardImg.setOnClickListener{
                findNavController().popBackStack()
            }

        }

        return binding.root

    }


}