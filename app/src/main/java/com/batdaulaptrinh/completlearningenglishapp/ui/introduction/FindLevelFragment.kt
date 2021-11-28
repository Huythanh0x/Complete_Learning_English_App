package com.batdaulaptrinh.completlearningenglishapp.ui.introduction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentFindLevelBinding

class FindLevelFragment : Fragment() {
    lateinit var binding: FragmentFindLevelBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_find_level, container, false)
        binding.takeATestBtn.setOnClickListener{
            Toast.makeText(context,"WILL UPDATE THIS FUNCTION LATER",Toast.LENGTH_SHORT).show()
        }
        binding.iAmBeginnerBtn.setOnClickListener{
            findNavController().navigate(R.id.action_findLevelFragment_to_setGoalFragment)
        }


        return binding.root

    }
}