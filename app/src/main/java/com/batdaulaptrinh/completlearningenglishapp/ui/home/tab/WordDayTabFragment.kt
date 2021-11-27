package com.batdaulaptrinh.completlearningenglishapp.ui.home.tab

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordDayTabBinding

class WordDayTabFragment : Fragment() {
    lateinit var binding:FragmentWordDayTabBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_day_tab, container, false)

        binding.experiencePointCv.setOnClickListener{
            val dialog = AlertDialog.Builder(requireContext()).setView(R.layout.statistic_experience_dialog).create()
            dialog.show()
        }

        binding.learningDayCv.setOnClickListener{
            val dialog = AlertDialog.Builder(requireContext()).setView(R.layout.statistic_streak_dialog).create()
            dialog.show()
        }
        return binding.root

    }
}