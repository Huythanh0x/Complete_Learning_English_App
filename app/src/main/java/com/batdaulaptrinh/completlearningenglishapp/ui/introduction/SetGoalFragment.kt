package com.batdaulaptrinh.completlearningenglishapp.ui.introduction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentSetGoalBinding

class SetGoalFragment : Fragment() {
    lateinit var binding: FragmentSetGoalBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_goal, container, false)
        binding.continueBtn.setOnClickListener { findNavController().navigate(R.id.action_setGoalFragment_to_completeIntroductionFragment) }
        binding.backwardImg.setOnClickListener { findNavController().popBackStack() }

        //handle click radio button
        binding.apply {
            easyModeCl.setOnClickListener{
                binding.easyCb.isChecked = true
                binding.seriousCb.isChecked = false
                binding.intenseCb.isChecked = false
            }
            seriousModeCl.setOnClickListener{
                binding.seriousCb.isChecked = true
                binding.easyCb.isChecked = false
                binding.intenseCb.isChecked = false
            }
            intenseModeCl.setOnClickListener{
                binding.intenseCb.isChecked = true
                binding.easyCb.isChecked = false
                binding.seriousCb.isChecked = false
            }
            easyCb.setOnClickListener{
                binding.easyCb.isChecked = true
                binding.seriousCb.isChecked = false
                binding.intenseCb.isChecked = false
            }
            seriousCb.setOnClickListener{
                binding.seriousCb.isChecked = true
                binding.easyCb.isChecked = false
                binding.intenseCb.isChecked = false
            }
            intenseCb.setOnClickListener{
                binding.intenseCb.isChecked = true
                binding.easyCb.isChecked = false
                binding.seriousCb.isChecked = false
            }
        }

        return binding.root

    }
}