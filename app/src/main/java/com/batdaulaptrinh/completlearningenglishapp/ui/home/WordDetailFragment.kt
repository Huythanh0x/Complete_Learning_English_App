package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordDetailBinding

class WordDetailFragment : Fragment() {
    companion object{
        val DETAIL_WORK_KEY = "DETAIL_WORD_KEY"
    }
    lateinit var binding: FragmentWordDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_detail, container, false)
        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }
}