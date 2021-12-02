package com.batdaulaptrinh.completlearningenglishapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordListBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.SetWordListRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils


class WordListFragment : Fragment() {
    lateinit var binding: FragmentWordListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            com.batdaulaptrinh.completlearningenglishapp.R.layout.fragment_word_list,
            container,
            false)
        //TODO FAKING
        binding.recyclerView.adapter = SetWordListRecyclerAdapter(arrayListOf(), {
            findNavController().navigate(R.id.action_wordListFragment_to_wordDetailFragment)
        }, {
            context?.let { context -> Utils.playSoundHello(context) }
        }, {

        })

        binding.backwardImg.setOnClickListener {
            findNavController().popBackStack()
        }



        return binding.root

    }
}