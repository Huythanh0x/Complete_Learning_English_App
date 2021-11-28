package com.batdaulaptrinh.completlearningenglishapp.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentMultipleChoiceBinding
import com.batdaulaptrinh.completlearningenglishapp.model.WordSet

class MultipleChoiceFragment : Fragment() {
    companion object {
        val KEY_AGRS_SET = "WORD_SET"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMultipleChoiceBinding>(layoutInflater,
            R.layout.fragment_multiple_choice,
            container,
            false)

        arguments?.let {
            val setWord = it.get(KEY_AGRS_SET)
            if (setWord is WordSet) {
                binding.titleToolBar.text = setWord.setNth.toString()
            }
        }

        binding.backwardImg.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }
}