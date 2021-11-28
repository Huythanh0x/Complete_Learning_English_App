package com.batdaulaptrinh.completlearningenglishapp.ui.introduction

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.batdaulaptrinh.completlearningenglishapp.MainActivity
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentCompleteIntroductionBinding

class CompleteIntroductionFragment : Fragment() {
    lateinit var binding:FragmentCompleteIntroductionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete_introduction, container, false)
        binding.startBtn.setOnClickListener{
            startActivity(Intent(context,MainActivity::class.java))
            activity?.finish()
        }


        return binding.root

    }
}