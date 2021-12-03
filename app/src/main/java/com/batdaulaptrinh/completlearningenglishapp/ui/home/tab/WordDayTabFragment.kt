package com.batdaulaptrinh.completlearningenglishapp.ui.home.tab

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.batdaulaptrinh.completlearningenglishapp.R
import com.batdaulaptrinh.completlearningenglishapp.databinding.FragmentWordDayTabBinding
import com.batdaulaptrinh.completlearningenglishapp.ui.adapter.WordSetRecyclerAdapter
import com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode.MultipleChoiceFragment
import com.batdaulaptrinh.completlearningenglishapp.utils.Utils

class WordDayTabFragment : Fragment() {
    companion object {
        val KEY_AGRS_SET = "WORD_SET"
    }

    lateinit var binding: FragmentWordDayTabBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_word_day_tab, container, false)

        binding.experiencePointCv.setOnClickListener {
            val dialog =
                AlertDialog.Builder(requireContext()).setView(R.layout.statistic_experience_dialog)
                    .create()
            dialog.show()
        }

        binding.learningDayCv.setOnClickListener {
            val dialog =
                AlertDialog.Builder(requireContext()).setView(R.layout.statistic_streak_dialog)
                    .create()
            dialog.show()
        }
        //TODO fake here
        binding.recyclerView.adapter = WordSetRecyclerAdapter(Utils.getWordSet()) { wordSet ->
//            Toast.makeText(context, "CLICK AT ${wordSet.setNth}", Toast.LENGTH_LONG).show()
            findNavController().navigate(
                R.id.action_navigation_home_to_choosingModeFragment,
                bundleOf(MultipleChoiceFragment.KEY_AGRS_SET to wordSet)
            )
        }

        return binding.root

    }
}